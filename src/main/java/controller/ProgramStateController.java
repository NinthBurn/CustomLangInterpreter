package controller;
import repository.IRepository;
import model.ProgramState;
import model.values.*;
import model.types.*;
import model.adt.*;
import java.util.Map;
import java.util.List;
import java.util.stream.*;
import java.util.concurrent.*;


public class ProgramStateController {
	private IRepository repo;
	private ExecutorService executor;
	
	public ProgramStateController(IRepository r) {
		repo = r;
		executor = Executors.newFixedThreadPool(2);
	}
		
	public List<ProgramState> removeCompletedPrograms(List<ProgramState> programs_list){
		return programs_list.stream()
				.filter(program -> !(program.finished()))
				.collect(Collectors.toList());
	}
	public List<ProgramState> oneStepExecutionForAll() throws Exception{
		executor = Executors.newFixedThreadPool(2);
		List<ProgramState> programs = removeCompletedPrograms(repo.getAll());

		if(programs.isEmpty()){
			repo.setProgramList(programs);
			return programs;
		}

		// display program states before execution
		programs.forEach(program -> {
			repo.logProgramStateExecution(program);
			System.out.println(program);
		});

		// execute one step for each
		List<Callable<ProgramState>> call_list = programs.stream()
				.map(program -> (Callable<ProgramState>)
						() -> {
							return program.executeStep();
						})
				.collect(Collectors.toList());

		// run garbage collector
		IDictionary<Integer, IValue> heap_table = repo.getAll().get(0).getHeapTable();
		List<Integer> used_addresses = programs.stream()
				.flatMap((program) -> {
					List<Integer> addresses_used_by_current_program = getAddressesInUse(program);
					return Stream.concat(
									Stream.empty(),
									addresses_used_by_current_program.stream())
							.distinct();

				})
				.collect(Collectors.toList());

		Map<Integer, IValue> new_heap_table = runGlobalGarbageCollector(used_addresses, heap_table);
		if(!heap_table.getContent().equals(new_heap_table))
		{
			System.out.println("--G A R B A G E   C O L L E C T E D--\n");
			heap_table.getContent().entrySet().stream()
					.filter(entry -> !new_heap_table.containsKey(entry.getKey()))
					.forEach(entry -> System.out.println("Address: " + entry.getKey() + "; with value: " + entry.getValue()));
			System.out.println("\n-------------------------------------\n");

			heap_table.setContent(runGlobalGarbageCollector(used_addresses, heap_table));
		}

		List<ProgramState> new_programs_list = executor.invokeAll(call_list).stream()
				.map(future -> {
					try {
						return future.get();
					}catch(Exception e) {
						System.out.println(e.getMessage());
						throw new RuntimeException(e.getMessage());
//						return null;
					}
				})
				.filter(program -> program != null)
				.collect(Collectors.toList());

		programs.addAll(new_programs_list);

		// display program states after execution
		programs.forEach(program -> {
			repo.logProgramStateExecution(program);
			System.out.println(program);
		});


		repo.setProgramList(programs);

		executor.shutdownNow();

		return programs;
	}

	public void allStepExecutionForAll() throws Exception{
		List<ProgramState> programs = removeCompletedPrograms(repo.getAll());

		while(!programs.isEmpty()) {
			programs = oneStepExecutionForAll();

		}

		repo.setProgramList(programs);
	}

	
	public Stream<Integer> getAddressRecursive(ProgramState state, ReferenceValue heap_variable) { 
		ReferenceValue v1 = null;
		
		if(heap_variable.getAddress() != 0 && heap_variable.getReferencedType() instanceof ReferenceType)
		{
			try {
				v1 = (ReferenceValue)state.getHeapTable().get(heap_variable.getAddress());				
			}catch(Exception e) {}
			
			return Stream.concat(
					Stream.of(heap_variable.getAddress()),
					getAddressRecursive(state, v1));
		}
		
		return Stream.of(heap_variable.getAddress());
	}
	
	synchronized List<Integer> getAddressesInUse(ProgramState state){
		List<Integer> result_list;
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		
		result_list = symbol_table.entrySet().stream()
				.filter(symbol -> {
					return symbol.getValue() instanceof ReferenceValue;
				})
				.flatMap(symbol -> {
					return getAddressRecursive(state, (ReferenceValue)(symbol.getValue()));
				})
				.distinct()
				.collect(Collectors.toList());
		
		return result_list;
	}

	
	Map<Integer, IValue> runGlobalGarbageCollector(List<Integer> addresses, IDictionary<Integer, IValue> heap_table){

		return heap_table.entrySet().stream()
				.filter(symbol -> addresses.contains(symbol.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
	}
	
	Map<Integer, IValue> runGarbageCollector(ProgramState state){
		List<Integer> addresses = getAddressesInUse(state);

		return state.getHeapTable().entrySet().stream()
				.filter(symbol -> addresses.contains(symbol.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}

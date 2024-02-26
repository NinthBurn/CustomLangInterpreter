package model;

import model.adt.*;
import model.statements.CompoundStmt;
import model.statements.IStmt;
import model.values.IValue;
import java.io.*;
import java.util.Map;

public class ProgramState {
	private IStack<IStmt> execution_stack;
	private IDictionary<String, IValue> symbol_table;
	private IDictionary<String, BufferedReader> file_table;
	private IDictionary<Integer, IValue> heap_table;
	private IDictionary<Integer, Integer> latch_table;
	private IList<String> output_list;
	private IStmt original_program;
	private int program_id;
	
	private static int last_used_id = 0;
	private boolean print_state;
	private boolean finished_execution;

	public static synchronized int getUnusedId() {
		last_used_id += 1;
		return last_used_id;
	}
	
	public ProgramState(IStmt orig) {		
		execution_stack = new ExecutionStack<IStmt>();
		symbol_table = new SymbolTable<String, IValue>();
		file_table = new FileTable<String, BufferedReader>();
		heap_table = new HeapTable<Integer, IValue>();
		latch_table = new LatchTable<Integer, Integer>();
		output_list = new OutputList<String>();
		original_program = orig;

		program_id = getUnusedId();
		print_state = true;
		finished_execution = false;
		execution_stack.push(original_program);
		expandExecutionStack();
	}
	
	public ProgramState(IStack<IStmt> stk, IDictionary<String, IValue> sym_tbl, IList<String> out, IDictionary<String, BufferedReader> file_tbl, IDictionary<Integer, IValue> heap_tbl, IDictionary<Integer, Integer> latch_tbl, IStmt orig) {
		execution_stack = stk;
		symbol_table = sym_tbl;
		output_list = out;
		file_table = file_tbl;
		heap_table = heap_tbl;
		latch_table = latch_tbl;
		original_program = orig;
		
		program_id = getUnusedId();
		print_state = true;
		finished_execution = false;
		execution_stack.push(original_program);
		expandExecutionStack();
	}

	public void reset() {
		execution_stack = new ExecutionStack<IStmt>();
		symbol_table = new SymbolTable<String, IValue>();
		output_list = new OutputList<String>();
		file_table = new FileTable<String, BufferedReader>();
		heap_table = new HeapTable<Integer, IValue>();
		latch_table = new LatchTable<Integer, Integer>();
		
		print_state = true;
		finished_execution = false;
		execution_stack.push(original_program);
		expandExecutionStack();
	}
	
	public IDictionary<String, IValue> copySymbolTable(){
		IDictionary<String, IValue> symbol_table_copy = new SymbolTable<String, IValue>();
		for(Map.Entry<String, IValue> entry : symbol_table.entrySet()) {
			symbol_table_copy.put(entry.getKey(), entry.getValue().deepcopy());
		}
		
		return symbol_table_copy;
	}
	
	public ProgramState executeStep() throws Exception{
		try {
			IStmt current_statement = execution_stack.pop();
			return current_statement.execute(this);	
		}catch(Exception e) {
			finished_execution = true; 
			throw e;
		}
	}

	public void executeAll() throws Exception{
		while (!this.finished())
			executeStep();
	}
	
	public void enableStateOutput() {
		print_state = true;
	}

	public void disableStateOutput() {
		print_state = false;
	}

	public boolean stateOutputStatus() {
		return print_state;
	}

	public boolean finished() {
		if(execution_stack.isEmpty() || finished_execution)
			return true;
		return false;
	}

	public int getId(){ return this.program_id;}

	public IStack<IStmt> getStack() {
		return this.execution_stack;
	}

	public IDictionary<String, IValue> getSymbolTable() {
		return this.symbol_table;
	}

	public IDictionary<String, BufferedReader> getFileTable() {
		return this.file_table;
	}
	
	public IDictionary<Integer, IValue> getHeapTable() {
		return this.heap_table;
	}

	public IDictionary<Integer, Integer> getLatchTable() {
		return this.latch_table;
	}

	public void setStack(IStack<IStmt> new_stack) {
		execution_stack = new_stack;
	}

	public void setSymbolTable(IDictionary<String, IValue> new_symbol_table) {
		symbol_table = new_symbol_table;
	}
	
	public void setFileTable(IDictionary<String, BufferedReader> new_file_table) {
		file_table = new_file_table;
	}
	
	public void setHeapTable(IDictionary<Integer, IValue> new_heap_table) {
		heap_table = new_heap_table;
	}
	public void setLatchTable(IDictionary<Integer, Integer> new_latch_table) {
		latch_table = new_latch_table;
	}

	public void expandExecutionStack() {
		IStack<IStmt> temp_stack = new ExecutionStack<>();
		try {
			while (!execution_stack.isEmpty()) {
				IStmt current_statement = execution_stack.pop();

				if (current_statement instanceof CompoundStmt) {
					current_statement.execute(this);
				} else temp_stack.push(current_statement);
			}

			while (!temp_stack.isEmpty()) {
				IStmt current_statement = temp_stack.pop();

				execution_stack.push(current_statement);
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public IList<String> getOutputList() {
		return this.output_list;
	}
	
	public String toStringFile() {
		return 
				"Program ID: " + String.valueOf(program_id) + "\n" +
				execution_stack.toString() +
			   "\n" + symbol_table.toString() +
			   "\n" + file_table.toString() +
			   "\n" + heap_table.toString() +
				"\n" + latch_table.toString() +
			   "\n" + output_list.toString() +
			   "\n-------------------------------------\n";
	}
	
	public String toString() {
		return 
				"Program ID: " + String.valueOf(program_id) + "\n" +
//				"\n---+---+--- EXECUTION STACK ---+---+---\n" +
				execution_stack.toString() + "\n" +
//				"---+---+---   SYMBOL TABLE  ---+---+---\n" +
				symbol_table.toString() + "\n" +
//				"---+---+---    FILE TABLE   ---+---+---\n" +
				file_table.toString() + "\n" +
//				"---+---+---    HEAP TABLE   ---+---+---\n" +
				heap_table.toString() + "\n" +
				latch_table.toString() + "\n" +
//				"---+---+---  PROGRAM OUTPUT ---+---+---\n" +
				output_list.toString() + "" +
				"\n-------------------------------------\n";
	}
}

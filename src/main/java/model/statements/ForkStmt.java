package model.statements;
import java.util.Map;

import model.ProgramState;
import model.adt.ExecutionStack;
import model.adt.IDictionary;
import model.adt.MyDictionary;
import model.types.IType;

public class ForkStmt implements IStmt{
	private IStmt fork_program;
	
	public ForkStmt(IStmt fork_stmt) {
		fork_program = fork_stmt;
	}
	
	public ProgramState execute(ProgramState state) {
		return new ProgramState(
				new ExecutionStack<IStmt>(), 
				state.copySymbolTable(),
				state.getOutputList(),
				state.getFileTable(),
				state.getHeapTable(),
				state.getLatchTable(),
				fork_program);
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		IDictionary<String, IType> type_env_copy = new MyDictionary<String, IType>();
		for(Map.Entry<String, IType> entry : type_env.entrySet()) {
			type_env_copy.put(entry.getKey(), entry.getValue().deepcopy());
		}
		
		fork_program.typeCheck(type_env_copy);
		
		return type_env;
	}
	
	public String toString() {
		return "fork(" + fork_program.toString() + ")";
	}
}

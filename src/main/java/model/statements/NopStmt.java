package model.statements;
import model.ProgramState;
import model.adt.IDictionary;
import model.types.IType;

public class NopStmt implements IStmt{
	// do nothing 
	public ProgramState execute(ProgramState state) throws StatementException{
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		return type_env;
	}
	
	public String toString() {
		return "No operation";
	}
}

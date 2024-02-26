package model.statements;

import model.ProgramState;
import model.types.IType;
import model.adt.IDictionary;
@SuppressWarnings("serial")
class StatementException extends Exception{
	StatementException(){
		super();
	}
	StatementException(String message){
		super(message);
	}
}

public interface IStmt {
	ProgramState execute(ProgramState state) throws StatementException;
	IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException;
	String toString();
	
}

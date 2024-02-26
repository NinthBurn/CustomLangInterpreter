package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.IStack;
import model.types.IType;

public class CompoundStmt implements IStmt {
	IStmt first;
	IStmt second;
	
	public CompoundStmt(IStmt s1, IStmt s2) {
		first = s1;
		second = s2;
	}
	
	// unpack the two statements and push them onto the stack in execution order
	public ProgramState execute(ProgramState state) throws StatementException{
		IStack<IStmt> stack = state.getStack();
		stack.push(second);
		stack.push(first);
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		return second.typeCheck(first.typeCheck(type_env));
	}
	
	public String toString(){
//		return first.toString() + ";\n" + second.toString();
//		return "( " + first.toString() + " & " + second.toString() + " )";
		return first.toString() + " & " + second.toString() + "";
	}
}

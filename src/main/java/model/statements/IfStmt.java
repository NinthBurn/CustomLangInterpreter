package model.statements;
import java.util.Map;

import model.ProgramState;
import model.adt.*;
import model.expressions.*;
import model.types.*;
import model.values.*;

public class IfStmt implements IStmt{
	IExpr expr;
	IStmt caseTrue;
	IStmt caseFalse;
	
	public IfStmt(IExpr e, IStmt first, IStmt second) {
		expr = e;
		caseTrue = first;
		caseFalse = second;
	}
	
	public ProgramState execute(ProgramState state) throws StatementException{
		IStack<IStmt> stack = state.getStack();
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		IDictionary<Integer, IValue> heap_table = state.getHeapTable();
		
		IValue val = expr.evaluate(symbol_table, heap_table);
		
		if((boolean)val.getValue() == true)
			stack.push(caseTrue);
		else stack.push(caseFalse);
		
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		IDictionary<String, IType> type_env_copy = new MyDictionary<String, IType>();
		for(Map.Entry<String, IType> entry : type_env.entrySet()) {
			type_env_copy.put(entry.getKey(), entry.getValue().deepcopy());
		}
		
		if(expr.typeCheck(type_env).equals(new BoolType())) {
			caseTrue.typeCheck(type_env_copy);
		
			for(Map.Entry<String, IType> entry : type_env.entrySet()) {
				type_env_copy.put(entry.getKey(), entry.getValue().deepcopy());
			}
			caseFalse.typeCheck(type_env_copy);
			
			return type_env;
		}
		
		throw new StatementException("Expression does not evaluate to a boolean");
	}
	
	public String toString() {
		return "IF(" + expr.toString() + ") THEN(" + caseTrue.toString() + 
				") ELSE(" + caseFalse.toString() + ")";	
	}
}

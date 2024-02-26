package model.statements;
import model.ProgramState;
import model.adt.*;
import model.expressions.*;
import model.types.IType;
import model.values.IValue;

public class AssignStmt implements IStmt{
	String identifier;
	IExpr expr;
	
	public AssignStmt(String id, IExpr e) {
		identifier = id;
		expr = e;
	}
	
	public ProgramState execute(ProgramState state) throws StatementException{
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		IDictionary<Integer, IValue> heap_table = state.getHeapTable();
		
		if(symbol_table.isDefined(identifier)) {
			IValue val = expr.evaluate(symbol_table, heap_table);
			IType typId;
			try {
				typId = (symbol_table.get(identifier)).getType();				
			}catch(ADTException e) {
				throw new StatementException(e.getMessage());
			}
			
			if(val.getType().equals(typId))
				symbol_table.update(identifier, val);
			else throw new StatementException("Types of variable and assignment do not match");
		}else throw new StatementException("Variable was not declared");
		
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
			if(!type_env.isDefined(identifier))
				throw new StatementException("Variable was not declared");

			try{
				if(expr.typeCheck(type_env).equals(type_env.get(identifier)))
					return type_env;

				throw new StatementException("Assignment error: types are different");
			}catch(Exception e){
				throw new StatementException(e.getMessage());
			}

	}
	
	public String toString() {
		return identifier + "=" + expr.toString();
	}
}

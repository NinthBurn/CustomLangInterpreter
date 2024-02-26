package model.expressions;
import model.values.*;
import model.adt.*;
import model.types.IType;

public class VariableExpr implements IExpr{
	String identifier;
	
	public VariableExpr(String id) {
		this.identifier = id;
	}
	
	public IValue evaluate(IDictionary<String, IValue> symbol_table, IDictionary<Integer, IValue> heap_table) throws ExpressionException
	{
		try {
			return symbol_table.get(this.identifier);			
		}catch(ADTException e) {
			throw new ExpressionException("Variable is not defined");
		}
	}
	
	public IType typeCheck(IDictionary<String, IType> type_env) throws ExpressionException{
		try {
			return type_env.get(this.identifier);			
		}catch(ADTException e) {
			throw new ExpressionException("Variable is not defined");
		}
	}
	
	public String toString() {
		return this.identifier;
	}
}

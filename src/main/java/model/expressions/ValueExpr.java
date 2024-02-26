package model.expressions;
import model.values.*;
import model.adt.*;
import model.types.IType;

public class ValueExpr implements IExpr{
	IValue value;
	
	public ValueExpr(IValue v) {
		this.value = v;
	}
	
	public IValue evaluate(IDictionary<String, IValue> symbol_table, IDictionary<Integer, IValue> heap_table) throws ExpressionException
	{
		return this.value;
	}
	
	 public IType typeCheck(IDictionary<String, IType> type_env) throws ExpressionException{
		 return value.getType();
	 }
	
	public String toString() {
		return this.value.toString();
	}
}

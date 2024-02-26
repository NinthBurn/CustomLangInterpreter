package model.expressions;
import model.values.*;
import model.adt.*;
import model.types.IType;
import model.types.ReferenceType;

public class RecursiveHeapReadExpr implements IExpr{
	private IExpr expr;
	private IValue value;
	public RecursiveHeapReadExpr(IExpr expression) {
		expr = expression;
	}
	
	public IValue evaluate(IDictionary<String, IValue> symbol_table, IDictionary<Integer, IValue> heap_table) throws ExpressionException
	{
		value = (new HeapReadExpr(expr)).evaluate(symbol_table, heap_table);
		
		while(value instanceof ReferenceValue) {
			value = (new HeapReadExpr(new ValueExpr(value))).evaluate(symbol_table, heap_table);
		}
		
		return value;
	}
	
	public IType typeCheck(IDictionary<String, IType> type_env) throws ExpressionException{
		IType type = expr.typeCheck(type_env);
		
		if(type instanceof ReferenceType) {
			
			while(type instanceof ReferenceType) {
				type = ((ReferenceType)type).getReferenceType();
			}
			
			return type;
		}
		
		throw new ExpressionException("Heap read argument is not of reference type");
	}
	
	
	public String toString() {
		return "rec_read_heap(" + expr.toString() + ")";
	}
}
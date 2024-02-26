package model.expressions;
import model.values.*;
import model.adt.*;
import model.types.*;

public class HeapReadExpr implements IExpr{
	private IExpr expr;
	private IValue value;
	public HeapReadExpr(IExpr expression) {
		expr = expression;
	}
	
	public IValue evaluate(IDictionary<String, IValue> symbol_table, IDictionary<Integer, IValue> heap_table) throws ExpressionException
	{
		IValue eval_expr = expr.evaluate(symbol_table, heap_table);
		
		if(!(eval_expr instanceof ReferenceValue))
			throw new ExpressionException("Expression does not evaluate to a reference value");
		
		ReferenceValue expr_ref_value = (ReferenceValue)eval_expr;
		
		if(!(heap_table.isDefined(expr_ref_value.getAddress())))
			throw new ExpressionException("Invalid address for reading; it was not allocated");
		
		try {
			value = (IValue)heap_table.get(expr_ref_value.getAddress());			
		}catch(Exception e) {
			throw new ExpressionException(e.getMessage());
		}
		
		return value;
	}
	
	public IType typeCheck(IDictionary<String, IType> type_env) throws ExpressionException{
		IType type = expr.typeCheck(type_env);
		
		if(type instanceof ReferenceType) {
			IType referenced_type = ((ReferenceType)type).getReferenceType();
			return referenced_type;
		}
		
		throw new ExpressionException("Heap read argument is not of reference type");
	}
	
	public String toString() {
		return "read_heap(" + expr.toString() + ")";
	}
}
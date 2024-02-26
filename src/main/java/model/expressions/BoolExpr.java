package model.expressions;
import model.values.*;
import model.adt.*;
import model.types.*;

public class BoolExpr implements IExpr{
	IExpr e1;
	IExpr e2;
	String operator;
	
	public BoolExpr(IExpr expr1, IExpr expr2, String op) {
		e1 = expr1;
		e2 = expr2;
		operator = op;
	}
	
	public IValue evaluate(IDictionary<String, IValue> symbol_table, IDictionary<Integer, IValue> heap_table) throws ExpressionException
	{
		IValue v1,v2;
		v1= e1.evaluate(symbol_table, heap_table);
		// evaluation of two booleans
		if (v1.getType().equals(new BoolType())) {
			v2 = e2.evaluate(symbol_table, heap_table);
			if (v2.getType().equals(new BoolType())) {
				BoolValue i1 = (BoolValue)v1;
				BoolValue i2 = (BoolValue)v2;
				boolean n1,n2;
				n1= (boolean)i1.getValue();
				n2 = (boolean)i2.getValue();
				switch(operator) {
				case "and":
					return new BoolValue(n1 && n2);
				case "or":
					return new BoolValue(n1 || n2);
				case "xor":
					return new BoolValue(n1 ^ n2);
				default:
					throw new ExpressionException("Invalid operator for booleans");
				}
			}else throw new ExpressionException("Second operand is not a boolean");
			
		// evaluation of two integers
		}else throw new ExpressionException("First operand is not of boolean type");
	}
	
	public IType typeCheck(IDictionary<String, IType> type_env) throws ExpressionException{
		IType type1, type2;
		type1 = e1.typeCheck(type_env);
		type2 = e2.typeCheck(type_env);
		
		if(type1.equals(type2)) {
			if(type1.equals(new BoolType()))
				return new BoolType();
			
			throw new ExpressionException("Operands are not of boolean type");
		}
		throw new ExpressionException("Operands do not have the same type");
	}
	
	public String toString() {
		return e1.toString() + " " + operator + " " + e2.toString();
	}
}

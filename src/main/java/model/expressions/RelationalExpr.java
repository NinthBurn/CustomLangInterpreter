package model.expressions;
import model.values.*;
import model.adt.*;
import model.types.*;

public class RelationalExpr implements IExpr{
	IExpr e1;
	IExpr e2;
	String operator;
	
	public RelationalExpr(IExpr expr1, IExpr expr2, String op) {
		e1 = expr1;
		e2 = expr2;
		operator = op;
	}
	
	public IValue evaluate(IDictionary<String, IValue> symbol_table, IDictionary<Integer, IValue> heap_table) throws ExpressionException
	{
		IValue v1,v2;
		v1= e1.evaluate(symbol_table, heap_table);
		
		// evaluation of two integers
		if (v1.getType().equals(new IntType())) {
			v2 = e2.evaluate(symbol_table, heap_table);
			if (v2.getType().equals(new IntType())) {
				IntValue i1 = (IntValue)v1;
				IntValue i2 = (IntValue)v2;
				int n1,n2;
				n1= (int)i1.getValue();
				n2 = (int)i2.getValue();
				switch(operator) {
				case ">":
					return new BoolValue(n1 > n2);
				case "<":
					return new BoolValue(n1 < n2);
				case "==":
					return new BoolValue(n1 == n2);
				case "!=":
					return new BoolValue(n1 != n2);
				case ">=":
					return new BoolValue(n1 >= n2);
				case "<=":
					return new BoolValue(n1 <= n2);
				default:
					throw new ExpressionException("Invalid operator for integers");
				}
			}else throw new ExpressionException("Second operand is not an integer");
			
		// evaluation of two floats
		}else if (v1.getType().equals(new FloatType())) {
			v2 = e2.evaluate(symbol_table, heap_table);
			if (v2.getType().equals(new FloatType())) {
				FloatValue i1 = (FloatValue)v1;
				FloatValue i2 = (FloatValue)v2;
				float n1,n2;
				n1= (float)i1.getValue();
				n2 = (float)i2.getValue();
				switch(operator) {
				case ">":
					return new BoolValue(n1 > n2);
				case "<":
					return new BoolValue(n1 < n2);
				case "==":
					return new BoolValue(n1 == n2);
				case "!=":
					return new BoolValue(n1 != n2);
				case ">=":
					return new BoolValue(n1 >= n2);
				case "<=":
					return new BoolValue(n1 <= n2);
				default:
					throw new ExpressionException("Invalid operator for floats");
				}
			}else throw new ExpressionException("Second operand is not a float");
		}else throw new ExpressionException("First operand is not of a comparable type");
	}
	
	public IType typeCheck(IDictionary<String, IType> type_env) throws ExpressionException{
		IType type1, type2;
		type1 = e1.typeCheck(type_env);
		type2 = e2.typeCheck(type_env);
		
		if(type1.equals(type2)) {
			if(type1.equals(new IntType()) || type1.equals(new FloatType()))
				return new BoolType();

			
			throw new ExpressionException("Operands are not numbers");
		}
		throw new ExpressionException("Operands do not have the same type");
	}
	
	public String toString() {
		return e1.toString() + " " + operator + " " + e2.toString();
	}
}

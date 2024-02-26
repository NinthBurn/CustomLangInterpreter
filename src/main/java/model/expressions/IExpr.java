package model.expressions;
import model.values.IValue;
import model.types.IType;
import model.adt.*;

@SuppressWarnings("serial")
class ExpressionException extends RuntimeException{
	ExpressionException(){
		super();
	}
	ExpressionException(String message){
		super(message);
	}
}

public interface IExpr {
	IValue evaluate(IDictionary<String, IValue> symTable, IDictionary<Integer, IValue> heapTable) throws ExpressionException;

	IType typeCheck(IDictionary<String, IType> type_env) throws ExpressionException;
	String toString();
}

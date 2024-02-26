package model.statements;
import model.ProgramState;
import model.adt.*;
import model.expressions.IExpr;
import model.types.IType;
import model.values.IValue;

public class PrintStmt implements IStmt {
	IExpr expr;
	
	public PrintStmt(IExpr expression) {
		expr = expression;
	}
	
	// add the evaluation of expression to the output list
	public ProgramState execute(ProgramState state) throws StatementException{
		//IStack<IStmt> stack = state.getStack();
		//stack.pop();
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		IList<String> output_list = state.getOutputList();
		IDictionary<Integer, IValue> heap_table = state.getHeapTable();
		
		output_list.add(expr.evaluate(symbol_table, heap_table).toString());
		
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		expr.typeCheck(type_env);
		return type_env;
	}
	
	public String toString() {
		return "print(" + expr.toString() + ")";
	}
}

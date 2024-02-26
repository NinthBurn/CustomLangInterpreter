package model.statements;
import model.adt.*;
import model.expressions.*;
import model.types.IType;
import model.types.ReferenceType;
import model.ProgramState;
import model.values.*;

public class HeapWriteStmt implements IStmt{
	String var_name;
	IExpr expr;
	
	public HeapWriteStmt(String variable_name, IExpr expression){
		var_name = variable_name;
		expr = expression;
	}
	
	public ProgramState execute(ProgramState state) throws StatementException{
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		IDictionary<Integer, IValue> heap_table = state.getHeapTable();
		IValue var;
		ReferenceValue ref_variable;
		IValue eval_expr = expr.evaluate(symbol_table, heap_table);
		
		if(!(symbol_table.isDefined(var_name)))
			throw new StatementException("Variable is not defined");
		
		try {
			var = symbol_table.get(var_name);
		}catch(Exception e){
			throw new StatementException(e.getMessage());
		}
		
		if(!(var instanceof ReferenceValue))
			throw new StatementException("Variable is not of reference type");
		ref_variable = (ReferenceValue)var;
		
		Integer address = ref_variable.getAddress();
		if(!heap_table.isDefined(address))
			throw new StatementException("Variable is pointing to an invalid address");
		
		if(!ref_variable.getReferencedType().equals(eval_expr.getType()))
			throw new StatementException("Expression evaluates to a different type than the one referenced by " + var_name);			
			
		heap_table.update(address, eval_expr);
		
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		try {
			IType type_variable = type_env.get(var_name);
			IType type_expression = expr.typeCheck(type_env);
			
			if(type_variable.equals(new ReferenceType(type_expression))) {
				return type_env;
			}
			
			throw new StatementException("Heap allocation: right hand side and left hand side have different types");
		}catch(Exception e) {
			throw new StatementException(e.getMessage());
		}
	}
	
	public String toString() {
		return "write_heap(" + var_name.toString() + "<-" + expr.toString() + ")";
	}
}

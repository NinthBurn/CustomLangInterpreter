package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.HeapTable;
import model.types.*;
import model.values.*;
import model.expressions.IExpr;
public class HeapAllocStmt implements IStmt{
	String var_name;
	IExpr expr;

	public HeapAllocStmt(String variable_name, IExpr expression) {
		var_name = variable_name;
		expr = expression;
	}
	
	public ProgramState execute(ProgramState state) throws StatementException{
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		HeapTable<Integer, IValue> heap_table = (HeapTable<Integer, IValue>)state.getHeapTable();
		IValue eval_expr = expr.evaluate(symbol_table, heap_table);
		IType location_type;
		
		if(!symbol_table.isDefined(var_name))
			throw new StatementException("Variable is not defined");
		
		try {
			location_type = symbol_table.get(var_name).getType();
			if(!(location_type instanceof ReferenceType))
				throw new StatementException("Variable is not of reference type");	
			
		}catch(Exception e) {
			throw new StatementException(e.getMessage());
		}
		
		IType referenced_type = ((ReferenceType)location_type).getReferenceType();
		
		if(!referenced_type.equals(eval_expr.getType()))
			throw new StatementException("Expression and reference variable do not reference the same type");
		
		Integer free_address = heap_table.get_free_address();
		heap_table.put(free_address, eval_expr);
		symbol_table.put(var_name, new ReferenceValue(free_address, referenced_type));
		
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
		return "new(" + var_name + ", " + expr.toString() + ")";
	}
}

package model.statements;
import java.util.Map;

import model.ProgramState;
import model.adt.*;
import model.expressions.*;
import model.values.*;
import model.types.*;

public class WhileStmt implements IStmt{
	IExpr condition;
	IStmt instructions;
	
	public WhileStmt(IExpr cond, IStmt instr) {
		condition = cond;
		instructions = instr;
	}
	
	public ProgramState execute(ProgramState state) throws StatementException{
		IStack<IStmt> stack = state.getStack();
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		IDictionary<Integer, IValue> heap_table = state.getHeapTable();
		
		IValue condition_val = condition.evaluate(symbol_table, heap_table);
		
		if(!condition_val.getType().equals(new BoolType())) {
			throw new StatementException("Invalid condition");
		}
			
		if((boolean)condition_val.getValue()) {
			stack.push(this);			
			stack.push(instructions);			
		}
		
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		IDictionary<String, IType> type_env_copy = new MyDictionary<String, IType>();
		for(Map.Entry<String, IType> entry : type_env.entrySet()) {
			type_env_copy.put(entry.getKey(), entry.getValue().deepcopy());
		}
		
		if(condition.typeCheck(type_env).equals(new BoolType())) {
			instructions.typeCheck(type_env_copy);
		
			return type_env;
		}
		
		throw new StatementException("Expression does not evaluate to a boolean");
	}
	
	public String toString() {
		return "WHILE(" + condition.toString() + ") EXECUTE{ " + instructions.toString() + "}";
	}
}

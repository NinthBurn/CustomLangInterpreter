package model.statements;
import model.ProgramState;
import model.adt.*;
import model.values.*;
import model.types.*;

public class ForStmt implements IStmt{
	IValue iteration_step, end_value, start_value;
	IStmt instructions;
		
	public ForStmt(IValue start_value, IValue end_value, IStmt instr) {
		this.start_value = start_value;
		this.end_value = end_value;
		iteration_step = new IntValue(1);
		instructions = instr;
	}
	
	public ForStmt(IValue start_value, IValue end_value, IValue step, IStmt instr) {
		this.start_value = start_value;
		this.end_value = end_value;
		iteration_step = step;
		instructions = instr;
	}
	
	public ProgramState execute(ProgramState state) throws StatementException{
		IStack<IStmt> stack = state.getStack();
		
		if(!start_value.getType().equals(new IntType()) || !end_value.getType().equals(new IntType()) || !iteration_step.getType().equals(new IntType()))
			throw new StatementException("Invalid type for loop arguments ");
		
		int step_int, end_int, start_int;
		step_int = (int)iteration_step.getValue();
		start_int = (int)start_value.getValue();
		end_int = (int)end_value.getValue();
		
		if((step_int < 0 && start_int >= end_int) || (step_int > 0 && start_int <= end_int)) {
			stack.push(new ForStmt(new IntValue(start_int + 1), end_value, iteration_step, instructions));
			stack.push(instructions);			
		}else{
			stack.push(instructions);
		}
		
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		return type_env;
	}
	
	public String toString() {
		return "FOR RANGE(" + String.valueOf(start_value) + " to " + String.valueOf(end_value) + ", " + String.valueOf(iteration_step) + ") EXECUTE{ " + instructions.toString() + "}";
	}
}

package model.statements;
import model.ProgramState;
import model.adt.*;
import model.types.*;
import model.values.*;

public class InitializationStmt implements IStmt{
	String name;
	IType type;
	IValue value;
	
	public InitializationStmt(IType t, String n, IValue v) {
		type = t;
		name = n;
		value = v;
	}

	public ProgramState execute(ProgramState state) throws StatementException{
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		
		if(symbol_table.isDefined(name))
			throw new StatementException("Variable is already defined");
		else {
			Object v = value.getValue();
			// int
			if(type instanceof IntType)
				symbol_table.put(name, new IntValue((int)v));
			// float
			else if(type instanceof FloatType)
				symbol_table.put(name, new FloatValue((float)v));
			// bool
			else if(type instanceof BoolType)
				symbol_table.put(name, new BoolValue((boolean)v));
			// string
			else if(type instanceof StringType)
				symbol_table.put(name, new StringValue((String)v));
			
			else throw new StatementException("Invalid declaration type");
		}
		
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		if(value.getType().equals(type))
			return type_env;
		
		throw new StatementException("Assignment error: types are different");
	}
	
	public String toString() {
		return type.toString() + " " + name;
	}
}

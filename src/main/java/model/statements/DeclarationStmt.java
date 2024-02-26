package model.statements;
import model.ProgramState;
import model.adt.*;
import model.types.*;
import model.values.*;

public class DeclarationStmt implements IStmt{
	String name;
	IType type;
	
	public DeclarationStmt(IType t, String n) {
		type = t;
		name = n;
	}
	
	// define the new variable in the symbol table 
	// or throw exception if it already exists
	public ProgramState execute(ProgramState state) throws StatementException{
		//IStack<IStmt> execution_stack = state.getStack();
		//execution_stack.pop();
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		
		if(symbol_table.isDefined(name))
			throw new StatementException("Variable is already defined");
		else {
			// int
			if(type instanceof IntType)
				symbol_table.put(name, new IntValue((int)(new IntType()).defaultValue().getValue()));
			// float
			else if(type instanceof FloatType)
				symbol_table.put(name, new FloatValue((float)(new FloatType()).defaultValue().getValue()));
			// string
			else if(type instanceof StringType)
				symbol_table.put(name, new StringValue((String)(new StringType()).defaultValue().getValue()));
			// bool
			else if(type instanceof BoolType)
				symbol_table.put(name, new BoolValue((boolean)(new BoolType().defaultValue().getValue())));
			// reference
			else if(type instanceof ReferenceType) {
				ReferenceType referenced_type = ((ReferenceType) type);
				symbol_table.put(name, (new ReferenceType(referenced_type.getReferenceType()).defaultValue()));
			}
			else throw new StatementException("Invalid declaration type");
		}
		
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		type_env.put(name, type);
		return type_env;
	}
	
	public String toString() {
		return type.toString() + " " + name;
	}
}

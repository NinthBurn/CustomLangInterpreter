package model.statements;
import model.ProgramState;
import model.adt.*;
import model.expressions.*;
import model.types.*;
import model.values.*;
import model.values.IValue;
import java.io.*;


public class ReadFileStmt implements IStmt{
	IExpr expr;
	String variable_name;
	
	public ReadFileStmt(IExpr e, String var_name) {
		expr = e;
		variable_name = var_name;
	}
	
	public ProgramState execute(ProgramState state) throws StatementException{
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		IDictionary<String, BufferedReader> file_table = state.getFileTable();
		IDictionary<Integer, IValue> heap_table = state.getHeapTable();
		IValue val = expr.evaluate(symbol_table, heap_table);
		
		if(!symbol_table.isDefined(variable_name))
			throw new StatementException("Variable is not defined");
				
		try {			
			if(!symbol_table.get(variable_name).getType().equals(new IntType()))
				throw new StatementException("Read variable must be of type int");
			
		}catch(Exception e) {
			throw new StatementException(e.getMessage());
		}
		
		if(!val.getType().equals(new StringType())) {
			throw new StatementException("Invalid expression argument");
		}else {
			String filename = (String)val.getValue();
			if(!file_table.isDefined(filename)){
				throw new StatementException("File was not opened");
			}else {
				try {
					BufferedReader file_descriptor = file_table.get(filename);
					String line = file_descriptor.readLine();
					int new_val;
					if(line == null)
						new_val = 0;
					else new_val = Integer.parseInt(line);
					
					symbol_table.update(variable_name, new IntValue(new_val));
					
				}catch(Exception e) {
					throw new StatementException(e.getMessage());
				}
			}
		}
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		try {
			if(!type_env.get(variable_name).equals(new IntType()))
				throw new StatementException("File read variable must be of type int");
	
			if(expr.typeCheck(type_env).equals(new StringType()))
				return type_env;
				
			throw new StatementException("Expression argument must be a string");
		}catch(Exception e) {
			throw new StatementException(e.getMessage());
		}
	}
	
	public String toString() {
		return   "read_file(" + expr.toString() + ", " + variable_name + ")" ;
	}
}

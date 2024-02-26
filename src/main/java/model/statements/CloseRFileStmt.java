package model.statements;
import model.ProgramState;
import model.adt.*;
import model.expressions.*;
import model.types.*;
import model.values.IValue;
import java.io.*;

public class CloseRFileStmt implements IStmt{
	IExpr expr;
	
	public CloseRFileStmt(IExpr e) {
		expr = e;
	}
	
	public ProgramState execute(ProgramState state) throws StatementException{
		IDictionary<String, IValue> symbol_table = state.getSymbolTable();
		IDictionary<String, BufferedReader> file_table = state.getFileTable();
		IDictionary<Integer, IValue> heap_table = state.getHeapTable();
		
		IValue val = expr.evaluate(symbol_table, heap_table);
		if(!val.getType().equals(new StringType())) {
			throw new StatementException("Invalid expression argument");
		}else {
			String filename = (String)val.getValue();
			if(!file_table.isDefined(filename)){
				throw new StatementException("File specified was not opened");
			}else {
				try {
					Reader file_descriptor = file_table.get(filename);
					file_table.remove(filename);
					file_descriptor.close();
				}catch(Exception e) {
					throw new StatementException(e.getMessage());
				}
			}
		}
		return null;
	}
	
	public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException{
		if(expr.typeCheck(type_env).equals(new StringType()))
			return type_env;
		
		throw new StatementException("Expression argument must be a string");
	}
	
	public String toString() {
		return  "close_file(" + expr.toString() + ")";
	}
}

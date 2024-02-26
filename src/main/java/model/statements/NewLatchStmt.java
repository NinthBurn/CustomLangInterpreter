package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.LatchTable;
import model.expressions.IExpr;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class NewLatchStmt implements IStmt{
    String var;
    IExpr exp;

    public NewLatchStmt(String variable_name, IExpr expression){
        var = variable_name;
        exp = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, IValue> symbol_table = state.getSymbolTable();
        IDictionary<Integer, IValue> heap_table = state.getHeapTable();
        LatchTable<Integer, Integer> latch_table = (LatchTable<Integer, Integer>)state.getLatchTable();

        IValue exp_val = exp.evaluate(symbol_table, heap_table);
        if(!exp_val.getType().equals(new IntType()))
            throw new StatementException("Latch expression does not evaluate to an integer");

        try{
            if(!symbol_table.isDefined(var))
                throw new StatementException("Latch variable is not defined");

            if(!symbol_table.get(var).getType().equals(new IntType()))
                throw new StatementException("Latch variable is not of int type");

            int free_location = latch_table.getFreeAddress();
            latch_table.put(free_location, (Integer)exp_val.getValue());
            symbol_table.update(var, new IntValue(free_location));

        }catch(Exception e){
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException {
        try{
            if(!type_env.get(var).equals(new IntType()))
                throw new StatementException("Latch variable is not of type int");

            if(!type_env.get(var).equals(exp.typeCheck(type_env)))
                throw new StatementException("Latch variable and expression don't have matching types");

        }catch(Exception e) {
            throw new StatementException(e.getMessage());
        }

        return type_env;
    }

    public String toString(){
        return "newLatch(" + var + "," + exp + ")";
    }
}

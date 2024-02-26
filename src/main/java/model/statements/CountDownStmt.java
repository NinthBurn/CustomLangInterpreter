package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.adt.LatchTable;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;

public class CountDownStmt implements IStmt{
    String var;

    public CountDownStmt(String variable_name){
        var = variable_name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary<String, IValue> symbol_table = state.getSymbolTable();
        LatchTable<Integer, Integer> latch_table = (LatchTable<Integer, Integer>)state.getLatchTable();

        try{
            if(!symbol_table.isDefined(var))
                throw new StatementException("Latch await variable is not defined");

            if(!symbol_table.get(var).getType().equals(new IntType()))
                throw new StatementException("Latch await variable is not of int type");

            int found_index = (Integer)symbol_table.get(var).getValue();

            if(!latch_table.isDefined(found_index))
                throw new StatementException("Invalid latch table index");

            int count = latch_table.get(found_index);

            if(count > 0){
                latch_table.update(found_index, count - 1);
            }

            state.getOutputList().add(String.valueOf(state.getId()));

        }catch(Exception e){
            throw new StatementException(e.getMessage());
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException {
        try{
            if(!type_env.get(var).equals(new IntType()))
                throw new StatementException("Latch count down variable is not of type int");
        }catch(Exception e) {
            throw new StatementException(e.getMessage());
        }

        return type_env;
    }

    public String toString(){
        return "countdown(" + var + ")";
    }
}

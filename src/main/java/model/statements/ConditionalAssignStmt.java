package model.statements;

import model.ProgramState;
import model.adt.IDictionary;
import model.expressions.IExpr;
import model.types.BoolType;
import model.types.IType;

public class ConditionalAssignStmt implements IStmt{
    String var;
    IExpr exp1, exp2, exp3;

    public ConditionalAssignStmt(String variable_name, IExpr expression1, IExpr expression2, IExpr expression3){
        var = variable_name;
        exp1 = expression1;
        exp2 = expression2;
        exp3 = expression3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StatementException {
        IStmt cond_assign_stmt = new IfStmt(exp1, new AssignStmt(var, exp2), new AssignStmt(var, exp3));
        state.getStack().push(cond_assign_stmt);

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> type_env) throws StatementException {
        if(!type_env.isDefined(var))
            throw new StatementException("Variable is not defined");

        try{
            if(!(type_env.get(var).equals(exp2.typeCheck(type_env)) && exp2.typeCheck(type_env).equals(exp3.typeCheck(type_env))))
                throw new StatementException("Type of variable and assignment expressions differs");

            if(!exp1.typeCheck(type_env).equals(new BoolType()))
                throw new StatementException("Conditional expression does not evaluate to a boolean type");

        }catch(Exception e){
            throw new StatementException(e.getMessage());
        }

        return type_env;
    }

    public String toString(){
        return var + "=" + exp1 + "?" + exp2 + ":" + exp3;
    }
}

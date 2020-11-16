package model.statement;

import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.expression.Expression;
import model.types.IntType;
import model.types.RefType;
import model.values.RefValue;
import model.values.Value;

public class ReadHeap implements IStatement {

    private Expression expression;

    public ReadHeap(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        // FIXME: Eval must have the heap table
        Value evaluated = expression.eval(state.getSymbolTable());

        if(!evaluated.getType().equals(new RefType(new IntType()))){
            throw new VariableTypeMismatchException("Not RefType...!?");
        }

        RefValue refValue = (RefValue)evaluated;

        // Get address of refValue and access Heap table for the associated value
        // ==>


        return state;
    }
}

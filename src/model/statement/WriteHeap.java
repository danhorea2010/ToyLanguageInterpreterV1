package model.statement;

import exceptions.VariableNotDeclaredException;
import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.expression.Expression;
import model.types.RefType;
import model.values.RefValue;
import model.values.Value;

public class WriteHeap implements IStatement{

    private final String variableName;
    private final Expression expression;

    public WriteHeap(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        // Check if variableName is defined in symtable
        Value value = state.getSymbolTable().get(variableName);
        if( value == null){
            throw new VariableNotDeclaredException(variableName + " not in symtable");
        }

        // check if refType
        if( !(value.getType() instanceof RefType))
        {
            throw new VariableTypeMismatchException(variableName + " must be RefType");
        }

        RefValue refValue = (RefValue)value;

        // check if address from Refvalue is associated with a key in the heap
        Value heapValue = state.getHeapTable().get(refValue.getAddress());

        if (heapValue == null) {
            throw  new RuntimeException("Reference value not in heap");
        }

        // Evaluate expression
        Value evaluated = expression.eval(state.getSymbolTable(), state.getHeapTable());

        // The result must have type equal to locationType of the varName ( refValue)

        if(!evaluated.getType().equals(refValue.getLocationType())) {
            throw new VariableTypeMismatchException("Variable and expression type don't match");
        }

        // access heap using varName address and update with evaluated value
        state.getHeapTable().put(refValue.getAddress(), evaluated);


        return state;
    }

    @Override
    public String toString() {
        return "WriteHeap( " +  variableName + ", " + expression + ")";
    }
}

package model.statement;

import exceptions.VariableNotDeclaredException;
import exceptions.VariableTypeMismatchException;
import model.ProgramState;
import model.expression.Expression;
import model.types.RefType;
import model.values.RefValue;
import model.values.Value;

public class New implements IStatement {

    private String variableName;
    private Expression expression;

    public New(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        var variable = state.getSymbolTable().get(variableName);

        // Check if variable is declared
        if(variable == null){
            throw new VariableNotDeclaredException("Variable "+variableName +" not declared");
        }

        // Check if variable is RefType
        if(!(variable.getType() instanceof RefType))
        {
            throw new VariableTypeMismatchException(variableName + " must be RefType!");
        }
        RefValue refVariable = (RefValue)variable;
        Value evaluated = expression.eval(state.getSymbolTable(), state.getHeapTable());
        // Type matches?
        if(!evaluated.getType().equals(refVariable.getLocationType()))
        {
            throw new VariableTypeMismatchException
                    ("Type of " + evaluated.getType() + " and "
                    + refVariable.getLocationType() + " do not match");
        }


        // Create new entry in heapTable
        // -> new free address is generated
        // -> associated to the result of the expression
        // evaluation
        var heapTable = state.getHeapTable();
        Integer nextFreeAddress = heapTable.getNextFreeAddress();
        refVariable.setAddress(nextFreeAddress);

        // Working version
        //heapTable.putHeap(evaluated);

        if( evaluated.getType() instanceof RefType)
        {
            RefValue evaluatedRef = (RefValue) evaluated;
            heapTable.putHeap(new RefValue(evaluatedRef.getAddress(), evaluatedRef.getLocationType()));
        }
        else{
            heapTable.putHeap(evaluated);
        }

        // Update RefValue in symtable
        // associated to
        // variableName
        // such that new RefValue has the same
        var symtable = state.getSymbolTable();

        symtable.put(variableName, refVariable);

        // locationType and the address is equal
        // to the new key generated
        // in the Heap at the previous step

        return state;
    }

    @Override
    public String toString() {
        return "New(" + variableName + "," + expression+ ")";
    }
}

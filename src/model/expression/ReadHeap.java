package model.expression;

import exceptions.VariableNotDeclaredException;
import exceptions.VariableTypeMismatchException;
import model.adt.Heap;
import model.adt.IDictionary;
import model.types.RefType;
import model.types.Type;
import model.values.RefValue;
import model.values.Value;

public class ReadHeap implements Expression {

    private final Expression expression;

    public ReadHeap(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, Heap heapTable) throws Exception {
        Value evaluated = expression.eval(tbl,heapTable);

        if(!(evaluated.getType() instanceof RefType)){
            throw new VariableTypeMismatchException("ReadHeap expression must be RefType");
        }

        RefValue refValue = (RefValue)evaluated;

        Value heapValue = heapTable.get(refValue.getAddress());

        if( heapValue == null){
            throw new VariableNotDeclaredException(refValue.getAddress() + " is not a key in the heap table!\n");
        }

        return heapValue;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws Exception {
        Type type = expression.typeCheck(typeEnvironment);

        if (!(type instanceof RefType)){
            throw new VariableTypeMismatchException("ReadHeap argument must be RefType");
        }

        RefType reft = (RefType)type;
        return reft.getInner();
    }

    @Override
    public String toString() {
        return "ReadHeap(" + expression + ")";
    }

}

package model.expression;

import model.adt.Heap;
import model.adt.IDictionary;
import model.values.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value value){
        this.value = value;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, Heap heapTable) throws Exception {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}

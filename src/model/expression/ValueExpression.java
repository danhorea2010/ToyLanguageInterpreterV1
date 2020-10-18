package model.expression;

import model.adt.IDictionary;
import model.values.Value;

public class ValueExpression implements Expression {
    Value value;

    public ValueExpression(Value value){
        this.value = value;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl) throws Exception {
        return this.value;
    }


}

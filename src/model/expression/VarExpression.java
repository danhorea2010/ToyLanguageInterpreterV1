package model.expression;

import model.adt.IDictionary;
import model.values.Value;

public class VarExpression implements Expression {

    String id;

    public VarExpression(String id){
        this.id = id;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl) throws Exception {
        return tbl.get(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

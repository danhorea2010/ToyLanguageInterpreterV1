package model.expression;

import model.IDictionary;
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
}

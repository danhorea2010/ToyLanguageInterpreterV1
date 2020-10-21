package model.expression;

import exceptions.VariableNotDeclaredException;
import model.adt.IDictionary;
import model.values.Value;

public class VarExpression implements Expression {

    String id;

    public VarExpression(String id){
        this.id = id;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl) throws Exception {
        Value val = tbl.get(id);
        if( val == null){
            throw new VariableNotDeclaredException("Variable not declared");
        }
        return val;
    }

    @Override
    public String toString() {
        return id;
    }

}

package model.expression;

import exceptions.VariableNotDeclaredException;
import model.adt.Heap;
import model.adt.IDictionary;
import model.types.Type;
import model.values.Value;

public class VarExpression implements Expression {

    String id;

    public VarExpression(String id){
        this.id = id;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, Heap heapTable) throws Exception {
        Value val = tbl.get(id);
        if( val == null){
            throw new VariableNotDeclaredException("Variable not declared");
        }
        return val;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws Exception {
        return typeEnvironment.get(id);
    }

    @Override
    public String toString() {
        return id;
    }

}

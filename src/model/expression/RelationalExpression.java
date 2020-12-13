package model.expression;

import exceptions.VariableTypeMismatchException;
import model.adt.Heap;
import model.adt.IDictionary;
import model.types.BoolType;
import model.types.IntType;
import model.types.Type;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;

public class RelationalExpression implements Expression {

    private final Expression expression1;
    private final Expression expression2;
    private final String operator;

    public RelationalExpression(String operator, Expression expression1, Expression expression2){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "" + expression1 + " " + operator + " " + expression2;
    }

    @Override
    public Value eval(IDictionary<String, Value> tbl, Heap heapTable) throws Exception {

        Value value1 = expression1.eval(tbl,heapTable );
        Value value2 = expression2.eval(tbl, heapTable);

        if(!value1.getType().equals(new IntType()))
            throw new VariableTypeMismatchException("Variable must be int");

        if(!value2.getType().equals(new IntType()))
            throw new VariableTypeMismatchException("Variable must be int");

        int intVal1 = ((IntValue) value1).getValue();
        int intVal2 = ((IntValue) value2).getValue();

        switch (this.operator){
            case "<" -> {
                return new BoolValue(intVal1 < intVal2);
            }
            case "<=" ->{
                return new BoolValue(intVal1 <= intVal2);
            }
            case "==" ->{
                return new BoolValue(intVal1 == intVal2);
            }
            case "!=" ->{
                return new BoolValue(intVal1 != intVal2);
            }
            case ">" ->{
                return new BoolValue(intVal1 > intVal2);
            }
            case ">=" -> {
                return new BoolValue(intVal1 >= intVal2);
            }

        }

        throw new VariableTypeMismatchException("Invalid operator");
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws Exception {
        Type type1, type2;

        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);

        if (!type1.equals(new BoolType()))
            throw new VariableTypeMismatchException("First operand must be boolean");
        if (!type2.equals(new BoolType()))
            throw new VariableTypeMismatchException("Second operand must be boolean");

        return new BoolType();


    }


}

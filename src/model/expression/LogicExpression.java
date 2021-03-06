package model.expression;

import exceptions.VariableTypeMismatchException;
import model.adt.Heap;
import model.adt.IDictionary;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpression implements Expression{

    public static final int INVALID_OPERATOR = 2;

    private final Expression expression1;
    private final Expression expression2;
    private final int operator;

    public LogicExpression(String operator, Expression expression1, Expression expression2){

        switch (operator){
            case "&&" -> this.operator = 0; // and
            case "||" -> this.operator = 1; // or
            default -> this.operator = INVALID_OPERATOR; // invalid
        }

        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    enum OperatorType{
        And,
        Or,
    }


    @Override
    public Value eval(IDictionary<String, Value> tbl, Heap heapTable) throws Exception {
        Value value1, value2;

        // invalid operator...
        if(this.operator == INVALID_OPERATOR){
            throw new VariableTypeMismatchException("Invalid logic operator");
        }

        value1 = expression1.eval(tbl, heapTable);
        if(value1.getType().equals(new BoolType())   )
        {
            value2 = expression2.eval(tbl, heapTable);
            if(value2.getType().equals(new BoolType())   )
            {
                BoolValue bool1 = (BoolValue)value1;
                BoolValue bool2 = (BoolValue)value2;

                boolean b1,b2;

                b1 = bool1.getValue();
                b2 = bool2.getValue();

                OperatorType operatorType = OperatorType.values()[operator];

                switch (operatorType){

                    case And -> {
                        return new BoolValue(b1 && b2);
                    }
                    case Or -> {
                        return new BoolValue(b1 || b2);
                    }
                }

            }else
                throw new VariableTypeMismatchException("Second operand is not boolean\n");
        }else
            throw new VariableTypeMismatchException("First operand is not boolean\n");

        return null;
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

    @Override
    public String toString() {

        String displayOp;

        switch (operator){
            case 0 -> displayOp = "&&";
            case 1 -> displayOp = "||";
            default -> displayOp = " invalid ";
        }

        return  "" + expression1 +" "+ displayOp  +" "+ expression2;
    }

}

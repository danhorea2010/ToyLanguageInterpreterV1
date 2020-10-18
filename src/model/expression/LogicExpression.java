package model.expression;

import model.adt.IDictionary;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;

public class LogicExpression implements Expression{

    Expression expression1;
    Expression expression2;
    int operator;

    enum OperatorType{
        And,
        Or,
    }


    @Override
    public Value eval(IDictionary<String, Value> tbl) throws Exception {
        Value value1, value2;

        value1 = expression1.eval(tbl);
        if(value1.getType().equals(new BoolType())   )
        {
            value2 = expression2.eval(tbl);
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

            }
        }

        return null;
    }
}

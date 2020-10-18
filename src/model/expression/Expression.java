package model.expression;

import model.adt.IDictionary;
import model.values.Value;

public interface Expression {
    Value eval(IDictionary<String, Value> tbl) throws Exception;
}

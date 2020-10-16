package model.expression;

import model.IDictionary;
import model.values.Value;

public interface Expression {
    Value eval(IDictionary<String, Value> tbl) throws Exception;
}

package model.expression;

import model.adt.Heap;
import model.adt.IDictionary;
import model.values.Value;

public interface Expression {
    Value eval(IDictionary<String, Value> tbl, Heap heapTable) throws Exception;
}

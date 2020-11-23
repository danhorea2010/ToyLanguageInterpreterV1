package model.adt;
import model.values.Value;

import java.util.Map;

public interface IHeap extends IDictionary<Integer, Value> {
    void putHeap(Value value);
    void setContent(Map<Integer, Value> newContent);
    Map<Integer, Value> getContent();
    Integer getAddress();
    void setAddress(Integer value);

}

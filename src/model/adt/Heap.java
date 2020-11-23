package model.adt;

import exceptions.VariableNotDeclaredException;
import model.values.RefValue;
import model.values.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap implements IHeap {

    private HashMap<Integer, Value> map;
    private int nextFreeLocation;

    public Heap(){
        map = new HashMap<>();
        nextFreeLocation = 1;
    }

    /* Should not be used directly */
    @Override
    public void put(Integer key, Value value) {
        this.map.put(key,value);
    }

    /* Used for heap */
    @Override
    public void putHeap(Value value) {
        this.map.put(nextFreeLocation, value);
    }

    @Override
    public void setContent(Map<Integer, Value> newContent) {
        this.map = new HashMap<>(newContent);
    }

    @Override
    public Integer getAddress() {
        return nextFreeLocation;
    }

    @Override
    public void setAddress(Integer value) {
        this.nextFreeLocation = value;
    }

    @Override
    public Map<Integer, Value> getContent() {
        return this.map;
    }

    @Override
    public Value get(Integer key) {
        return map.get(key);
    }

    @Override
    public Value remove(Integer key) throws VariableNotDeclaredException {
        Value removed = map.remove(key);
        if( removed == null){
            throw new VariableNotDeclaredException("Key " + key + " not found in heap!");
        }

        RefValue refValue = (RefValue)removed;
        nextFreeLocation = refValue.getAddress();
        return removed;
    }

    @Override
    public Set<Integer> keyset() {
        return map.keySet();
    }

    @Override
    public Collection<Value> values() {
        return map.values();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

}

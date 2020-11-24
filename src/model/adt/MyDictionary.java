package model.adt;

import exceptions.VariableNotDeclaredException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary<K,V> implements IDictionary<K,V> {

    private final HashMap<K,V> hashMap;

    public MyDictionary(){
        hashMap = new HashMap<>();
    }
    public MyDictionary(HashMap<K,V> newMap){
        this.hashMap = new HashMap<>(newMap);
    }

    @Override
    public void put(K key, V value) {
        hashMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return hashMap.get(key);
    }

    @Override
    public V remove(K key) throws VariableNotDeclaredException {
        V removed = hashMap.remove(key);
        if(removed == null){
            throw new VariableNotDeclaredException("Key " + key + " not present in dictionary");
        }
        return removed;
    }

    @Override
    public Set<K> keyset() {
        return hashMap.keySet();
    }

    @Override
    public Collection<V> values() {
        //if(hashMap.size() == 0){
        //    throw new ReadFromEmptyException("Dictionary is empty\n");
        //}
        return hashMap.values();
    }

    @Override
    public void clear() {
        this.hashMap.clear();
    }

    @Override
    public IDictionary<K, V> Clone() {
        return new MyDictionary<>(this.hashMap);
    }

    @Override
    public String toString() {
        return this.hashMap.toString();
    }
}

package model.adt;

import exceptions.VariableNotDeclaredException;

import java.util.Collection;
import java.util.Set;

public interface IDictionary<K,V> {

    void put(K key, V value);
    V get (K key);
    V remove(K key) throws VariableNotDeclaredException;
    Set<K> keyset();
    Collection<V> values();
    void clear();



}

package model;

import java.util.Collection;
import java.util.Set;

public interface IDictionary<K,V> {

    void put(K key, V value);
    V remove(K key);
    Set<K> keyset();
    Collection<V> values();



}

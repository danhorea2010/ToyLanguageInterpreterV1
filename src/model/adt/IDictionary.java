package model.adt;

import exceptions.ReadFromEmptyException;

import java.util.Collection;
import java.util.Set;

// TODO: Dictionary exceptions
public interface IDictionary<K,V> {

    void put(K key, V value);
    V get (K key) ;
    V remove(K key) ;
    Set<K> keyset();
    Collection<V> values() throws ReadFromEmptyException;

    void clear();



}

package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary<K,V> implements IDictionary<K,V> {

    private final HashMap<K,V> hashMap;

    public MyDictionary(){
        hashMap = new HashMap<K,V>();
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
    public V remove(K key) {
        return hashMap.remove(key);
    }

    @Override
    public Set<K> keyset() {
        return hashMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return hashMap.values();
    }
}

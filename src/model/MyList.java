package model;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T>{

    private final List<T> list;

    public MyList(){
        list = new ArrayList<T>();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public boolean add(T value) {
        return list.add(value);
    }

    @Override
    public boolean remove(T value) {
        return list.remove(value);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }


}

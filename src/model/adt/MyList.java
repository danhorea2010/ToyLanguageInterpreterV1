package model.adt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MyList<T> implements IList<T>{

    private final List<T> list;

    public MyList(){
        list = new ArrayList<>();
    }

    @Override
    public T get(int index)  {

        if(index > this.list.size()){
            throw new ArrayIndexOutOfBoundsException("Index larger than list size\n");
        }

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

    @Override
    public Stream<T> stream() {
        return this.list.stream();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

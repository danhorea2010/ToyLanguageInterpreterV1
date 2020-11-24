package model.adt;

import java.util.List;
import java.util.stream.Stream;

public interface IList<T> {

    T       get   (int index);
    List<T> getList();
    boolean add   (T value);
    boolean remove(T value);
    int     size  ();
    void    clear ();
    Stream<T> stream();

}

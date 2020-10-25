package model.adt;

import java.util.stream.Stream;

public interface IList<T> {

    T       get   (int index);
    boolean add   (T value);
    boolean remove(T value);
    int     size  ();
    void    clear ();
    Stream<T> stream();

}

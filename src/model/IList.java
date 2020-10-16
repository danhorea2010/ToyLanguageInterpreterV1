package model;

public interface IList<T> {

    T       get   (int index);
    boolean add   (T value);
    boolean remove(T value);
    int     size  ();
    void    clear ();

}

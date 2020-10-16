package model;

public interface IList<T> {

    boolean add   (T value);
    boolean remove(T value);
    int     size  ();
    void    clear ();

}

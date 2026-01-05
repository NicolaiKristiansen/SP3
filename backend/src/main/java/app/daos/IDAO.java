package app.daos;

public interface IDAO<E, I> {

    public E create(E e);

    public E findById(I id);

    public E update(E e, I id);

    public void delete(I id);
}

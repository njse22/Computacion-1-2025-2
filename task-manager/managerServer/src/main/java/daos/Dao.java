package daos;

import java.util.List;

public interface Dao<T, P> {
    public List<T> findAll();
    public T findById(P id);
    public T update(T newEntity);
    public void delete(T entity);
    public void save(T entity);
}

package dao;

import java.util.Collection;

public interface Dao<T> {
    T getById(long id);

    Collection<T> getAll();

    T save(T t);

    void deleteById(long id);

    void deleteAll();

    void deleteByEntity(T t);

    T update(T t);
}
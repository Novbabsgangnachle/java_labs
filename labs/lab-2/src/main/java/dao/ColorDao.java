package dao;

import domain.Color;
import domain.Pet;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.Collection;

public class ColorDao implements Dao<Color> {
    private <T> T executeInTransaction(TransactionAction<T> action) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                T result = action.execute(session);
                transaction.commit();
                return result;
            } catch (Exception e) {
                if (transaction.getStatus().canRollback()) {
                    try {
                        transaction.rollback();
                    } catch (Exception rollbackEx) {
                        rollbackEx.printStackTrace();
                    }
                }
                throw e;
            }
        }
    }

    @Override
    public Color getById(long id) {
        return executeInTransaction(session -> session.find(Color.class, id));
    }

    @Override
    public Collection<Color> getAll() {
        return executeInTransaction(session -> session.createQuery("from Color", Color.class).list());
    }

    @Override
    public Color save(Color color) {
        return executeInTransaction(session -> {
            session.save(color);
            return color;
        });
    }

    public Color saveWithId(Color color, long id) {
        return executeInTransaction(session -> {
            String sql = "INSERT INTO colors (id, color_name) VALUES (:id, :color_name)";
            Query query = session.createNativeQuery(sql, Pet.class);

            query.setParameter("id", id);
            query.setParameter("color_name", color.getColorName());

            query.executeUpdate();

            return color;
        });
    }
    @Override
    public void deleteById(long id) {
        executeInTransaction(session -> {
            Color color = getById(id);
            if (color != null) {
                session.delete(color);
            }
            return null;
        });
    }

    @Override
    public void deleteAll() {
        executeInTransaction(session -> {
            Query<?> query = session.createQuery("delete from Color");
            query.executeUpdate();
            return null;
        });
    }

    @Override
    public void deleteByEntity(Color color) {
        executeInTransaction(session -> {
            session.delete(color);
            return null;
        });
    }

    @Override
    public Color update(Color color) {
        return executeInTransaction(session -> {
            return (Color) session.merge(color);
        });
    }

    @FunctionalInterface
    private interface TransactionAction<T> {
        T execute(Session session);
    }
}


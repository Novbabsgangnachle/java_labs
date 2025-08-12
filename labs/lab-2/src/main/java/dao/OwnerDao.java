package dao;

import domain.Owner;
import domain.Pet;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.Collection;

@Transactional
public class OwnerDao implements Dao<Owner> {

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
    public Owner getById(long id) {
        return executeInTransaction(session -> session.find(Owner.class, id));
    }

    @Override
    public Collection<Owner> getAll() {
        return executeInTransaction(session -> session.createQuery("from Owner", Owner.class).list());
    }

    @Override
    public Owner save(Owner owner) {
        return executeInTransaction(session -> {
            session.save(owner);
            return owner;
        });
    }

    public Owner saveWithId(Owner owner, long id) {
        return executeInTransaction(session -> {
            String sql = "INSERT INTO owners (id, name, birth_date) VALUES (:id, :name, :birthDate)";
            Query query = session.createNativeQuery(sql, Pet.class);

            query.setParameter("id", id);
            query.setParameter("name", owner.getName());
            query.setParameter("birthDate", owner.getBirthDate());

            query.executeUpdate();

            return owner;
        });
    }

    @Override
    public void deleteById(long id) {
        executeInTransaction(session -> {
            Owner owner = getById(id);
            if (owner != null) {
                session.delete(owner);
            }
            return null;
        });
    }

    @Override
    public void deleteAll() {
        executeInTransaction(session -> {
            Query<?> query = session.createQuery("delete from Owner");
            query.executeUpdate();
            return null;
        });
    }

    @Override
    public void deleteByEntity(Owner owner) {
        executeInTransaction(session -> {
            session.delete(owner);
            return null;
        });
    }

    @Override
    public Owner update(Owner owner) {
        return executeInTransaction(session -> {
            return (Owner) session.merge(owner);
        });
    }

    @FunctionalInterface
    private interface TransactionAction<T> {
        T execute(Session session);
    }
}
package dao;

import domain.Pet;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.Collection;

@Transactional
public class PetDao implements Dao<Pet> {

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
    public Pet getById(long id) {
        return executeInTransaction(session -> session.find(Pet.class, id));
    }

    @Override
    public Collection<Pet> getAll() {
        return executeInTransaction(session -> session.createQuery("from Pet", Pet.class).list());
    }

    @Override
    public Pet save(Pet pet) {
        return executeInTransaction(session -> {
            session.save(pet);
            return pet;
        });
    }

    public Pet saveWithId(Pet pet, long id) {
        return executeInTransaction(session -> {
            String sql = "INSERT INTO pets (id, name, birth_date, breed, color_id, owner_id) VALUES (:id, :name, :birthDate, :breed, :colorId, :ownerId)";
            Query query = session.createNativeQuery(sql, Pet.class);

            query.setParameter("id", id);
            query.setParameter("name", pet.getName());
            query.setParameter("birthDate", pet.getBirthDate());
            query.setParameter("breed", pet.getBreed());
            query.setParameter("colorId", pet.getColor().getId());
            query.setParameter("ownerId", pet.getOwner().getId());

            query.executeUpdate();

            return pet;
        });
    }

    @Override
    public void deleteById(long id) {
        executeInTransaction(session -> {
            Pet pet = getById(id);
            if (pet != null) {
                session.delete(pet);
            }
            return null;
        });
    }

    @Override
    public void deleteAll() {
        executeInTransaction(session -> {
            Query query = session.createQuery("delete from Pet");
            query.executeUpdate();
            return null;
        });
    }

    @Override
    public void deleteByEntity(Pet pet) {
        executeInTransaction(session -> {
            session.delete(pet);
            return null;
        });
    }

    @Override
    public Pet update(Pet pet) {
        return executeInTransaction(session -> {
            return session.merge(pet);
        });
    }

    @FunctionalInterface
    private interface TransactionAction<T> {
        T execute(Session session);
    }
}
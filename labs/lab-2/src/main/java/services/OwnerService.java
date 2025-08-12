package services;

import dao.OwnerDao;
import domain.Owner;
import domain.Pet;
import org.hibernate.Hibernate;

import java.util.Collection;

public class OwnerService {

    private final OwnerDao ownerDao;

    public OwnerService(OwnerDao ownerDao) {
        this.ownerDao = ownerDao;
    }

    public Owner getOwnerById(long id) {
        return ownerDao.getById(id);
    }

    public Collection<Owner> getAllOwners() {
        return ownerDao.getAll();
    }

    public Owner saveOwner(Owner owner) {
        return ownerDao.save(owner);
    }

    public void deleteOwnerById(long id) {
        ownerDao.deleteById(id);
    }

    public void deleteAllOwners() {
        ownerDao.deleteAll();
    }

    public void deleteOwnerByEntity(Owner owner) {
        ownerDao.deleteByEntity(owner);
    }

    public Owner updateOwner(Owner owner) {
        return ownerDao.update(owner);
    }

    public Owner addPet(Owner owner, Pet pet) {
        owner.addPet(pet);

        return ownerDao.update(owner);
    }

    public Owner removePet(Owner owner, Pet pet) {
        owner.removePet(pet);

        return ownerDao.update(owner);
    }

    public Collection<Pet> getPetsByOwner(Owner owner) {
        return owner.getPets();
    }
}
package services;

import dao.PetDao;
import domain.Pet;
import org.hibernate.Hibernate;

import java.util.Collection;

public class PetService {

    private final PetDao petDao;

    public PetService(PetDao petDao) {
        this.petDao = petDao;
    }

    public Pet getPetById(long id) {
        return petDao.getById(id);
    }

    public Collection<Pet> getAllPets() {
        return petDao.getAll();
    }

    public Pet savePet(Pet pet) {
        return petDao.save(pet);
    }

    public void deletePetById(long id) {
        petDao.deleteById(id);
    }

    public void deleteAllPets() {
        petDao.deleteAll();
    }

    public void deletePetByEntity(Pet pet) {
        petDao.deleteByEntity(pet);
    }

    public Pet updatePet(Pet pet) {
        return petDao.update(pet);
    }

    public Pet addFriend(Pet pet, Pet friend) {
        pet.addFriend(friend);
        friend.addFriend(pet);

        petDao.update(friend);
        return petDao.update(pet);
    }

    public Pet removeFriend(Pet pet, Pet friend) {
        pet.removeFriend(friend);
        friend.removeFriend(pet);

        petDao.update(friend);
        return petDao.update(pet);
    }

    public Collection<Pet> getFriends(Pet pet) {
        return pet.getFriends();
    }
}
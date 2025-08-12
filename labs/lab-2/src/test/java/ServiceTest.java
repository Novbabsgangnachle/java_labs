import dao.*;
import domain.*;
import services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetDao petDao;

    @InjectMocks
    private PetService petService;

    private Pet pet;
    private Pet friend;

    @BeforeEach
    void init() {
        pet = new Pet(1L, "Bobby", "Bulldog", null, null, LocalDate.now());
        friend = new Pet(2L, "Lucky", "Beagle", null, null, LocalDate.now());
    }

    @Test
    @DisplayName("savePet() should delegate to DAO and return saved entity")
    void savePet() {
        when(petDao.save(pet)).thenReturn(pet);

        Pet saved = petService.savePet(pet);

        verify(petDao).save(pet);
        assertEquals("Bobby", saved.getName());
    }

    @Test
    @DisplayName("deletePetById() should call DAO deleteById once")
    void deletePetById() {
        petService.deletePetById(1L);
        verify(petDao, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("addFriend() should add friendship both ways and update both pets")
    void addFriend() {
        when(petDao.update(any(Pet.class))).thenAnswer(inv -> inv.getArgument(0));

        Pet result = petService.addFriend(pet, friend);

        assertTrue(pet.getFriends().contains(friend));
        assertTrue(friend.getFriends().contains(pet));

        verify(petDao).update(friend);
        verify(petDao).update(pet);
        assertEquals(pet, result);
    }
}

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerDao ownerDao;

    @InjectMocks
    private OwnerService ownerService;

    private Owner owner;
    private Pet pet;

    @BeforeEach
    void setUp() {
        owner = new Owner(1L, "John", LocalDate.of(1990, 1, 1));
        pet = new Pet(1L, "Bobby", "Bulldog", null, owner, LocalDate.now());
    }

    @Test
    @DisplayName("addPet() should link pet to owner and persist change")
    void addPet() {
        when(ownerDao.update(owner)).thenReturn(owner);

        Owner updated = ownerService.addPet(owner, pet);

        assertTrue(owner.getPets().contains(pet));
        verify(ownerDao).update(owner);
        assertEquals(owner, updated);
    }

    @Test
    @DisplayName("deleteOwnerById() delegates to DAO")
    void deleteOwnerById() {
        ownerService.deleteOwnerById(4L);
        verify(ownerDao).deleteById(4L);
    }

    @Test
    @DisplayName("updateOwner() returns updated entity")
    void updateOwner() {
        owner.setName("Jack");
        when(ownerDao.update(owner)).thenReturn(owner);

        Owner up = ownerService.updateOwner(owner);
        assertEquals("Jack", up.getName());
        verify(ownerDao).update(owner);
    }
}


@ExtendWith(MockitoExtension.class)
class ColorServiceTest {

    @Mock
    private ColorDao colorDao;

    @InjectMocks
    private ColorService colorService;

    @Test
    @DisplayName("saveColor() returns saved entity from DAO")
    void saveColor() {
        Color c = new Color(1L, "Black");
        when(colorDao.save(c)).thenReturn(c);

        Color saved = colorService.saveColor(c);

        verify(colorDao).save(c);
        assertSame(c, saved);
    }

    @Test
    @DisplayName("getColorById() propagates DAO result")
    void getColorById() {
        Color c = new Color(2L, "White");
        when(colorDao.getById(2L)).thenReturn(c);

        Color fetched = colorService.getColorById(2L);
        assertEquals("White", fetched.getColorName());
    }

    @Test
    @DisplayName("deleteAllColors() calls DAO deleteAll() exactly once")
    void deleteAllColors() {
        colorService.deleteAllColors();
        verify(colorDao, times(1)).deleteAll();
    }
}

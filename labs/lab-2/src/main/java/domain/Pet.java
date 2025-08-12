package domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String breed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "pet_friends",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private final List<Pet> friends = new ArrayList<>();

    public Pet(String name, String breed, Color color, Owner owner, LocalDate birthDate) {
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.birthDate = birthDate;
    }

    public Pet(long id, String name, String breed, Color color, Owner owner, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.birthDate = birthDate;
    }

    public void addFriend(Pet pet) {
        friends.add(pet);
    }

    public void removeFriend(Pet pet) {
        friends.remove(pet);
    }

    @PreRemove
    private void removeFriendsBeforeDelete() {
        List<Pet> friendsCopy = new ArrayList<>(friends);
        for (Pet friend : friendsCopy) {
            friend.getFriends().remove(this);
            friends.remove(friend);
        }

    }
}

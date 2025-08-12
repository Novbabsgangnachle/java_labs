package domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Getter
@Setter
@Table(name = "colors")
@AllArgsConstructor
@NoArgsConstructor
@Immutable
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "color_name", nullable = false)
    private String colorName;

    public Color(String colorName) {
        this.colorName = colorName;
    }
}

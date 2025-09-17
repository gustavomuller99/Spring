package tacos.web.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placedAt;

    @NotBlank
    private String name;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String zip;

    @NotBlank
    private String ccNumber;

    @NotBlank
    private String ccExpiration;

    @NotBlank
    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos;

    @ManyToOne
    private User user;

    public void addTaco(Taco taco) {
        if (tacos == null) {
            tacos = new ArrayList<>();
        }
        tacos.add(taco);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}

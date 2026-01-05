package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<BasketProduct> basketProducts = new ArrayList<>();

    public Basket(int id) {
        this.id = id;
    }
}
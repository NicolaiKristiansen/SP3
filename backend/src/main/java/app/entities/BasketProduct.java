package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class BasketProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Basket basket;

    @ManyToOne
    private Product product;

    private double amount;

    private double price;

    public BasketProduct(Basket basket, Product product, double amount, double price) {
        this.basket = basket;
        this.product = product;
        this.amount = amount;
        this.price = price;
    }
}

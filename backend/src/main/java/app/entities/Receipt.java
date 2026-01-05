package app.entities;

import app.dtos.receipt.ReceiptDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode


@Entity
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double totalPrice;

    @OneToOne
    private Basket basket;

    public Receipt(double totalPrice, Basket basket) {
        this.totalPrice = totalPrice;
        this.basket = basket;
    }

    public Receipt(ReceiptDTO receiptDTO) {
        this.id = receiptDTO.getId();
        this.totalPrice = receiptDTO.getTotalPrice();
    }
}

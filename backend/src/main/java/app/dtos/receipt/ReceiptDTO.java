package app.dtos.receipt;


import app.entities.Receipt;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@ToString
@Builder
@AllArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiptDTO {
    public int id;
    public double totalPrice;
    public int basketId;


    public ReceiptDTO(Receipt receipt){
        this.id = receipt.getId();
        this.totalPrice = receipt.getTotalPrice();
        this.basketId = receipt.getId();
    }


    public static List<ReceiptDTO> toDTOlist(List<Receipt> ResultReceipts){
        return ResultReceipts.stream().map(ReceiptDTO::new).toList();
    }
}

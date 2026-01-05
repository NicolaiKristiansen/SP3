package app.controllers;

import app.config.HibernateConfig;
import app.daos.BasketDAO;
import app.daos.BasketProductDAO;
import app.dtos.basket_product.BasketProductDTO;
import app.dtos.basket_product.BasketProductResponseDTO;
import app.entities.BasketProduct;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class BasketProductController {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    private BasketProductDAO basketProductDAO = new BasketProductDAO(emf);
    private BasketDAO basketDAO = new BasketDAO(emf);

    public void getProductsFromBasket(Context ctx) {
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            List<BasketProduct> basketProducts = basketDAO.getProductsFromBasket(id);

            List<BasketProductDTO> basketProductDTOList = basketProducts.stream()
                    .map(basketProduct -> new BasketProductDTO(
                            basketProduct.getId(),
                            basketProduct.getBasket().getId(),
                            basketProduct.getProduct().getName(),
                            basketProduct.getAmount(),
                            basketProduct.getPrice()
                    ))
                    .collect(Collectors.toList());

            BasketProductResponseDTO responseDTO = new BasketProductResponseDTO(basketProductDTOList);

            ctx.status(HttpStatus.OK);
            ctx.json(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Kunne ikke hente produkter");
        }
    }
}

package app.controllers;

import app.config.HibernateConfig;
import app.daos.BasketDAO;
import app.daos.BasketProductDAO;
import app.daos.ProductDAO;
import app.daos.ReceiptDAO;
import app.dtos.basket.BasketRequestDTO;
import app.dtos.receipt.ReceiptDTO;
import app.entities.Basket;
import app.entities.BasketProduct;
import app.entities.Product;
import app.entities.Receipt;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class BasketController {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    private ReceiptDAO receiptDAO = new ReceiptDAO(emf);
    private BasketDAO basketDAO = new BasketDAO(emf);
    private ProductDAO productDAO = new ProductDAO(emf);
    private BasketProductDAO basketProductDAO = new BasketProductDAO(emf);

    public void addProductToBasket(Context ctx) {
        BasketRequestDTO dto = ctx.bodyAsClass(BasketRequestDTO.class);
        Basket basket = basketDAO.findById(dto.getBasketId());

        if(basket == null) {
            basket = basketDAO.create(new Basket());
        }

        Product product = productDAO.findById(dto.getProductId());
        double price = dto.getAmount() * product.getPrice();
        BasketProduct basketProduct = new BasketProduct(basket, product, dto.getAmount(), price);

        basketProductDAO.create(basketProduct);
        ctx.status(HttpStatus.OK);
    }

    public void buyBasket(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));

        Basket basket = basketDAO.findById(id);
        if (basket == null) {
            ctx.status(404).result("Basket not found");
            return;
        }
        double basketTotalPrice = 0; // You can replace this with a method that calculates total
        List<BasketProduct> basketProducts = basketDAO.getProductsFromBasket(id);
        for (BasketProduct basketProduct : basketProducts) {
            basketTotalPrice += basketProduct.getPrice();
        }


        Receipt receipt = new Receipt(basketTotalPrice, basket);

        receiptDAO.create(receipt);

        ctx.status(201).result("Receipt created successfully with ID: " + receipt.getId());
    }

}

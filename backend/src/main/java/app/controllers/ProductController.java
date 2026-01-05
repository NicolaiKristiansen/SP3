package app.controllers;

import app.config.HibernateConfig;
import app.daos.ProductDAO;
import app.dtos.product.ProductDTO;
import app.dtos.product.ProductRequestDTO;
import app.dtos.product.ProductResponseDTO;
import app.dtos.product.ProductsResponseDTO;
import app.entities.Product;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {

    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    private final ProductDAO productDAO = new ProductDAO(emf);

    private static final Logger logger = LoggerFactory.getLogger(BasketController.class);
    private static final Logger debugLogger = LoggerFactory.getLogger("app");

    public void create(Context ctx) {
        try {
            ProductRequestDTO requestDTO = ctx.bodyAsClass(ProductRequestDTO.class);
            Product product = new Product(
                    requestDTO.getName(),
                    requestDTO.getPrice(),
                    requestDTO.getCategory());

            productDAO.create(product);

            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setProductId(product.getId());
            responseDTO.setName(product.getName());
            responseDTO.setPrice(product.getPrice());
            responseDTO.setCategory(product.getCategory());
            ctx.status(HttpStatus.CREATED);
            ctx.json(responseDTO);

        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            ctx.status(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error(e.getMessage());
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void findByID(Context ctx){
        try {
            Integer id = Integer.parseInt(ctx.pathParam("id"));
            Product product = productDAO.findById(id);
            if (product == null) {
                ctx.status(HttpStatus.NOT_FOUND).result("Product not found");
            } else {
                ProductResponseDTO dto = new ProductResponseDTO();
                dto.setProductId(product.getId());
                dto.setName(product.getName());
                dto.setPrice(product.getPrice());
                dto.setCategory(product.getCategory());
                ctx.status(HttpStatus.OK);
                ctx.json(dto);
            }
        }catch (NumberFormatException numberError){
            ctx.status(HttpStatus.BAD_REQUEST).result("Invalid product id");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getAll(Context ctx) {
        try {
            List<Product> products = productDAO.getAllProducts();

            List<ProductDTO> productDTOs = products.stream()
                    .map(product -> new ProductDTO(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getCategory()
                    ))
                    .collect(Collectors.toList());

            ProductsResponseDTO responseDTO = new ProductsResponseDTO(productDTOs);

            ctx.status(HttpStatus.OK);
            ctx.json(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("Kunne ikke hente produkter");
        }
    }

    public void update(Context ctx) {
        Integer id = Integer.parseInt(ctx.pathParam("id"));
        ProductRequestDTO requestDTO = ctx.bodyAsClass(ProductRequestDTO.class);

        Product check = productDAO.findById(id);

        if (check != null) {
            check.setName(requestDTO.getName());
            check.setPrice(requestDTO.getPrice());
            check.setCategory(requestDTO.getCategory());

            Product updatedProduct = productDAO.update(check, id);

            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setProductId(updatedProduct.getId());
            responseDTO.setName(updatedProduct.getName());
            responseDTO.setPrice(updatedProduct.getPrice());
            responseDTO.setCategory(updatedProduct.getCategory());

            ctx.status(HttpStatus.OK);
            ctx.json(responseDTO);
        } else {
            ctx.status(HttpStatus.BAD_REQUEST).result("Invalid product id");
        }
    }

    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        productDAO.delete(id);
        ctx.result("Product with id " + id + " deleted");
    }
}

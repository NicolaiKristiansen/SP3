package app.populators;

import app.config.HibernateConfig;
import app.daos.ProductDAO;
import app.entities.Product;
import app.enums.Category;

import java.util.ArrayList;

public class PopulateProduct {

    private static ArrayList<Product> products = new ArrayList<>();

    public static void main(String[] args){
        ProductDAO productDAO = new ProductDAO(HibernateConfig.getEntityManagerFactory());

        Product product1 = new Product("Crease 3XP digital pen", 200.00, Category.ELECTRONICS);
        Product product2 = new Product("Milk", 18.00, Category.FOOD);
        Product product3 = new Product("Wacom XL Gen3 drawing tablet", 1800.00, Category.ELECTRONICS);
        Product product4 = new Product("Bread Bundle", 54.00, Category.FOOD);
        Product product5 = new Product("Chair", 200.00, Category.FURNITURE);
        Product product6 = new Product("Table", 500.00, Category.FURNITURE);
        Product product7 = new Product("Sofa", 700.00, Category.FURNITURE);


        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);


       for (Product product: products){
           productDAO.create(product);
       }
    }
}

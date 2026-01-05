package app.daos;

import app.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class ProductDAO implements IDAO<Product, Integer>{

    private EntityManagerFactory emf;

    public ProductDAO (EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Product create(Product product) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTransaction ts = em.getTransaction();
            ts.begin();
            em.persist(product);
            ts.commit();
            return product;
        }
    }

    @Override
    public Product findById(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            Product product = em.find(Product.class, id);
            if (product != null) {
                return product;
            } else {
                return null;
            }
        }
    }

    public List<Product> getAllProducts(){
        List<Product> products;
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Product> query= em.createQuery("select p from Product p", Product.class);
            products = query.getResultList();
            return products;
        }
    }

    @Override
    public Product update(Product product, Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Product updatedProduct = em.find(Product.class, id);
            updatedProduct.setId(product.getId());
            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setCategory(product.getCategory());
            Product mergedProduct = em.merge(updatedProduct);
            em.getTransaction().commit();
            return mergedProduct;
        }
    }

    @Override
    public void delete(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            Product deleteProduct = em.find(Product.class, id);
            em.getTransaction().begin();
            em.remove(deleteProduct);
            em.getTransaction().commit();
        }
    }
}

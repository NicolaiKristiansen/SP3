package app.daos;

import app.entities.BasketProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class BasketProductDAO {
    private EntityManagerFactory emf;

    public BasketProductDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public BasketProduct create(BasketProduct basketProduct) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(basketProduct);
            em.getTransaction().commit();
            return basketProduct;
        }
    }
}

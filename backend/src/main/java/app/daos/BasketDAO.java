package app.daos;

import app.entities.Basket;
import app.entities.BasketProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class BasketDAO implements IDAO<Basket, Integer> {
    EntityManagerFactory emf;

    public BasketDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Basket create(Basket basket) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(basket);
            em.getTransaction().commit();
        }
        return basket;
    }

    @Override
    public Basket findById(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            Basket basket = em.find(Basket.class, id);
            if (basket != null) {
                return basket;
            } else {
                return null;
            }
        }
    }

    public List<BasketProduct> getProductsFromBasket(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Basket basket = em.find(Basket.class, id);
            if (basket == null) {
                return null;
            }
            TypedQuery<BasketProduct> query = em.createQuery(
                    "SELECT bp FROM BasketProduct bp WHERE bp.basket.id = :basketId", BasketProduct.class);
            query.setParameter("basketId", id);
            return query.getResultList();
        }
    }

    @Override
    public Basket update(Basket basket, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            Basket deleteBasket = em.find(Basket.class, id);
            em.getTransaction().begin();
            em.remove(deleteBasket);
            em.getTransaction().commit();
        }
    }
}

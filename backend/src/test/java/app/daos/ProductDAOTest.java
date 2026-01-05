package app.daos;

import app.enums.Category;
import app.config.HibernateConfig;
import app.entities.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDAOTest {
    private static ProductDAO dao;
    private static int createdId;

    @BeforeAll
    static void setUp() {
        dao = new ProductDAO(HibernateConfig.getEntityManagerFactoryForTest());
    }

    @Test
    @Order(1)
    void create() {
        Product expected = new Product("Test Object", 2000, Category.ELECTRONICS);
        Product actual = dao.create(expected);
        createdId = actual.getId();

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategory(), actual.getCategory());
    }

    @Test
    @Order(2)
    void findById() {
        Product expected = new Product("Test Object", 2000, Category.ELECTRONICS);
        Product actual = dao.findById(createdId);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategory(), actual.getCategory());
    }

    @Test
    @Order(3)
    void update() {
        Product expected = new Product("New Name", 50.0, Category.FURNITURE);
        expected.setId(createdId);
        Product actual = dao.update(expected, createdId);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategory(), actual.getCategory());
    }

    @Test
    @Order(4)
    void delete() {
        dao.delete(createdId);
        Product found = dao.findById(createdId);
        assertNull(found);
    }
}
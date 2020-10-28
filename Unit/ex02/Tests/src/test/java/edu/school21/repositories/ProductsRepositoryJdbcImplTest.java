package edu.school21.repositories;

import edu.school21.models.Product;
import edu.school21.repository.ProductsRepositoryJdbcImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    private DataSource ds = null;
    List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(asList(new Product(0L, "milk", 10L),
            new Product(1L, "apple", 5L),
            new Product(2L, "computer", 1000L),
            new Product(3L, "BBQ Chicken", 999L),
            new Product(4L, "Sea Bass", 50L)));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(3L, "BBQ Chicken", 999L);
    final Product EXPECTED_DELETE_BY_ID_PRODUCT = new Product(1L, "apple", 5L);
    final Product EXPECTED_UPDATE_PRODUCT = new Product(2L, "mac", 2000L);
    final Product EXPECTED_SAVE_PRODUCT = new Product(5L, "cake", 22L);


    @BeforeEach
    public void init() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        ds = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }

    @Test
    public void testFindAlL() {
        ProductsRepositoryJdbcImpl pr = new ProductsRepositoryJdbcImpl();
        pr.setJdbcTemplate(ds);
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, pr.findAll());
    }

    @Test
    public void testFindById() throws SQLException {
        ProductsRepositoryJdbcImpl pr = new ProductsRepositoryJdbcImpl();
        pr.setConnection(ds.getConnection());
        assertEquals(Optional.of(EXPECTED_FIND_BY_ID_PRODUCT), pr.findById(3L));
    }

    @Test
    public void testUpdate() throws SQLException {
        ProductsRepositoryJdbcImpl pr = new ProductsRepositoryJdbcImpl();
        pr.setConnection(ds.getConnection());
        pr.update(new Product(2L, "mac", 2000L));
        assertEquals(Optional.of(EXPECTED_UPDATE_PRODUCT), pr.findById(2L));
    }


    @Test
    public void testSave() throws SQLException {
        ProductsRepositoryJdbcImpl pr = new ProductsRepositoryJdbcImpl();
        pr.setConnection(ds.getConnection());
        pr.save(new Product(5L, "cake", 22L));
        assertEquals(Optional.of(EXPECTED_SAVE_PRODUCT), pr.findById(5L));
    }

    @Test
    public void testDeleteById() throws SQLException {
        ProductsRepositoryJdbcImpl pr = new ProductsRepositoryJdbcImpl();
        pr.setConnection(ds.getConnection());
        pr.delete(1L);
        assertNotEquals(Optional.of(EXPECTED_DELETE_BY_ID_PRODUCT), pr.findById(1L));
    }
}

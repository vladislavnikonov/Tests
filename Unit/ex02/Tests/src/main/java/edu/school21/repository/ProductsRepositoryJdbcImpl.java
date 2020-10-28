package edu.school21.repository;

import edu.school21.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private JdbcTemplate jdbcTemplate;
    private Connection connection;


    public void setJdbcTemplate(DataSource datasource){
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * from products";
        return this.jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new Product(
                                rs.getLong("product_id"),
                                rs.getString("name"),
                                rs.getLong("price")
                        )
        );
    }

    @Override
    public Optional<Product> findById(Long id) {
        PreparedStatement statement;
        try {
            String SQL_SELECT_BY_ID = "select * from products where product_id = ?";
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                Long price = resultSet.getLong("price");
                return Optional.of(new Product(productId, name, price));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    };

    @Override
    public void update(Product product) {
        PreparedStatement statement;
        try {
            String SQL_UPDATE = "UPDATE products SET name = ?, price = ? where product_id = ?";
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, product.getName());
            statement.setLong(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {
        PreparedStatement statement;
        try {
            String SQL_SAVE = "INSERT INTO products (name, price) VALUES (?, ?)";
            statement = connection.prepareStatement(SQL_SAVE);
            statement.setString(1, product.getName());
            statement.setLong(2, product.getPrice());
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement statement;
        try {
            String SQL_DELETE_BY_ID = "DELETE FROM products WHERE product_id = ?";
            statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}

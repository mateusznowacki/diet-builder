package pl.dietbuilder.dbmanagement;

import pl.dietbuilder.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {
    private final Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void addNewProduct(Product product) {
        String sql = "INSERT INTO product (name, category, energy, carbohydrates, fat, protein) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getCalories());
            statement.setDouble(4, product.getCarbohydrates());
            statement.setDouble(5, product.getFats());
            statement.setDouble(6, product.getProteins());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

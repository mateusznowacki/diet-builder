package pl.dietbuilder.dbmanagement;

import pl.dietbuilder.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
    private final ConnectionManager connectionManager;

    public ProductDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void addNewProduct(Product product) {
        String sql = "INSERT INTO product (name, category, energy, carbohydrates, fat, protein) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getCalories());
            statement.setDouble(4, product.getCarbohydrates());
            statement.setDouble(5, product.getFats());
            statement.setDouble(6, product.getProteins());

            statement.executeUpdate();
            connectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editProduct(Product product) {
        String sql = "UPDATE product SET name = ?, category = ?, energy = ?, carbohydrates = ?, fat = ?, protein = ? WHERE id = ?";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setDouble(3, product.getCalories());
            statement.setDouble(4, product.getCarbohydrates());
            statement.setDouble(5, product.getFats());
            statement.setDouble(6, product.getProteins());
            statement.setInt(7, product.getId());

            statement.executeUpdate();
            connectionManager.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> productsList = new ArrayList<>();

        String sql = "SELECT * FROM product";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                double calories = resultSet.getDouble("energy");
                double carbohydrates = resultSet.getDouble("carbohydrates");
                double fats = resultSet.getDouble("fat");
                double proteins = resultSet.getDouble("protein");

                Product product = new Product(id, name, category, calories, proteins, fats, carbohydrates);
                productsList.add(product);
            }
            connectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;

    }

    public void deleteProduct(int id) {
        try {
            String sql = "DELETE FROM product WHERE id = ?";
            try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                connectionManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

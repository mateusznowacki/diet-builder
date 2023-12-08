package pl.dietbuilder.dbmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {
    private final ConnectionManager connectionManager;

    public CategoryDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public ArrayList<String> getProductCategories() {
        ArrayList<String> categories = new ArrayList<>();

        String sql = "SELECT category FROM categories";

        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String category = resultSet.getString("category");
                categories.add(category);

            }
            connectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    } public ArrayList<String> getMealCategories() {
        ArrayList<String> categories = new ArrayList<>();

        String sql = "SELECT category FROM meal_category";

        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String category = resultSet.getString("category");
                categories.add(category);

            }
            connectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
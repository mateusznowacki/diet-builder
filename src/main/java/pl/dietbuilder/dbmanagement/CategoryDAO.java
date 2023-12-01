package pl.dietbuilder.dbmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {
    private final Connection connection;

    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> categories = new ArrayList<>();

        String sql = "SELECT category FROM categories";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String category = resultSet.getString("category");
                categories.add(category);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
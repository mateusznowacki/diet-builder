package pl.dietbuilder.dbmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class MealDAO {

    private final Connection connection;

    public MealDAO(Connection connection) {
        this.connection = connection;
    }

    public void addMeal(String name, String category,HashMap<Integer, Double> products) {

        while (products.entrySet().iterator().hasNext()) {
            String sql = "INSERT INTO meal (name, product_id, quantity) VALUES (?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, category);
                statement.setInt(3, products.entrySet().iterator().next().getKey());
                statement.setDouble(4, products.entrySet().iterator().next().getValue());

                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteMeal(int id) {
        try {
            String sql = "DELETE FROM meal WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void editMeal() {
    }


}

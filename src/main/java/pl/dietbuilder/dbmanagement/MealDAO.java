package pl.dietbuilder.dbmanagement;

import pl.dietbuilder.model.Meal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealDAO {

    private final Connection connection;

    public MealDAO(Connection connection) {
        this.connection = connection;
    }

    public void addMeal(ArrayList<Meal> meal) {

        for (int i = 0; i < meal.size(); i++) {
            String sql = "INSERT INTO meal (meal_name, product_name, quantity, category) VALUES (?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, meal.get(i).getName());
                statement.setString(2, meal.get(i).getProductName());
                statement.setDouble(3, meal.get(i).getProductAmount());
                statement.setString(4, meal.get(i).getCategory());

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

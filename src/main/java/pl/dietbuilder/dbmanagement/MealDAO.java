package pl.dietbuilder.dbmanagement;

import pl.dietbuilder.model.Meal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealDAO {

    private final ConnectionManager connectionManager;

    public MealDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void addMeal(ArrayList<Meal> meal) {

        for (int i = 0; i < meal.size(); i++) {
            String sql = "INSERT INTO meal (meal_name, product_name, quantity, category) VALUES (?,?,?,?)";
            try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
                statement.setString(1, meal.get(i).getName());
                statement.setString(2, meal.get(i).getProductName());
                statement.setDouble(3, meal.get(i).getProductAmount());
                statement.setString(4, meal.get(i).getCategory());

                statement.executeUpdate();
                connectionManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteMeal(String mealName) {
        try {
            String sql = "DELETE FROM meal WHERE meal_name = ?";
            try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, mealName);

                preparedStatement.executeUpdate();
                connectionManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMealIngredient(String mealName, String productName) {
        try {
            String sql = "DELETE FROM meal WHERE meal_name = ? AND product_name = ?";
            try (PreparedStatement preparedStatement = connectionManager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, mealName);
                preparedStatement.setString(2, productName);

                preparedStatement.executeUpdate();
                connectionManager.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editMeal(String mealName, String productName, Double newAmount) {
        String sql = "UPDATE meal SET  quantity = ? WHERE meal_name = ? AND product_name = ?";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {

            statement.setDouble(1, newAmount);
            statement.setString(2, mealName);
            statement.setString(3, productName);

            statement.executeUpdate();
            connectionManager.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Meal> getAllMeals() {

        ArrayList<Meal> mealsList = new ArrayList<>();
        String sql = "SELECT MIN(id) AS id, meal_name, category FROM meal GROUP BY meal_name, category;";

        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String mealName = resultSet.getString("meal_name");
                String category = resultSet.getString("category");

                Meal meal = new Meal(id, mealName, category);
                mealsList.add(meal);

            }
            connectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mealsList;

    }

    public ArrayList<Meal> getAllMealsIngredients(String name) {

        ArrayList<Meal> mealsList = new ArrayList<>();
        String sql = "SELECT product_name, quantity FROM meal WHERE meal_name = ?";

        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String productName = resultSet.getString("product_name");
                Double quantity = resultSet.getDouble("quantity");

                Meal meal = new Meal(productName, quantity);
                mealsList.add(meal);
            }
            connectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mealsList;
    }

    public void addIngredient(String mealName, String productName, Double productAmount, String category) {

        String sql = "INSERT INTO meal (meal_name, product_name, quantity, category) VALUES (?,?,?,?)";
        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            statement.setString(1, mealName);
            statement.setString(2, productName);
            statement.setDouble(3, productAmount);
            statement.setString(4, category);

            statement.executeUpdate();
            connectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Meal> getMealInfo(String mealName) {
        ArrayList<Meal> mealsList = new ArrayList<>();
        String sql = "SELECT m.meal_name, m.quantity, p.name, p.carbohydrates, p.fat, p.protein, p.energy FROM meal m " +
                "JOIN product p ON m.product_name = p.name WHERE m.meal_name = ?";

        try (PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql)) {
            statement.setString(1, mealName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("m.meal_name");
                Double quantity = resultSet.getDouble("m.quantity");
                String productName = resultSet.getString("p.name");
                Double carbohydrates = resultSet.getDouble("p.carbohydrates");
                Double fat = resultSet.getDouble("p.fat");
                Double protein = resultSet.getDouble("p.protein");
                Double energy = resultSet.getDouble("p.energy");

                mealsList.add(new Meal(name, productName, quantity, energy, protein,fat, carbohydrates));
            }
                connectionManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mealsList;
    }
}

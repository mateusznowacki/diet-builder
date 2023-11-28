package pl.dietbuilder.model;

public class Product extends NutritionalInformation{
    private int id;
    public Product(String name, double calories, double proteins, double fats, double carbohydrates, double fiber, double weight) {
        super(name, calories, proteins, fats, carbohydrates, fiber, weight);
    }

    public Product(int id, String name, double calories, double proteins, double fats, double carbohydrates, double fiber, double weight) {
        super(name, calories, proteins, fats, carbohydrates, fiber, weight);
        this.id = id;
    }
}

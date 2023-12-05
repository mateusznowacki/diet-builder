package pl.dietbuilder.model;

public class Product extends NutritionalInformation {
    private int id;
    private String category;

    public Product(String name, String category, double calories, double proteins, double fats, double carbohydrates) {
        super(name, calories, proteins, fats, carbohydrates);
        this.category = category;
    }

    public Product(int id, String name, String category, double calories, double proteins, double fats, double carbohydrates) {
        super(name, calories, proteins, fats, carbohydrates);
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

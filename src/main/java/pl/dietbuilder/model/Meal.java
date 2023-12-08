package pl.dietbuilder.model;

import java.util.Objects;

public class Meal extends NutritionalInformation {
    private int id;
    private String productName;
    private double productAmount;
    private String category;

    public Meal(String name, String category, double calories, double proteins, double fats, double carbohydrates) {
        super(name, calories, proteins, fats, carbohydrates);
        this.category = category;
    }

    public Meal(String name, String productName, double productAmount, String category) {
        super(name);
        this.productName = productName;
        this.productAmount = productAmount;
        this.category = category;
    }

    public Meal(String productName, double productAmount) {
        this.productName = productName;
        this.productAmount = productAmount;
    }

    public Meal(int id,String mealName, String category) {
        super(mealName);
        this.id = id;
        this.productName = productName;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}

package pl.dietbuilder.model;

import java.util.ArrayList;

public class Meal extends NutritionalInformation {
    private int id;
    private ArrayList<Product> products;

    public Meal(String name, double calories, double proteins, double fats, double carbohydrates) {
        super(name,calories, proteins, fats, carbohydrates);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}

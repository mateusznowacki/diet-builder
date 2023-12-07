package pl.dietbuilder.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Meal extends NutritionalInformation {
    private int id;
    private HashMap<Integer, Double> products;

    public Meal(String name, double calories, double proteins, double fats, double carbohydrates) {
        super(name,calories, proteins, fats, carbohydrates);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, Double> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Integer, Double> products) {
        this.products = products;
    }
}

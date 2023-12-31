package pl.dietbuilder.model;

import java.util.Objects;

public abstract class NutritionalInformation {
    private String name;
    private double calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public NutritionalInformation(String name, double calories, double proteins, double fats, double carbohydrates) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public NutritionalInformation( ) {

    }

    public NutritionalInformation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }


    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionalInformation that = (NutritionalInformation) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

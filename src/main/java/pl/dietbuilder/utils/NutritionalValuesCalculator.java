package pl.dietbuilder.utils;

import pl.dietbuilder.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;

public class NutritionalValuesCalculator {

    public HashMap<String, Double> getNutritionalValues(ArrayList<Meal> mealArrayList) {
        HashMap<String, Double> nutritionalValues = new HashMap<>();
        double calorie = 0;
        double proteins = 0;
        double fats = 0;
        double carbohydrates = 0;

        for (int i = 0; i < mealArrayList.size(); i++) {
            calorie += calculateNutritionalValue(mealArrayList.get(i).getCalories(), mealArrayList.get(i).getProductAmount());
            proteins += calculateNutritionalValue(mealArrayList.get(i).getProteins(), mealArrayList.get(i).getProductAmount());
            fats += calculateNutritionalValue(mealArrayList.get(i).getFats(), mealArrayList.get(i).getProductAmount());
            carbohydrates += calculateNutritionalValue(mealArrayList.get(i).getCarbohydrates(), mealArrayList.get(i).getProductAmount());
        }
        nutritionalValues.put("calories", calorie);
        nutritionalValues.put("proteins", proteins);
        nutritionalValues.put("fats", fats);
        nutritionalValues.put("carbohydrates", carbohydrates);

        return nutritionalValues;
    }

    private double calculateNutritionalValue(double amount, double productAmount) {
        return amount * productAmount / 100;
    }

}

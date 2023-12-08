package pl.dietbuilder.utils;

import java.util.HashMap;

public class NutritionalValuesCalculator {

    public HashMap<String,Double> getNutritionalValues(String productName, double productAmount) {

        //////hjakies dao itp


        HashMap<String,Double> nutritionalValues = new HashMap<>();
        nutritionalValues.put("calories", 0.0);
        nutritionalValues.put("proteins", 0.0);
        nutritionalValues.put("fats", 0.0);
        nutritionalValues.put("carbohydrates", 0.0);
        return nutritionalValues;
    }



}

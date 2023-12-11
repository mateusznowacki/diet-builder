package pl.dietbuilder.utils;


import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.ProductDAO;
import pl.dietbuilder.model.Meal;
import pl.dietbuilder.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PDFGenerator {


    public void generateShoppingList(ArrayList<Meal> meals) {
        Map<String, ArrayList<Product>> categorizedProducts = new HashMap<>();
        ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance());
        ArrayList<Product> products = productDAO.getMealIngredients(meals);

        for (Product product : products) {
            String category = product.getCategory();
            categorizedProducts.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
        }

    }


}


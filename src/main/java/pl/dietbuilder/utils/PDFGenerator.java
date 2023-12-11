package pl.dietbuilder.utils;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.ProductDAO;
import pl.dietbuilder.model.Meal;
import pl.dietbuilder.model.Product;

import java.io.IOException;
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

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);

                for (Map.Entry<String, ArrayList<Product>> entry : categorizedProducts.entrySet()) {
                    // Category header
                    contentStream.showText("Category: " + entry.getKey());
                    contentStream.newLine();

                    // Products in the category
                    for (Product product : entry.getValue()) {
                        contentStream.showText("- " + product.getName());
                        contentStream.newLine();
                    }

                    contentStream.newLine(); // Add a newline between categories
                }

                contentStream.endText();
            }

            document.save("Documents/shoppingList.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





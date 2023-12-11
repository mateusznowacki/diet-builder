package pl.dietbuilder.controllers.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {
    @FXML
    private Button addMeal;

    @FXML
    private Button addProduct;

    @FXML
    private Button addShoppingList;

    @FXML
    private Button deleteMeal;

    @FXML
    private Button deleteProduct;

    @FXML
    private Button editMeal;

    @FXML
    private Button editProduct;

    @FXML
    private Button exitButton;

    @FXML
    private Button seeInfo;

    @FXML
    private StackPane stackPane;

    @FXML
    void closeApplication(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void addMeal(ActionEvent event) {
        loadView("/fxml/addMeal.fxml");
    }

    @FXML
    void seeMealInfo(ActionEvent event) {
        loadView("/fxml/mealInfo.fxml");
    }

    @FXML
    void addProduct(ActionEvent event) {
        loadView("/fxml/addProduct.fxml");
    }

    @FXML
    void addShoppingList(ActionEvent event) {
        loadView("/fxml/addShoppingList.fxml");
    }

    @FXML
    void deleteMeal(ActionEvent event) {
        loadView("/fxml/deleteMeal.fxml");
    }

    @FXML
    void deleteProduct(ActionEvent event) {
        loadView("/fxml/deleteProduct.fxml");
    }

    @FXML
    void editMeal(ActionEvent event) {
        loadView("/fxml/editMeal.fxml");
    }

    @FXML
    void editProduct(ActionEvent event) {
        loadView("/fxml/editProduct.fxml");
    }

    private void loadView(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            stackPane.getChildren().clear();
            stackPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

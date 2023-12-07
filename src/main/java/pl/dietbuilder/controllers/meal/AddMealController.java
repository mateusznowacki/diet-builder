package pl.dietbuilder.controllers.meal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;

public class AddMealController {
    @FXML
    private Button addMeal;

    @FXML
    private Button addToMeal;

    @FXML
    private ChoiceBox<?> categoryChoiceBox;

    @FXML
    private TableColumn<?, ?> categoryColumn;

    @FXML
    private TextField mealName;

    @FXML
    private TableColumn<?, ?> mealTableView;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TextField productAmount;

    @FXML
    private TextFlow productName;

    @FXML
    private TableView<?> productTableView;

    @FXML
    private TextField searchBar;

    @FXML
    void addProductToMeal(ActionEvent event) {

    }

    @FXML
    void addReadyMeal(ActionEvent event) {

    }
}

package pl.dietbuilder.controllers.product;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pl.dietbuilder.dbmanagement.CategoryDAO;
import pl.dietbuilder.dbmanagement.ConnectionManager;

import java.util.ArrayList;

public class AddProductController {

    @FXML
    private Button addButton;

    @FXML
    private TextField carbohydratesAmount;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private TextField fatAmount;

    @FXML
    private TextField productName;

    @FXML
    private ImageView productView;

    @FXML
    private TextField proteinAmount;

    @FXML
    void addNewProduct(ActionEvent event) {

    }

    @FXML
    void setCarbohydratesAmount(ActionEvent event) {

    }

    @FXML
    void setFatAmount(ActionEvent event) {

    }

    @FXML
    void setProductName(ActionEvent event) {

    }

    @FXML
    void setProteinAmount(ActionEvent event) {

    }

    @FXML
    void chooseCategory(MouseEvent event) {
        CategoryDAO categoryDAO = new CategoryDAO(ConnectionManager.getInstance().getConnection());
        System.out.println(categoryDAO.getCategories());
        categoryChoiceBox.getItems().addAll(categoryDAO.getCategories());
    }

}

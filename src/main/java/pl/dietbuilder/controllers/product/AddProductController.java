package pl.dietbuilder.controllers.product;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.dietbuilder.dbmanagement.CategoryDAO;
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.ProductDAO;
import pl.dietbuilder.model.Product;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.dietbuilder.utils.ImageGetter.getFirstImageFromSearch;
import static pl.dietbuilder.utils.NumberFormatter.formatDoubleWithComma;

public class AddProductController implements Initializable {

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
    private TextField energyAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        diableAddButton();
        changeFirstLetterToUpper();
        initalizeChocieBox();
    }

    @FXML
    void setProductView(String query) {

        try {
            getFirstImageFromSearch(query);
            Image image = new Image(getClass().getResource("/icons/downloaded_image.jpg").toExternalForm());
            productView.setImage(image);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void addNewProduct(ActionEvent event) {

        String name = productName.getText();
        String category = categoryChoiceBox.getSelectionModel().getSelectedItem();
        double calories = formatDoubleWithComma(energyAmount.getText());
        double proteins = formatDoubleWithComma(proteinAmount.getText());
        double fats = formatDoubleWithComma(fatAmount.getText());
        double carbohydrates = formatDoubleWithComma(carbohydratesAmount.getText());

        Product product = new Product(name, category, calories, proteins, fats, carbohydrates);

        ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance());
        productDAO.addNewProduct(product);
        clearFields();

    }

    private void diableAddButton() {
        BooleanBinding conditionBinding = productName.textProperty().isEmpty()
                .or(categoryChoiceBox.valueProperty().isNull())
                .or(energyAmount.textProperty().isEmpty())
                .or(carbohydratesAmount.textProperty().isEmpty())
                .or(fatAmount.textProperty().isEmpty())
                .or(proteinAmount.textProperty().isEmpty());

        addButton.disableProperty().bind(conditionBinding);
    }

    private void initalizeChocieBox() {
        CategoryDAO categoryDAO = new CategoryDAO(ConnectionManager.getInstance());
        ObservableList<String> categories = FXCollections.observableArrayList(categoryDAO.getProductCategories());
        categoryChoiceBox.setItems(categories);

    }

    private void changeFirstLetterToUpper() {
        productName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = productName.getText();
                if (!text.isEmpty()) {
                    String modifiedText = text.substring(0, 1).toUpperCase() + text.substring(1);
                    productName.setText(modifiedText);
                    setProductView(productName.getText());
                }
            }
        });
    }

    private void clearFields() {
        productName.clear();
        categoryChoiceBox.getSelectionModel().clearSelection();
        energyAmount.clear();
        proteinAmount.clear();
        fatAmount.clear();
        carbohydratesAmount.clear();
    }
}

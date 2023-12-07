package pl.dietbuilder.controllers.product;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.dietbuilder.dbmanagement.CategoryDAO;
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.ProductDAO;
import pl.dietbuilder.model.Product;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.dietbuilder.utils.NumberFormatter.formatDoubleWithComma;

public class EditProductController implements Initializable {

    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private Button editProduct;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TextField searchBar;
    @FXML
    private TextField carbohydratesAmount;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TextField fatAmount;
    @FXML
    private TextField productName;
    @FXML
    private TextField proteinAmount;
    @FXML
    private TextField energyAmount;

    private ObservableList<Product> productsObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initalizeProductTable();
        initalizeChoiceBox();
        disableEditIfNothingSelected();

    }

    private void disableEditIfNothingSelected() {
        BooleanBinding conditionBinding = Bindings.isNull(productTableView.getSelectionModel().selectedItemProperty());
        editProduct.disableProperty().bind(conditionBinding);
        productName.disableProperty().bind(conditionBinding);
        categoryChoiceBox.disableProperty().bind(conditionBinding);
        energyAmount.disableProperty().bind(conditionBinding);
        carbohydratesAmount.disableProperty().bind(conditionBinding);
        fatAmount.disableProperty().bind(conditionBinding);
        proteinAmount.disableProperty().bind(conditionBinding);
    }

    @FXML
    void editSelected(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            int productId = selectedProduct.getId();
            String name = productName.getText();
            String category = categoryChoiceBox.getSelectionModel().getSelectedItem().toString();
            double calories = formatDoubleWithComma(energyAmount.getText());
            double proteins = formatDoubleWithComma(proteinAmount.getText());
            double fats = formatDoubleWithComma(fatAmount.getText());
            double carbohydrates = formatDoubleWithComma(carbohydratesAmount.getText());

            Product product = new Product(productId, name, category, calories, proteins, fats, carbohydrates);

            ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance().getConnection());
            productDAO.editProduct(product);
        }
    }

    private void initalizeChoiceBox() {
        CategoryDAO categoryDAO = new CategoryDAO(ConnectionManager.getInstance().getConnection());
        ObservableList<String> categories = FXCollections.observableArrayList(categoryDAO.getProductCategories());
        categoryChoiceBox.setItems(categories);
    }

    private void initalizeProductTable() {
        ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance().getConnection());
        productsObservableList.addAll(productDAO.getAllProducts());


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        productTableView.setItems(productsObservableList);
        FilteredList<Product> filteredData = new FilteredList<>(productsObservableList, p -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
            if (newValue.isEmpty() || newValue == null || newValue.isBlank()) {
                return true;
            }

            String keywordSearch = newValue.toLowerCase();
            if (product.getName().toLowerCase().indexOf(keywordSearch) > -1) {
                return true;
            } else if (product.getCategory().toLowerCase().indexOf(keywordSearch) > -1) {
                return true;
            } else if (String.valueOf(product.getId()).indexOf(keywordSearch) > -1) {
                return true;
            } else {
                return false;
            }
        }));
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
        productTableView.setItems(sortedData);
    }
}



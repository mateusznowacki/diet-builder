package pl.dietbuilder.controllers.meal;

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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import pl.dietbuilder.dbmanagement.CategoryDAO;
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.MealDAO;
import pl.dietbuilder.dbmanagement.ProductDAO;
import pl.dietbuilder.model.Meal;
import pl.dietbuilder.model.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static pl.dietbuilder.utils.NumberFormatter.formatDoubleWithComma;

public class AddMealController implements Initializable {
    @FXML
    private Button addMeal;

    @FXML
    private Button addToMeal;

    @FXML
    private TableView<Meal> mealTableView;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TextField mealName;

    @FXML
    private TableColumn<Product, String> productNameMealView;
    @FXML
    private TableColumn<Product, String> productAmountMealView;

    @FXML
    private TextField productAmount;

    @FXML
    private TextFlow productName;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TextField searchBar;

    private ObservableList<Product> productsObservableList = FXCollections.observableArrayList();
    private ObservableList<Meal> mealsObservableList = FXCollections.observableArrayList();
    private ArrayList<Meal> mealIngredients = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize tables
        initalizeProductTable();
        initializeMealTable();

        //initialize choicebox with categories
        initalizeChocieBox();

        //initialize product name text
        initializeProductNameText();

        //disabble confirm if nothing selected and delete if not confirmed
        disableButtons();
    }

    private void disableButtons() {
        BooleanBinding mealNameBinding = Bindings.isEmpty(mealName.textProperty());
        BooleanBinding productAmountBinding = Bindings.isEmpty(productAmount.textProperty());
        BooleanBinding categoryChoiceBoxBinding = Bindings.isNull(categoryChoiceBox.getSelectionModel().selectedItemProperty());
        BooleanBinding productTableViewBinding = Bindings.isNull(productTableView.getSelectionModel().selectedItemProperty());

        addToMeal.disableProperty().bind(mealNameBinding.or(productAmountBinding).or(productTableViewBinding).or(categoryChoiceBoxBinding));
        addMeal.disableProperty().bind(mealNameBinding.or(productAmountBinding).or(productTableViewBinding).or(categoryChoiceBoxBinding));
    }

    private void initializeMealTable() {

        productNameMealView.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productAmountMealView.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        mealTableView.setItems(mealsObservableList);

    }

    private void initializeProductNameText() {
        productTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                productName.getChildren().clear();
                Text text = new Text(newSelection.getName());
                productName.getChildren().add(text);
            }
        });
    }

    @FXML
    void addProductToMeal(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            String name = mealName.getText();
            String category = categoryChoiceBox.getSelectionModel().getSelectedItem().toString();
            String product = productTableView.getSelectionModel().getSelectedItem().getName();
            Double amount = formatDoubleWithComma(productAmount.getText());
            Meal ingredients = new Meal(product, amount);
            Meal mealPart = new Meal(name, product, amount, category);
            mealsObservableList.add(ingredients);
            mealIngredients.add(mealPart);
        }
    }

    @FXML
    void addReadyMeal(ActionEvent event) {

        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());
        mealDAO.addMeal(mealIngredients);
        // clear fields after adding meal
        clearFields();
    }

    private void clearFields() {
        mealIngredients.clear();
        mealsObservableList.clear();
        productAmount.clear();
        productName.getChildren().clear();
        mealName.clear();
        categoryChoiceBox.getSelectionModel().clearSelection();
    }

    private void initalizeProductTable() {
        ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance());
        productsObservableList.addAll(productDAO.getAllProducts());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        productTableView.setItems(productsObservableList);
        FilteredList<Product> filteredData = new FilteredList<>(productsObservableList, p -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(product -> {
            if (newValue.isEmpty() || newValue == null || newValue.isBlank()) {
                return true;
            }

            String keywordSearch = newValue.toLowerCase();
            if (product.getName().toLowerCase().contains(keywordSearch)) {
                return true;
            } else if (product.getCategory().toLowerCase().contains(keywordSearch)) {
                return true;
            } else if (String.valueOf(product.getId()).contains(keywordSearch)) {
                return true;
            } else {
                return false;
            }
        }));
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
        productTableView.setItems(sortedData);
    }

    private void initalizeChocieBox() {
        CategoryDAO categoryDAO = new CategoryDAO(ConnectionManager.getInstance());
        ObservableList<String> categories = FXCollections.observableArrayList(categoryDAO.getMealCategories());
        categoryChoiceBox.setItems(categories);
    }

}

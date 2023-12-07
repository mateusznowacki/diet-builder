package pl.dietbuilder.controllers.meal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import java.util.HashMap;
import java.util.ResourceBundle;

import static pl.dietbuilder.utils.NumberFormatter.formatDoubleWithComma;

public class AddMealController implements Initializable {
    @FXML
    private Button addMeal;

    @FXML
    private Button addToMeal;

    @FXML
    private TableView<String> mealTableView;

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
        // disableIfNotSelectedConfirmed();

    }

    private void initializeMealTable() {

        productNameMealView.setCellValueFactory(new PropertyValueFactory<>("name"));
        productAmountMealView.setCellValueFactory(new PropertyValueFactory<>("amount"));


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
        Integer id = productTableView.getSelectionModel().getSelectedItem().getId();
        Double amount = formatDoubleWithComma(productAmount.getText());

        pr.put(id,amount);
    }

    @FXML
    void addReadyMeal(ActionEvent event) {

        String name = mealName.getText();
        String category = categoryChoiceBox.getSelectionModel().getSelectedItem().toString();

        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance().getConnection());
        mealDAO.addMeal(name,category, (HashMap<Integer, Double>) productsObservableMap);

    }

    private void initalizeProductTable() {
        ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance().getConnection());
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

    private void initalizeChocieBox() {
        CategoryDAO categoryDAO = new CategoryDAO(ConnectionManager.getInstance().getConnection());
        ObservableList<String> categories = FXCollections.observableArrayList(categoryDAO.getMealCategories());
        categoryChoiceBox.setItems(categories);
    }

}

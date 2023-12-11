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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.MealDAO;
import pl.dietbuilder.dbmanagement.ProductDAO;
import pl.dietbuilder.model.Meal;
import pl.dietbuilder.model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMealController implements Initializable {

    @FXML
    private Button addProductToMeal;

    @FXML
    private TableColumn<Meal, Double> amountTable;

    @FXML
    private Button editMeal;

    @FXML
    private TableView<Meal> mealingredientsTV;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TextField newProductAmount;

    @FXML
    private TextField productAmount;

    @FXML
    private TextFlow productName;

    @FXML
    private TextField searchBarMeal;

    @FXML
    private TableColumn<Meal, String> mealName;

    @FXML
    private TableColumn<Meal, String> productNameTable;

    @FXML
    private TableView<Product> productTV;

    @FXML
    private Button removeProduct;

    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Meal> mealsTableView;

    private final ObservableList<Meal> mealObservableList = FXCollections.observableArrayList();
    private final ObservableList<Meal> mealIngredientsObservableList = FXCollections.observableArrayList();
    private final ObservableList<Product> productsObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTables();
        disableButtons();
    }

    private void initializeTables() {
        initalizeMealSearchTable();
        initalizeMealIngredientsTV();
        initializeProductNameText();
        initalizeProductTable();
    }

    private void disableButtons() {
        disableAddButton();
        disableRemoveButton();
        disableEditButton();
    }

    @FXML
    void addNewProduct(ActionEvent event) {

        String mealName = mealsTableView.getSelectionModel().getSelectedItem().getName();
        String productName = productTV.getSelectionModel().getSelectedItem().getName();
        String category = mealsTableView.getSelectionModel().getSelectedItem().getCategory();
        Double productAmount = Double.parseDouble(newProductAmount.getText());

        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());
        mealDAO.addIngredient(mealName, productName, productAmount, category);
        refreshMealIngredientsTV(mealName);

        newProductAmount.clear();
    }

    @FXML
    void removeProductFromMeal(ActionEvent event) {
        String mealName = mealsTableView.getSelectionModel().getSelectedItem().getName();
        String productName = mealingredientsTV.getSelectionModel().getSelectedItem().getProductName();

        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());
        mealDAO.deleteMealIngredient(mealName, productName);
        mealIngredientsObservableList.remove(mealingredientsTV.getSelectionModel().getSelectedItem());

        this.productName.getChildren().clear();
    }

    @FXML
    void saveEditChanges(ActionEvent event) {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());

        String mealName = mealsTableView.getSelectionModel().getSelectedItem().getName();
        String editedIngredient = mealingredientsTV.getSelectionModel().getSelectedItem().getProductName();
        Double newAmount = Double.parseDouble(productAmount.getText());

        mealDAO.editMeal(mealName, editedIngredient, newAmount);
        refreshMealIngredientsTV(mealName);

        productName.getChildren().clear();
        productAmount.clear();
    }

    private void initializeProductNameText() {
        mealingredientsTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                productName.getChildren().clear();
                Text text = new Text(newSelection.getProductName());
                productName.getChildren().add(text);
            }
        });
    }

    private void initalizeMealSearchTable() {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());
        mealObservableList.addAll(mealDAO.getAllMeals());

        mealName.setCellValueFactory(new PropertyValueFactory<>("name"));

        mealsTableView.setItems(mealObservableList);
        FilteredList<Meal> filteredData = new FilteredList<>(mealObservableList, p -> true);
        searchBarMeal.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(meal -> {
            if (newValue.isEmpty() || newValue == null || newValue.isBlank()) {
                return true;
            }
            String keywordSearch = newValue.toLowerCase();
            if (meal.getName().toLowerCase().contains(keywordSearch)) {
                return true;
            } else if (meal.getCategory().toLowerCase().contains(keywordSearch)) {
                return true;
            } else return String.valueOf(meal.getId()).contains(keywordSearch);
        }));
        SortedList<Meal> sortedData = new SortedList<Meal>(filteredData);
        sortedData.comparatorProperty().bind(mealsTableView.comparatorProperty());
        mealsTableView.setItems(sortedData);
    }

    private void initalizeMealIngredientsTV() {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());

        mealsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String selectedMealName = newSelection.getName();
                mealIngredientsObservableList.clear();
                mealIngredientsObservableList.addAll(mealDAO.getAllMealsIngredients(selectedMealName));
            }
        });

        productNameTable.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amountTable.setCellValueFactory(new PropertyValueFactory<>("productAmount"));

        mealingredientsTV.setItems(mealIngredientsObservableList);
    }

    private void refreshMealIngredientsTV(String mealName) {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());

        mealIngredientsObservableList.clear();
        mealIngredientsObservableList.addAll(mealDAO.getAllMealsIngredients(mealName));

        productNameTable.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amountTable.setCellValueFactory(new PropertyValueFactory<>("productAmount"));

        mealingredientsTV.setItems(mealIngredientsObservableList);
    }

    private void initalizeProductTable() {
        ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance());
        productsObservableList.addAll(productDAO.getAllProducts());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        productTV.setItems(productsObservableList);
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
            } else return String.valueOf(product.getId()).contains(keywordSearch);
        }));
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTV.comparatorProperty());
        productTV.setItems(sortedData);
    }

    private void disableEditButton() {
        BooleanBinding mealTableBind = Bindings.isNull(mealsTableView.getSelectionModel().selectedItemProperty());
        BooleanBinding mealIngredientsTableBind = Bindings.isNull(mealingredientsTV.getSelectionModel().selectedItemProperty());
        BooleanBinding productAmountBind = productAmount.textProperty().isEmpty();

        editMeal.disableProperty().bind(mealTableBind.or(mealIngredientsTableBind).or(productAmountBind));
    }

    private void disableRemoveButton() {
        BooleanBinding mealTableBind = Bindings.isNull(mealsTableView.getSelectionModel().selectedItemProperty());
        BooleanBinding mealIngredientsTableBind = Bindings.isNull(mealingredientsTV.getSelectionModel().selectedItemProperty());

        removeProduct.disableProperty().bind(mealTableBind.or(mealIngredientsTableBind));
    }

    private void disableAddButton() {
        BooleanBinding productTableBind = Bindings.isNull(productTV.getSelectionModel().selectedItemProperty());
        BooleanBinding productAmountBind = newProductAmount.textProperty().isEmpty();
        BooleanBinding mealTableBind = Bindings.isNull(mealsTableView.getSelectionModel().selectedItemProperty());

        addProductToMeal.disableProperty().bind(productTableBind.or(productAmountBind).or(mealTableBind));
    }

}

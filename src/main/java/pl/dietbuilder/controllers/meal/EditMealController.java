package pl.dietbuilder.controllers.meal;

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

    private ObservableList<Meal> mealObservableList = FXCollections.observableArrayList();
    private ObservableList<Meal> mealIngredientsObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Meal> mealsTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initalizeMealSearchTable();
        initalizeMealIngredientsTV();
        initializeProductNameText();

    }

    @FXML
    void addNewProduct(ActionEvent event) {

    }

    @FXML
    void removeProductFromMeal(ActionEvent event) {

    }

    @FXML
    void saveEditChanges(ActionEvent event) {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());

        mealDAO.editMeal();

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
            } else if (String.valueOf(meal.getId()).contains(keywordSearch)) {
                return true;
            } else {
                return false;
            }
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

}

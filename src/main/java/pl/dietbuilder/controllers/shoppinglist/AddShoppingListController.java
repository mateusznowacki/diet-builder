package pl.dietbuilder.controllers.shoppinglist;

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
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.MealDAO;
import pl.dietbuilder.model.Meal;
import pl.dietbuilder.utils.PDFGenerator;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddShoppingListController implements Initializable {
    @FXML
    private Button addToList;

    @FXML
    private TableColumn<Meal, String> categoryColumn;

    @FXML
    private Button generateShoppingList;

    @FXML
    private TableView<Meal> listTable;

    @FXML
    private TableColumn<Meal, String> mealName;

    @FXML
    private TableView<Meal> mealTable;

    @FXML
    private TableColumn<Meal, String> nameColumn;

    @FXML
    private TextField searchBar;

    private  ObservableList<Meal> mealObservableList = FXCollections.observableArrayList();
    private  ObservableList<Meal> shoppingObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initalizeMealTable();
        initializeShoppingListTable();
        disableIfNotSelected();
    }

    @FXML
    void addToShoppingList(ActionEvent event) {
        shoppingObservableList.add(mealTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    void generateShoppingList(ActionEvent event) {
        PDFGenerator pdfGenerator = new PDFGenerator();
        ArrayList<Meal> mealList = new ArrayList<>(shoppingObservableList);
         pdfGenerator.generateShoppingList(mealList);

    }

    private void initalizeMealTable() {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());
        mealObservableList.addAll(mealDAO.getAllMeals());


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        mealTable.setItems(mealObservableList);
        FilteredList<Meal> filteredData = new FilteredList<>(mealObservableList, p -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(meal -> {
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
        sortedData.comparatorProperty().bind(mealTable.comparatorProperty());
        mealTable.setItems(sortedData);
    }

    private void initializeShoppingListTable() {
        mealName.setCellValueFactory(new PropertyValueFactory<>("name"));
        listTable.setItems(shoppingObservableList);
    }

    private void disableIfNotSelected() {
        BooleanBinding addBinding = Bindings.isNull(mealTable.getSelectionModel().selectedItemProperty());
        addToList.disableProperty().bind(addBinding);
        BooleanBinding generateBinding = Bindings.isEmpty(shoppingObservableList);
        generateShoppingList.disableProperty().bind(generateBinding);
    }

}

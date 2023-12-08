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
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.MealDAO;
import pl.dietbuilder.model.Meal;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteMealController implements Initializable {
    @FXML
    private TableColumn<Meal, String> categoryColumn;

    @FXML
    private TableColumn<Meal, Integer> idColumn;

    @FXML
    private TableColumn<Meal, String> nameColumn;

    @FXML
    private Button deleteMeal;

    @FXML
    private CheckBox confirmSelection;

    @FXML
    private TableView<Meal> mealTableView;

    @FXML
    private TextField searchBar;

    private ObservableList<Meal> mealObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize table with meals
        initalizeMealTable();
        //disabble confirm if nothing selected and delete if not confirmed
        disableIfNotSelectedConfirmed();

    }

    private void disableIfNotSelectedConfirmed() {
        BooleanBinding confirmBinding = Bindings.isNull(mealTableView.getSelectionModel().selectedItemProperty());
        confirmSelection.disableProperty().bind(confirmBinding);
        BooleanBinding deleteBinding = Bindings.not(confirmSelection.selectedProperty());
        deleteMeal.disableProperty().bind(deleteBinding);


    }

    @FXML
    private void deleteSelected(ActionEvent event) {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());

        Meal meal = mealTableView.getSelectionModel().getSelectedItem();

        mealDAO.deleteMeal(meal.getName());
        mealObservableList.remove(meal);
        confirmSelection.setSelected(false);
    }


    private void initalizeMealTable() {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());
        mealObservableList.addAll(mealDAO.getAllMeals());

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        mealTableView.setItems(mealObservableList);
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
            } else if (String.valueOf(meal.getId()).contains(keywordSearch)) {
                return true;
            } else {
                return false;
            }
        }));
        SortedList<Meal> sortedData = new SortedList<Meal>(filteredData);
        sortedData.comparatorProperty().bind(mealTableView.comparatorProperty());
        mealTableView.setItems(sortedData);
    }

}

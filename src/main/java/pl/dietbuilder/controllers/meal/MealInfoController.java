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
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.MealDAO;
import pl.dietbuilder.model.Meal;
import pl.dietbuilder.utils.NutritionalValuesCalculator;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MealInfoController implements Initializable {
    @FXML
    private TextFlow carbohydratesAmount;

    @FXML
    private TableColumn<Meal, String> categoryColumn;

    @FXML
    private TextFlow energyAmount;

    @FXML
    private TextFlow fatAmount;

    @FXML
    private TableView<Meal> mealTableView;

    @FXML
    private TableColumn<Meal, String> nameColumn;

    @FXML
    private TextFlow proteinAmount;

    @FXML
    private TextField searchBar;

    @FXML
    private Button showDataButton;

    private ObservableList<Meal> mealObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initalizeMealTable();
        disableShowDataButtonIfNotSelected();
    }

    @FXML
    void showData(ActionEvent event) {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());
        String mealName = mealTableView.getSelectionModel().getSelectedItem().getName();

        clearFields();

        NutritionalValuesCalculator calculator = new NutritionalValuesCalculator();
        assignNutritionalValues(calculator.getNutritionalValues(mealDAO.getMealInfo(mealName)));

    }

    private void initalizeMealTable() {
        MealDAO mealDAO = new MealDAO(ConnectionManager.getInstance());
        mealObservableList.addAll(mealDAO.getAllMeals());


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
        mealTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }

    private void disableShowDataButtonIfNotSelected() {
        BooleanBinding selectionBinding = Bindings.isNull(mealTableView.getSelectionModel().selectedItemProperty());
        showDataButton.disableProperty().bind(selectionBinding);
    }

    private void assignNutritionalValues(HashMap<String, Double> nutritionalInformation) {

        Text energyText = new Text(String.valueOf(nutritionalInformation.get("calories")));
        Text proteinText = new Text(String.valueOf(nutritionalInformation.get("proteins")));
        Text fatText = new Text(String.valueOf(nutritionalInformation.get("fats")));
        Text carbohydratesText = new Text(String.valueOf(nutritionalInformation.get("carbohydrates")));

        energyAmount.getChildren().add(energyText);
        proteinAmount.getChildren().add(proteinText);
        fatAmount.getChildren().add(fatText);
        carbohydratesAmount.getChildren().add(carbohydratesText);

    }

    private void clearFields() {
        energyAmount.getChildren().clear();
        proteinAmount.getChildren().clear();
        fatAmount.getChildren().clear();
        carbohydratesAmount.getChildren().clear();
    }


}

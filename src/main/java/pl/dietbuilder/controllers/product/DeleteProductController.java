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
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.ProductDAO;
import pl.dietbuilder.model.Product;

import java.net.URL;
import java.util.ResourceBundle;


public class DeleteProductController implements Initializable {

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private Button deleteProduct;

    @FXML
    private CheckBox confirmSelection;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TextField searchBar;

    private final ObservableList<Product> productsObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize table with products
        initalizeProductTable();
        //disabble confirm if nothing selected and delete if not confirmed
        disableIfNotSelectedConfirmed();

    }

    private void disableIfNotSelectedConfirmed() {
        BooleanBinding confirmBinding = Bindings.isNull(productTableView.getSelectionModel().selectedItemProperty());
        confirmSelection.disableProperty().bind(confirmBinding);
        BooleanBinding deleteBinding = Bindings.not(confirmSelection.selectedProperty());
        deleteProduct.disableProperty().bind(deleteBinding);
    }

    @FXML
    private void deleteSelected(ActionEvent event) {
        ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance());
        Product product = productTableView.getSelectionModel().getSelectedItem();
        productDAO.deleteProduct(product.getId());
        productsObservableList.remove(product);
        confirmSelection.setSelected(false);
    }


    private void initalizeProductTable() {
        ProductDAO productDAO = new ProductDAO(ConnectionManager.getInstance());
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
            } else return String.valueOf(product.getId()).indexOf(keywordSearch) > -1;
        }));
        SortedList<Product> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
        productTableView.setItems(sortedData);
    }

}

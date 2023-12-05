package pl.dietbuilder.controllers.product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.dietbuilder.dbmanagement.ConnectionManager;
import pl.dietbuilder.dbmanagement.ProductDAO;
import pl.dietbuilder.model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProductController implements Initializable {

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TextField searchBar;

    ObservableList<Product> productsObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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



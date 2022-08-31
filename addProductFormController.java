package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This Class is the controller for the Add Product screen, called from the Main Screen.
 */
public class addProductFormController implements Initializable {
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    public AnchorPane addProductForm;

    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField minField;
    public TextField maxField;
    public TextField searchField;

    public TableView<Part> allPartsTable;
    public TableColumn<Part, Integer> partIDCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> invCol;
    public TableColumn<Part, Double> priceCol;

    public TableView<Part> associatedTable;
    public TableColumn<Part, Integer> assocPartIDCol;
    public TableColumn<Part, String> assocPartNameCol;
    public TableColumn<Part, Integer> assocInvCol;
    public TableColumn<Part, Double> assocPriceCol;

    public Button add;
    public Button removeAssocPart;
    public Button save;
    public Button cancel;

    /**
     * Populates the allPartsTable with all available parts. Populates associatedTable with any parts added
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        associatedTable.setItems(assocParts);
        assocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        assocPriceCol.setCellValueFactory( new PropertyValueFactory<>("Price"));

    }

    /**
     * Searches the Part Table
     * @param actionEvent handles pressing the ENTER key inside the search field for the 'Parts' Table
     */
    public void onPartSearch(ActionEvent actionEvent) {
        if (!searchField.getText().trim().isEmpty()) {
            try {
                int idSearch = Integer.parseInt(searchField.getText());
                allPartsTable.getSelectionModel().select(Inventory.lookUpPartID(idSearch));
                if (Inventory.lookUpPartID(idSearch) == null) {
                    String nameSearch = searchField.getText();
                    allPartsTable.setItems(Inventory.lookUpPartName(nameSearch));
                    if (Inventory.lookUpPartName(nameSearch).isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Part Search");
                        alert.setContentText("No parts found matching that Name");
                        alert.showAndWait();
                        allPartsTable.setItems(Inventory.getAllParts());
                    }
                }
            }
            catch (NumberFormatException e) {
                String nameSearch = searchField.getText();
                allPartsTable.setItems(Inventory.lookUpPartName(nameSearch));
                if (Inventory.lookUpPartName(nameSearch).isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Search");
                    alert.setContentText("No parts found matching that Name");
                    alert.showAndWait();
                    allPartsTable.setItems(Inventory.getAllParts());
                }
            }
        }
        else {
            allPartsTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Adds a Part from the Parts table to the Associated Parts table
     * @param actionEvent handles clicking the 'Add' button
     */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part must be selected");
            alert.showAndWait();
        }
        else {
            assocParts.add(selectedPart);
            associatedTable.setItems(assocParts);
        }

    }

    /**
     * Removes an Associated Part from the Associated Parts table
     * @param actionEvent handles clicking the 'Remove' button
     */
    public void onRemoveAssocPart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Associated Parts");
        alert.setHeaderText("Remove");
        alert.setContentText("Do you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part selectedPart = associatedTable.getSelectionModel().getSelectedItem();
            assocParts.remove(selectedPart);
            associatedTable.setItems(assocParts);
        }
        else {
            alert.close();
        }

    }

    /**
     * Stores data from all fields and creates a new Product, checks logic and catches Exceptions
     * @param actionEvent handles clicking the 'Save' button
     * @throws IOException sends user back to the Main Screen
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        int id = Inventory.getProductID();
        String name = nameField.getText();
        int inv = 0;
        if (invField.getText().isEmpty()) {
            inv = 0;
        }
        else {
            try {
                inv = Integer.parseInt(invField.getText());
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory must be int");
                alert.showAndWait();
                inv = 0;
            }
        }
        double price = 0d;
        if (priceField.getText().isEmpty()) {
            price = 0d;
        }
        else {
            try {
                price = Double.parseDouble(priceField.getText());
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be double");
                alert.showAndWait();
                price = 0d;
            }
        }
        int max = 0;
        if (maxField.getText().isEmpty()) {
            max = 0;
        }
        else {
            try {
                max = Integer.parseInt(maxField.getText());
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max must be int");
                alert.showAndWait();
                max = 0;
            }
        }
        int min = 0;
        if (minField.getText().isEmpty()) {
            min = 0;
        }
        else {
            try {
                min = Integer.parseInt(minField.getText());
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be int");
                alert.showAndWait();
                min = 0;
            }
        }
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Products must have a name");
            alert.showAndWait();
        }
        if (inv <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory must be int type greater than 0");
            alert.showAndWait();
        }
        if (price <= 0d) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product price must be double type greater than 0");
            alert.showAndWait();
        }
        if (max <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Max must be int type greater than 0");
            alert.showAndWait();
        }
        if (min <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Min must be int type greater than 0");
            alert.showAndWait();
        }
        if (max < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Maximum inventory must be greater than minimum inventory");
            alert.showAndWait();
            max = 0;
        }
        if (inv > max || inv < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory must be less than Maximum and more than Minimum");
            alert.showAndWait();
            inv = 0;
        }
        if (!nameField.getText().isEmpty() && !invField.getText().isEmpty() && !priceField.getText().isEmpty() && !maxField.getText().isEmpty() && !minField.getText().isEmpty()
                && price > 0 && inv > 0 && max > 0 && min > 0) {
            Product product = new Product(id, name, price, inv, min, max);
            Inventory.addProduct(product);
            for (Part part : assocParts) {
                product.addAssociatedPart(part);
            }

            Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 500);
            stage.setTitle("C482 PA");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Closes the Add Product screen
     * @param actionEvent handles clicking the 'Delete' button
     * @throws IOException returns the user to the Main screen
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("C482 PA");
        stage.setScene(scene);
        stage.show();
    }
}

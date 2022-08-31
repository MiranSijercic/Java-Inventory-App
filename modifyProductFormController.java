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
 * This Class is the Controller for the Modify Product Screen, called from the Main Screen
 */
public class modifyProductFormController implements Initializable {
    Product selectedProduct;
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    public AnchorPane modifyProductForm;

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
     * Initializes the Parts table with all parts and the Associated Parts tables with the passed Product's associated parts
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allPartsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        associatedTable.setItems((assocParts));
        assocPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        assocPriceCol.setCellValueFactory( new PropertyValueFactory<>("Price"));

    }

    /**
     * Used to pass data from selectedProduct to the Modify Product Form
     * @param selectedProduct is the product selected from the Product table on the Main screen
     */
    public void sendProduct (Product selectedProduct) {
        idField.setText(String.valueOf(selectedProduct.getId()));
        nameField.setText(selectedProduct.getName());
        invField.setText(String.valueOf(selectedProduct.getStock()));
        priceField.setText(String.valueOf(selectedProduct.getPrice()));
        maxField.setText(String.valueOf(selectedProduct.getMax()));
        minField.setText(String.valueOf(selectedProduct.getMin()));
        this.selectedProduct = selectedProduct;

    }
    public void sendAssocParts (ObservableList<Part> associatedParts) {
        assocParts = associatedParts;
        associatedTable.setItems(assocParts);
    }

    /**
     * Used to search Parts in the Part table
     * @param actionEvent handles pressing the ENTER kay
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
     * Used to add a Part from the Part table to the Associated Parts
     * @param actionEvent handles clicking the 'Add' button
     */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        }
        else {
            assocParts.add(selectedPart);
            associatedTable.setItems(assocParts);
        }

    }

    /**
     * Removes an Associated Part from the Associated parts table
     */
    public void onRemoveAssocPart() {
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
     * Saves changes to fields associated with a Product, checks logic and catches Exceptions
     * @param actionEvent handles clicking the 'Save' button
     * @throws IOException sends the user back to the Main Screen
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(idField.getText());
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
        for (Product product:Inventory.getAllProducts()) {
            if (product.getId() == id) {
                if (!nameField.getText().isEmpty() && !invField.getText().isEmpty() && !priceField.getText().isEmpty() && !maxField.getText().isEmpty() && !minField.getText().isEmpty()
                        && price > 0 && inv > 0 && max > 0 && min > 0) {
                    Product updatedProduct = new Product(id, name, price, inv, min, max);
                    for (Part part : assocParts) {
                        updatedProduct.addAssociatedPart(part);
                    }
                    Inventory.updateProduct(Inventory.getAllProducts().indexOf(product), updatedProduct);

                    Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1000, 500);
                    stage.setTitle("C482 PA");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }

    /**
     * Closes the Modify Product Screen
     * @param actionEvent handles clicking the 'Cancel' button
     * @throws IOException sends user back to the Main Screen
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

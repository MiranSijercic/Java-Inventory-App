package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
 * This Class is the Controller for the first screen (Main Screen) that shows after application launch.
 */
public class mainScreenController implements Initializable {
    public AnchorPane mainForm;

    public Label inventoryManagementSystem;
    public Label Parts;
    public Label Products;

    public TableView<Part> allPartsTable;
    public TableColumn<Part, Integer> partIDCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInventoryCol;
    public TableColumn<Part, Double> partPricePerCol;

    public TableView<Product> allProductsTable;
    public TableColumn<Product, Integer> productIDCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, Integer> productInventoryCol;
    public TableColumn<Product, Double> productPricePerCol;

    public Button partAdd;
    public Button partModify;
    public Button partDelete;
    public Button productAdd;
    public Button productModify;
    public Button productDelete;
    public Button exit;

    public TextField searchPart;
    public TextField searchProduct;

    @Override
    /**
     * This method populates the 'Parts' and 'Products' tableviews with respective information
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPricePerCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        allProductsTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPricePerCol.setCellValueFactory( new PropertyValueFactory<>("Price"));

    }

    /**
     * Sends the user to the Add Part Screen
     * @param actionEvent handles clicking the 'Add' button below the 'Parts' table
     * @throws IOException opens addPartForm.fxml, with corresponding application window
     */
    public void onPartAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPartForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * After selecting a Part, sends a user to the Modify Part Screen
     * @param actionEvent handles clicking the 'Modify' button below the 'Parts' table
     * @throws IOException opens modifyPartForm.fxml, with corresponding application window, populates existing Part data
     */
    public void onPartModify(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyPartForm.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            modifyPartFormController modifyPartFormController = loader.getController();
            modifyPartFormController.sendPart(allPartsTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part from the list");
            alert.showAndWait();
        }

    }

    /**
     * After confirmation, deletes a part from the Part table
     * @param actionEvent handles clicking the 'Delete' button below the 'Parts' table, calls deletePart method from Inventory Class
     */
    public void onPartDelete(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(selectedPart);
            }
            else {
                alert.close();
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Searches for a Part in the Parts table
     * @param actionEvent handles pressing the ENTER key inside the search field above the 'Parts' table, calls Part ID or Name search methods from Inventory Class
     */
    public void onSearchPart(ActionEvent actionEvent) {
        if (!searchPart.getText().trim().isEmpty()) {
            try {
                int idSearch = Integer.parseInt(searchPart.getText());
                allPartsTable.getSelectionModel().select(Inventory.lookUpPartID(idSearch));
                if (Inventory.lookUpPartID(idSearch) == null) {
                    String nameSearch = searchPart.getText();
                    allPartsTable.setItems(Inventory.lookUpPartName(nameSearch));
                    if (Inventory.lookUpPartName(nameSearch).isEmpty()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Part Search");
                        alert.setContentText("No parts found matching that Name");
                        alert.showAndWait();
                        allPartsTable.setItems(Inventory.getAllParts());
                    }
                }
            }
            catch (NumberFormatException e) {
                String nameSearch = searchPart.getText();
                allPartsTable.setItems(Inventory.lookUpPartName(nameSearch));
                if (Inventory.lookUpPartName(nameSearch).isEmpty()) {
                    Alert alert = new Alert(AlertType.INFORMATION);
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
     * Sends the user to Add Product Screen
     * @param actionEvent handles clicking the 'Add' button below the 'Products' table
     * @throws IOException opens addProductForm.fxml, with corresponding application window
     */
    public void onProductAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProductForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * After selecting a Product, send the user to the Modify Product Screen
     * @param actionEvent handles clicking the 'Modify' button below the 'Products' table
     * @throws IOException opens modifyProductForm.fxml, with corresponding application window, populates existing Product data
     */
    public void onProductModify(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modifyProductForm.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);

            modifyProductFormController modifyProductFormController = loader.getController();
            modifyProductFormController.sendProduct(allProductsTable.getSelectionModel().getSelectedItem());
            modifyProductFormController.sendAssocParts(allProductsTable.getSelectionModel().getSelectedItem().getAllAssociatedParts());

            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product from the list");
            alert.showAndWait();
        }

    }

    /**
     * If Product does not have Associated Parts, deletes the Product
     * @param actionEvent handles clicking the 'Delete' button below the 'Parts' table, calls deleteProduct from 'allParts' tableview
     */
    public void onProductDelete(ActionEvent actionEvent) {
        try {
            Product selectedProduct = allProductsTable.getSelectionModel().getSelectedItem();
            if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please remove all associated parts.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Product");
                alert.setHeaderText("Delete");
                alert.setContentText("Do you want to delete this product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selectedProduct);
                } else {
                    alert.close();
                }
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }
    }

    /**
     * Searches for a Product in the Product Table
     * @param actionEvent handles pressing the ENTER key inside the search field about the 'Products' table, calls Product ID or Name search methods from Inventory Class
     */
    public void onSearchProduct(ActionEvent actionEvent) {
        if (!searchProduct.getText().trim().isEmpty()) {
            try {
                int searchID = Integer.parseInt(searchProduct.getText());
                allProductsTable.getSelectionModel().select(Inventory.lookUpProductID(searchID));
                if (Inventory.lookUpProductID(searchID) == null) {
                    String searchString = searchProduct.getText();
                    allProductsTable.setItems(Inventory.lookUpProductName(searchString));
                    if (Inventory.lookUpProductName(searchString).isEmpty()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Product Search");
                        alert.setContentText("No products found matching that name");
                        alert.showAndWait();
                        allProductsTable.setItems(Inventory.getAllProducts());
                    }
                }
            }
            catch (NumberFormatException e) {
                String searchString = searchProduct.getText();
                allProductsTable.setItems(Inventory.lookUpProductName(searchString));
                if (Inventory.lookUpProductName(searchString).isEmpty()) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Product Search");
                    alert.setContentText("No products found matching that name");
                    alert.showAndWait();
                    allProductsTable.setItems(Inventory.getAllProducts());
                }
            }
        }
        else {
            allProductsTable.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * After confirmation, closes the application
     * @param actionEvent handles clicking the 'Exit' button, closes the application
     */
    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Application");
        alert.setContentText("Do you want to close the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
        else {
            alert.close();
        }
    }

}

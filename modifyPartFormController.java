package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This Class is the Controller for the Modify Part Screen, called from the Main Screen
 */
public class modifyPartFormController implements Initializable {
    public AnchorPane modifyPart;
    
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;

    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField machineIDorCompanyField;
    public TextField minField;

    public Button save;
    public Button cancel;

    public ToggleGroup modifyPartToggle;
    public Label machineIDLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * Used to pass data from the selectedPart to the Modify Part Form
     * @param selectedPart is the product selected from the Part Table in the Main Screen
     */
    public void sendPart(Part selectedPart) {
        if (selectedPart instanceof InHouse) {
            inHouseRadio.setSelected(true);
            idField.setText(String.valueOf(selectedPart.getId()));
            nameField.setText(selectedPart.getName());
            invField.setText(String.valueOf(selectedPart.getStock()));
            priceField.setText(String.valueOf(selectedPart.getPrice()));
            maxField.setText(String.valueOf(selectedPart.getMax()));
            minField.setText(String.valueOf(selectedPart.getMin()));
            machineIDorCompanyField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));

        }
        else {
            outsourcedRadio.setSelected(true);
            machineIDLabel.setText("Company");
            idField.setText(String.valueOf(selectedPart.getId()));
            nameField.setText(selectedPart.getName());
            invField.setText(String.valueOf(selectedPart.getStock()));
            priceField.setText(String.valueOf(selectedPart.getPrice()));
            maxField.setText(String.valueOf(selectedPart.getMax()));
            minField.setText(String.valueOf(selectedPart.getMin()));
            machineIDorCompanyField.setText((((Outsourced) selectedPart).getCompanyName()));
        }
    }

    /**
     * Used to change the MachineID label to the appropriate text for either an InHouse or Outsourced Part
     * @param actionEvent handles selecting either the InHouse or Outsourced Radio button
     */
    public void onInHouseRadio(ActionEvent actionEvent) {
        if (inHouseRadio.isSelected()) {
            machineIDLabel.setText("Machine ID");
        }
        if (outsourcedRadio.isSelected()) {
            machineIDLabel.setText("Company");
        }

    }

    /**
     * Saves changes to any fields associated with the Part, checks logic and catches Exceptions
     * @param actionEvent handles clicking the 'Save' button
     * @throws IOException if save is successful, sends the user back to the Main Screen
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(idField.getText());
        String partName = nameField.getText();
        int partStock = 0;
        if (invField.getText().isEmpty()) {
            partStock = 0;
        }
        else {
            try {
                partStock = Integer.parseInt(invField.getText());
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inventory must be int");
                alert.showAndWait();
                partStock = 0;
            }
        }
        double partPrice = 0d;
        if (priceField.getText().isEmpty()) {
            partPrice = 0d;
        }
        else {
            try {
                partPrice = Double.parseDouble(priceField.getText());
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Price must be double");
                alert.showAndWait();
                partPrice = 0d;
            }
        }
        int partMax = 0;
        if (maxField.getText().isEmpty()) {
            partMax = 0;
        }
        else {
            try {
                partMax = Integer.parseInt(maxField.getText());
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max must be int");
                alert.showAndWait();
                partMax = 0;
            }
        }
        int partMin = 0;
        if (minField.getText().isEmpty()) {
            partMin = 0;
        }
        else {
            try {
                partMin = Integer.parseInt(minField.getText());
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be int");
                alert.showAndWait();
                partMin = 0;
            }
        }
        if (partName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Parts must have a name");
            alert.showAndWait();
        }
        if (partStock <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory must be int type greater than 0");
            alert.showAndWait();
        }
        if (partPrice <= 0d) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part price must be double type greater than 0");
            alert.showAndWait();
        }
        if (partMax <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Max must be int type greater than 0");
            alert.showAndWait();
        }
        if (partMin <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Min must be int type greater than 0");
            alert.showAndWait();
        }
        if (partMax < partMin) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Maximum inventory must be greater than minimum inventory");
            alert.showAndWait();
            partMax = 0;
        }
        if (partStock > partMax || partStock < partMin) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory must be less than Maximum and more than Minimum");
            alert.showAndWait();
            partStock = 0;
        }
        if (inHouseRadio.isSelected()) {
            int machineID = 0;
            try {
                machineID = Integer.parseInt(machineIDorCompanyField.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Machine ID must be int");
                alert.showAndWait();
                machineID = 0;
            }
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() == Integer.parseInt(idField.getText())) {
                    if (!nameField.getText().isEmpty() && !invField.getText().isEmpty() && !priceField.getText().isEmpty() && !maxField.getText().isEmpty() && !minField.getText().isEmpty() && !machineIDorCompanyField.getText().isEmpty()
                            && partPrice != 0 && partStock != 0 && partMax != 0 && partMin != 0 && machineID != 0) {
                        Inventory.updatePart(Inventory.getAllParts().indexOf(part), new InHouse(id, partName, partPrice, partStock, partMin, partMax, machineID));

                        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 1000, 500);
                        stage.setTitle("C482 PA");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }
        }
        else if (outsourcedRadio.isSelected()) {
            String companyName = machineIDorCompanyField.getText();
            if (companyName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Company must have a name");
                alert.showAndWait();
            }
            for (Part part : Inventory.getAllParts()) {
                if (part.getId() == Integer.parseInt(idField.getText())) {
                    if (!nameField.getText().isEmpty() && !invField.getText().isEmpty() && !priceField.getText().isEmpty() && !maxField.getText().isEmpty() && !minField.getText().isEmpty() && !machineIDorCompanyField.getText().isEmpty()
                            && partPrice != 0 && partStock != 0 && partMax != 0 && partMin != 0) {
                        Inventory.updatePart(Inventory.getAllParts().indexOf(part), new Outsourced(id, partName, partPrice, partStock, partMin, partMax, companyName));

                        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 1000, 500);
                        stage.setTitle("C482 PA");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part type");
            alert.showAndWait();
        }
    }

    /**
     * Closes the Modify Part Screen
     * @param actionEvent handles clicking the 'Cancel' button
     * @throws IOException sends the user back to the Main Screen
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("C482");
        stage.setScene(scene);
        stage.show();
    }

}

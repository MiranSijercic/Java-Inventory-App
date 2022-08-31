package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This Class is the Controller for the Add Part Form, called from the Main Screen
 */
public class addPartFormController implements Initializable {

    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;

    public TextField idField;
    public TextField nameField;
    public TextField stockField;
    public TextField priceField;
    public TextField maxField;
    public TextField machineOrCompanyField;
    public TextField minField;

    public Button save;
    public Button cancel;
    public ToggleGroup partSource;

    public Label machineIDorCompany;

    public DialogPane consolePrint;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Sets the machineIDorCompany label to 'Machine ID'
     * @param actionEvent handles clicking the InHouse Radio button
     */
    public void onInHouseRadio(ActionEvent actionEvent) {
        machineIDorCompany.setText("Machine ID");

    }

    /**
     * Sets the machineIDorCompany label to 'Company'
     * @param actionEvent handles clicking the Outsource Radio button
     */
    public void onOutsourcedRadio(ActionEvent actionEvent) {
        machineIDorCompany.setText("Company Name");

    }

    /**
     * Stores data from all fields and creates a new Part, checks logic and catches Exceptions
     * @param actionEvent handles clicking the 'Save' button
     * @throws IOException sends user back to the Main Screen
     * <p><b>
     * In trying to save a new Part, I ran into both runtime and logic errors. Specifically, the program was throwing NumberFormat Exceptions
     * when trying to pass an incorrect data type into the addPart function. To handle this, I declared each variable and initialized it with 0.
     * Before allowing a call to the addPart function, I instated an IF statement to check that no variables are set to 0 or that any of the fields are empty.
     * Should the field be empty, the variable stays assigned with 0, which would fail the IF check.
     * If the field is empty, but the wrong data type, I placed a try/catch block to handle the exception and reset the variable to 0 to also fail the addPart IF check.
     * This prevented any further exceptions being thrown or logic errors from being saved to a new Part.
     * Any variables that fail checks provide an Alert detailing variable requirements.
     * </b></p>
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        int id = Inventory.getPartID();
        String partName = nameField.getText();
        int partStock = 0;
        if (stockField.getText().isEmpty()) {
            partStock = 0;
        }
        else {
            try {
                partStock = Integer.parseInt(stockField.getText());
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
            if (machineOrCompanyField.getText().isEmpty()) {
                machineID = 0;
            } else {
                try {
                    machineID = Integer.parseInt(machineOrCompanyField.getText());
                }
                catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Machine ID must be int");
                    alert.showAndWait();
                    machineID = 0;
                }
            }
            if (machineID <= 0) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("Machine ID must be positive int");
                alert2.showAndWait();
            }
            else if (!nameField.getText().isEmpty() && !stockField.getText().isEmpty() && !priceField.getText().isEmpty() && !maxField.getText().isEmpty() && !minField.getText().isEmpty() && !machineOrCompanyField.getText().isEmpty()
                      && partPrice > 0 && partStock > 0 && partMax > 0 && partMin > 0 && machineID > 0) {
                Inventory.addPart(new InHouse(id, partName, partPrice, partStock, partMin, partMax, machineID));

                Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 500);
                stage.setTitle("C482 PA");
                stage.setScene(scene);
                stage.show();
            }
        }

        else if (outsourcedRadio.isSelected()) {
            String company = machineOrCompanyField.getText();
            if (company.isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setContentText("Company field must have string");
                alert2.showAndWait();
            }
            if (!nameField.getText().isEmpty() && !stockField.getText().isEmpty() && !priceField.getText().isEmpty() && !maxField.getText().isEmpty() && !minField.getText().isEmpty() && !machineOrCompanyField.getText().isEmpty()
                    && partPrice != 0 && partStock != 0 && partMax != 0 && partMin != 0) {
                Inventory.addPart(new Outsourced(id, partName, partPrice, partStock, partMin, partMax, company));

                Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 500);
                stage.setTitle("C482 PA");
                stage.setScene(scene);
                stage.show();
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
     * Closes the Add Part Screen
     * @param actionEvent handles clicking the 'Cancel' button
     * @throws IOException sends the user back to the Main Screen
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("C482 PA");
        stage.setScene(scene);
        stage.show();

    }}


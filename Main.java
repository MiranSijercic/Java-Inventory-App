package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This Class controls the Inventory Management Application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainForm.fxml"));
        primaryStage.setTitle("C482 PA");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }

    /**
     * This is the main method.
     * @param args
     * <p><b>
     * Please find the javadoc file at location (C482 PA for Miran Sijercic Submission 3\C482 PA\src\model)
     * One improvement that can be made for future versions of the application is that the Part stock count can be decremented as they are saved to the
     * associatedParts lists for each Product.
     * </b></p>
     */
    public static void main(String[] args) {

        InHouse part1 = new InHouse(11, "abcd", .1, 10, 2, 20, 123 );
        InHouse part3 = new InHouse(15, "abcdef", .1, 10, 2, 20, 123 );
        Outsourced part2 = new Outsourced(13, "efgh", 2, 5, 2, 20, "Tester");
        Product product1 = new Product(10, "ijkl", 5.0, 10, 2, 20);
        Product product3 = new Product(14, "ijklmn", 5.0, 10, 2, 20);
        Product product2 = new Product(12, "mnop", 20, 5, 2, 20);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        //Inventory.addProduct(product1);
        //Inventory.addProduct(product2);
        //Inventory.addProduct(product3);

        launch(args);
    }

}


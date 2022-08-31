package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Locale;

/**
 * This Class consists of all of the methods used to manipulate Inventory. Inventory consists of all Products and Parts.
 */
public class Inventory {
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    public static int partID = -1;
    public static int productID = 0;

    /**
     * Adds a Part to the Inventory
     * @param newPart the Part to be added to the allParts observable list
     */
    public static void addPart(Part newPart) { allParts.add(newPart); }

    /**
     * Adds a Product to the Inventory
     * @param newProduct the Product to be added to the allProducts observable list
     */
    public static void addProduct(Product newProduct) { allProducts.add(newProduct); }

    /**
     * Searches all Parts using an integer ID
     * @param partID the ID used to search the allParts observable list
     * @return the Part matching the partID passed in, Null if partID is not found
     */
    public static Part lookUpPartID(int partID) {
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            Part searchPart = Inventory.getAllParts().get(i);
            if (searchPart.getId() == partID) {
                return searchPart;
            }
        }
        return null;
    }

    /**
     * Searches all Products using an integer ID
     * @param productID the int ID used to search the allProducts observable list
     * @return the Product matching the productID passed in, Null if productID is not found
     */
    public static Product lookUpProductID(int productID) {
        for (int i = 0; i < Inventory.allProducts.size(); i++) {
            Product searchProduct = Inventory.getAllProducts().get(i);
            if (searchProduct.getId() == productID) {
                return searchProduct;
            }
        }
        return null;
    }

    /**
     * Searches Part names using provided String
     * @param partName the String name of the part used to search the allParts observable list
     * @return the Part matching the partName passed in, Null if partName is not found
     */
    public static ObservableList<Part> lookUpPartName(String partName) {
        if (!(Inventory.getFilteredParts().isEmpty())) {
            Inventory.getFilteredParts().clear();
        }

        for (Part searchPart : Inventory.getAllParts()) {
            if (searchPart.getName().toLowerCase(Locale.ROOT).contains(partName.toLowerCase(Locale.ROOT))) {
                getFilteredParts().add(searchPart);
            }
        }
        return filteredParts;
    }

    /**
     * Searches Product names using provided String
     * @param productName the String name of the product used to search the allParts observable list
     * @return the Product matching the productName passed in, Null if productName is not found
     */
    public static ObservableList<Product> lookUpProductName(String productName) {
        if (!(Inventory.getFilteredProducts().isEmpty())) {
            Inventory.getFilteredProducts().clear();
        }

        for (Product searchProduct : Inventory.getAllProducts()) {
            if (searchProduct.getName().toLowerCase(Locale.ROOT).contains(productName.toLowerCase(Locale.ROOT))) {
                getFilteredProducts().add(searchProduct);
            }
        }
        return filteredProducts;
    }

    /**
     * Replaces a Part with an updated version using .set method
     * @param index the index in allParts observable list of the Part to be replaced
     * @param selectedPart the updated Part
     */
    public static void updatePart(int index, Part selectedPart) { getAllParts().set(index, selectedPart); }

    /**
     * Replaces a Product with an updated version using .set method
     * @param index the index in allProducts observable list of the Product to be replaced
     * @param selectedProduct the updated Product
     */
    public static void updateProduct(int index, Product selectedProduct) { getAllProducts().set(index, selectedProduct); }

    /**
     * Used to delete a Part from the allParts Observable List
     * @param selectedPart the Part to be deleted
     * @return True after Part is found and deleted, false if selectedPart is not found
     */
    public static boolean deletePart(Part selectedPart) {
        if (Inventory.getAllParts().contains(selectedPart)) {
            Inventory.getAllParts().remove(selectedPart);
            return true;
        }
        else {return false;}
    }

    /**
     * Used to delete a Product from the allProducts Observable List
     * @param selectedProduct the Product to be deleted
     * @return True after selectedProduct is found and deleted, False if selectedProduct is not found
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (Inventory.getAllProducts().contains(selectedProduct)) {
            Inventory.getAllProducts().remove(selectedProduct);
            return true;
        }
        else {return false;}
    }

    /**
     * Gets the allParts Observable List
     * @return the allParts observable list
     */
    public static ObservableList<Part> getAllParts() { return allParts; }

    /**
     * Gets the allProducts Observable List
     * @return the allProducts observable list
     */
    public static ObservableList<Product> getAllProducts() { return allProducts; }

    /**
     * Used to filter the allParts list to populate search results
     * @return the filteredParts list
     */
    public static ObservableList<Part> getFilteredParts() { return filteredParts; }

    /**
     * Used to filter the allProducts list to populate search results
     * @return the filteredProducts list
     */
    public static ObservableList<Product> getFilteredProducts() { return filteredProducts; }

    /**
     * Gets a unique Part ID
     * @return partID
     */
    public static int getPartID() {
        partID = partID + 2;
        return partID;
    }

    /**
     * Gets a unique Product ID
     * @return productID
     */
    public static int getProductID() {
        productID = productID + 2;
        return productID;
    }

}

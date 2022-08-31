package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This Class constructs Product and contains all methods for manipulating Products. Products consist of Parts.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the id field
     * @param id the Product ID to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * Gets the id
     * @return the Product ID
     */
    public int getId() { return id; }

    /**
     * Sets the name field
     * @param name the Product name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets the name
     * @return the Product name
     */
    public String getName() { return name; }

    /**
     * Sets the price field
     * @param price the Product price to set
     */
    public void setPrice(int price) { this.price = price; }

    /**
     * Gets the price
     * @return the Product price
     */
    public double getPrice() { return price; }

    /**
     * Sets the stock field
     * @param stock the Product stock to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * Gets the stock
     * @return the Product stock
     */
    public int getStock() { return stock; }

    /**
     * Sets the minimum inventory field
     * @param min the Product minimum inventory to set
     */
    public void setMin(int min) { this.min = min; }

    /**
     * Gets the minimum inventory
     * @return the Product minimum inventory count
     */
    public int getMin() { return min; }

    /**
     * Sets the maximum inventory field
     * @param max the Product maximum inventory to set
     */
    public void setMax(int max) { this.max = max; }

    /**
     * Gets the maximum inventory
     * @return the Product maximum inventory count
     */
    public int getMax() { return max; }

    /**
     * Adds an Associated Part to the product
     * @param part The Product associated part to be added to the associatedParts observable list
     */
    public void addAssociatedPart(Part part) { associatedParts.add(part); }

    /**
     * Deletes an Associated Part from the Product
     * @param selectedAssociatedPart The Product associated part to be deleted from the associatedParts observable list
     * @return True if selectedAssociatedPart is found, False if selectedAssociatedPart is not found
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }

        else { return false; }
    }

    /**
     * Gets the list of Associated Parts for the Product
     * @return the Product associatedParts observable list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}

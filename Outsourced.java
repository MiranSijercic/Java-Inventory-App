package model;

/**
 * This is a subclass of Part used to create and manipulate Outsourced Parts
 */
public class Outsourced extends Part {
    public String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Used to set the Company Name
     * @param companyName the Company Name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Used to get the Company Name
     * @return the Company Name
     */
    public String getCompanyName() {
        return companyName;
    }
}

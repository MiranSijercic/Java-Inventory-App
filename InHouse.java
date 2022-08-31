package model;

/**
 * This is a subclass of Part used to create and manipulate In House Parts
 */
public class InHouse extends Part {
    public int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the machineID field
     * @param machineId the Machine ID to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets the machineID
     * @return the Machine ID
     */
    public int getMachineId() {
        return machineId;
    }
}

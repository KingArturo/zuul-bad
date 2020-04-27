/**
 * Clae que representa un objeto que tiene una descipcion y
 * un peso.
 */
public class Item {

    private String id;
    private String description;
    private int weight;
    private boolean take;

    /**
     * Contructor que tiene dos parametros de entrada
     * @param itemDescription
     * @param itemWeight
     */
    public Item(String itemId, String itemDescription, int itemWeight, boolean take) {
        id = itemId;
        description = itemDescription;
        weight = itemWeight;
        this.take=take;
    }

    /**
     * Devuelve un String con la descripcion del objeto
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Devuelve un entero con el peso del objeto
     * @return
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Devuelve un String con el id del item
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * devuelve true si el ogjeto se puede coger.
     * @return
     */
    public boolean getTake() {
        return take;
    }
}
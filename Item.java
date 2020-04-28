/**
 * Clase que representa un objeto que tiene una descipcion y
 * un peso.
 */
public class Item {

    private String id;
    private String description;
    private int weight;

    /**
     * Contructor que tiene dos parametros de entrada
     * @param itemDescription
     * @param itemWeight
     */
    public Item(String itemId, String itemDescription, int itemWeight) {
        id = itemId;
        description = itemDescription;
        weight = itemWeight;
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

    public String getId() {
        return id;
    }
}
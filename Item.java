/**
 * Clase que representa un objeto que tiene una descipcion y
 * un peso.
 */
public class Item {

    private String id;
    private String description;
    private int weight;
    private boolean canTake;

    /**
     * Contructor que tiene dos parametros de entrada
     * @param itemDescription
     * @param itemWeight
     */
    public Item(String itemId, String itemDescription, int itemWeight, boolean canTake) {
        id = itemId;
        description = itemDescription;
        weight = itemWeight;
        this.canTake = canTake;
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
     * Devuelve un String con el id de objeto.
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo que devuelve true si el objeto se puede coger.
     * @return
     */
    public boolean canTake() {
        return canTake;
    }
}
/**
 * Clase que representa un objeto que tiene una descipcion y
 * un peso.
 */
public class Item {

    private String description;
    private int weight;

    /**
     * Contructor que tiene dos parametros de entrada
     * @param itemDescription
     * @param itemWeight
     */
    public Item(String itemDescription, int itemWeight) {
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
}
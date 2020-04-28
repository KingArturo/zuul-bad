import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salida;
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salida=new HashMap<>();
        items = new ArrayList<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    /* public void setExits(Room north, Room east, Room south, Room west, Room southEast) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
        if(southEast != null) {
            southEastExit = southEast;
        }
    } */

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
    * Devuelve la sala vecina a la actual que esta ubicada en la direccion indicada como parametro.
    *
    * @param salida Un String indicando la direccion por la que saldriamos de la sala actual
    * @return La sala ubicada en la direccion especificada o null si no hay ninguna salida en esa direccion
    */
    public Room getExit(String salida) {
        Room nextRoom = this.salida.get(salida);    
        return nextRoom;
    }

    /**
    * Devuelve la información de las salidas existentes
    * Por ejemplo: "Exits: north east west" o "Exits: south" 
    * o "Exits: " si no hay salidas disponibles
    *
    * @return Una descripción de las salidas existentes.
    */
    public String getExitString(){
        String salidas = "Exits: ";
        for(String sala : this.salida.keySet()) {
            salidas += sala + " ";
        }
        return salidas;
    }
    /**
    * Define una salida para esta sala
    * 
    * @param direccion La direccion de la salida (por ejemplo "north" o "southEast")
    * @param sala La sala que se encuentra en la direccion indicada
    */
    public void setExit(String direccion, Room sala) {
        salida.put(direccion, sala);
    }

    /**
    * Devuelve un texto con la descripcion completa de la habitacion, que 
    * incluye la descripcion corta de la sala y las salidas de la misma y 
    * la descripcion del objeto y su peso. Por ejemplo:
    *     You are in the lab
    *     Exits: north west southwest
    * @return Una descripcion completa de la habitacion incluyendo sus salidas
    */
    public String getLongDescription() {
        String text = "Te encuentras ";
        text += getDescription();
        text += "\n" + getExitString();
        if(!items.isEmpty()){
            for (Item item : items) {
                text += "\nVes un " + item.getDescription();
                text += " , peso " + item.getWeight() + "kg";
            }            
        }
        return text;
    }

    /**
     * Añade un item a la sala
     * @param description
     * @param weight
     */
    public void addItem(String id, String description, int weight) {
        Item item = new Item(id, description, weight);
        items.add(item);
    }

    /**
     * Devuelve la coleccion que contiene los objetos de la sala.
     * @return
     */
    public ArrayList<Item> getItems() {
        return items;
    }
}

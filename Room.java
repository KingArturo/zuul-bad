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
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Room southEastExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room southEast) 
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
    }

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
        Room nextRoom = null;
        if(salida.equals("north")) {
            nextRoom = northExit;
        }
        if(salida.equals("east")) {
            nextRoom = eastExit;
        }
        if(salida.equals("south")) {
            nextRoom = southExit;
        }
        if(salida.equals("west")) {
            nextRoom = westExit;
        }
        if(salida.equals("south-west")) {
            nextRoom = southEastExit;
        }
    
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
        if(northExit != null)
            salidas += "Norte ";
        if(eastExit != null)
            salidas += "Este ";
        if(southExit != null)
            salidas += "Sur ";
        if(westExit != null)
            salidas += "Oeste ";
        if(southEastExit != null)
            salidas += "Sur-Este";
        return salidas;
    }
}

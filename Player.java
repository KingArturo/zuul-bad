import java.util.Stack;

/**
 * Clase que representa al jugador, en ella se guardara toda la informacion
 * referente al jugador.
 */
public class Player {
    
    private Room currentRoom;
    private Stack<Room> previousRoom;

    public Player() {
        currentRoom = null;
        previousRoom = new Stack<>();
    }

    public void setCurrentRoom(Room sala) {
        currentRoom = sala;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        if (currentRoom.getExit(direction) == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom.add(currentRoom);
            currentRoom = currentRoom.getExit(direction);
            look();
        }
    }

    /**
     * Te envia a la sala en la que estabas, execpto al 
     * inicio del juego.
     */
    public void back() {
        if(!previousRoom.empty()) {
            currentRoom = previousRoom.get(previousRoom.size()-1);
            System.out.println(currentRoom.getLongDescription());
            previousRoom.remove(previousRoom.size()-1);
        }
        else {
            System.out.println("No hay lugar al que volver.");
        }
    }

    /**
     * Imprime por pantalla el siguiente mensaje
     * You have eaten now and you are not hungry any more
     */
    public void eat() {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    /**
     * Inprime por pantalla la informacion completa de la sala
     * en la que te encuentras
     */
    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

}
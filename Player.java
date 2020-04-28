import java.util.ArrayList;
import java.util.Stack;

/**
 * Clase que representa al jugador, en ella se guardara toda la informacion
 * referente al jugador.
 */
public class Player {
    
    private Room currentRoom;
    private Stack<Room> previousRoom;
    private ArrayList<Item> items;

    public Player() {
        currentRoom = null;
        previousRoom = new Stack<>();
        items = new ArrayList<>();
    }

    public void setCurrentRoom(Room sala) {
        currentRoom = sala;
    }

    /**
     * Permite cojer un objeto de la sala
     */
    public void take(Command comando) {
        if(!comando.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String objeto = comando.getSecondWord();

        if (currentRoom.getItems().isEmpty()) {
            System.out.println("No hay objetos en esta sala");
        }
        else {
            Item item = getItem(objeto);
            if(item != null) {
                items.add(item);
                System.out.println("Coges el objeto sin problemas");
                currentRoom.getItems().remove(item);
            }
            else {
                System.out.println("No esta ese objeto en la sala o no se puede coger");
            }
        }
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

    /**
     * Busca el item que tiene el id que se le pasa por parametro y 
     * comprueba que el objeto se puede coger.
     */
    private Item getItem(String item) {
        Item objeto = null;
        for(Item it : currentRoom.getItems()) {
            if(it.getId().equals(item)) {
                if(it.canTake()) {
                    objeto = it;
                }                
            }
        }
        return objeto;
    }

}
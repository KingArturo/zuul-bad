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
    private int maxWeigth;

    public Player(int maxPeso) {
        currentRoom = null;
        previousRoom = new Stack<>();
        items = new ArrayList<>();
        maxWeigth = maxPeso;
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
        }
    }

    /**
     * Permite ver los objetos que llebas encima
     * @param command
     */
    public void items() {
        if(items.isEmpty()) {
            System.out.println("No levas objetos encima");
        }
        else {
            String text = "Llevas encima ";
            for (Item item : items) {
                text += "un " + item.getDescription();
                text += ", su id es " + item.getId();
                text += ", peso " + item.getWeight() + "kg\n";
            }
            text += "Llevas " + totalWeigth() + "kg";
            System.out.println(text);
        }
    }

    /**
     * Metodo que permite soltar un objeto pasdo por parametro.
     * @param command
     */
    public void drop(Command comando) {
        if(!comando.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        String objeto = comando.getSecondWord();

        if (items.isEmpty()) {
            System.out.println("No llevas objetos");
        }
        else {
            Item item = getItemPlayerInventory(objeto);           
            if(item != null) {
                System.out.println("Sueltas el objeto");
                currentRoom.addItem(item.getId(), item.getDescription(), item.getWeight(), 
                item.canTake());
                items.remove(item);
            }
            else {
                System.out.println("No llevas ese objeto en el inventario");
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
            currentRoom = previousRoom.pop();
            System.out.println(currentRoom.getLongDescription());
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
     * Metodo que aumenta en 30 el peso maximo que puedes llevar 
     * si el jugador tiene el item "pesas".
     * @param item
     * @return
     */
    public void train() {
        if(getItemPlayerInventory("pesas") != null) {
            maxWeigth += 30;
            System.out.println("Utilizas las pesas para entrenas, ahora te sientes mas fuerte");
        }
        else {
            System.out.println("No tienes nada con lo que entrenar");
        }
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
                    if( maxWeigth > (it.getWeight() + totalWeigth()) ) {
                        objeto = it;
                    }
                    else {
                        System.out.println("No puedes llevar tanto peso.");
                    }
                }
                else {
                    System.out.println("Este objeto no se puede coger.");
                }                
            }
        }
        return objeto;
    }

    /**
     * Metodo que suma el total del peso de los objetos que 
     * llevas encima.
     */
    public int totalWeigth() {
        int peso = 0;
        for(Item item : items) {
            peso += item.getWeight();
        }
        return peso;
    }

    /**
     * Metodo que busca el id de un objeto Item y lo devuelve.
     * @param idItem
     * @return
     */
    private Item getItemPlayerInventory(String idItem) {
        Item objeto = null;
        for (Item item : items) {
            if(item.getId().equals(idItem)) {
                objeto = item;
            }
        }
        return objeto;
    }

}
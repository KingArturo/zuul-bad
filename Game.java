import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> previousRoom;
    private ArrayList<Item> playerItems;
    private int maxWeigth;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = new Stack<>();
        playerItems = new ArrayList<>();
        maxWeigth = 80;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrada, pasillo, habitacion1, bano, salonComedor, cocina, garaje, despensa, jardin;
      
        // create the rooms
        entrada = new Room("en la entrada de la casa");
        pasillo = new Room("en el pasillo");
        habitacion1 = new Room("en tu habitacion");
        bano = new Room("en el baño");
        salonComedor = new Room("en un salon");
        cocina = new Room("en una cocina");
        garaje = new Room("en un garaje");
        despensa = new Room("en una despensa");
        jardin = new Room("en un jardin");

        // Asociate the items
        habitacion1.addItem("escritorio", "escritorio gri", 16, true);
        bano.addItem("lavabo", "lavabo estropeado", 35, true);
        salonComedor.addItem("sofa", "sofa grande y maron", 80, false);
        salonComedor.addItem("mesa", "mesa heaxagonal de color burdeos", 60, true);
        garaje.addItem("coche", "coche marron", 500, false);
        jardin.addItem("cortacesped", "corta cesped", 25, true);
        
        // initialise room exits
        entrada.setExit("north", pasillo);
        entrada.setExit("salir", jardin);
        pasillo.setExit("north", habitacion1);
        pasillo.setExit("south", entrada);
        pasillo.setExit("east", salonComedor);
        pasillo.setExit("west", bano);
        habitacion1.setExit("south", pasillo);
        bano.setExit("east", pasillo);
        bano.setExit("south-east", despensa);
        salonComedor.setExit("east", garaje);
        salonComedor.setExit("south", cocina);
        salonComedor.setExit("west", pasillo);
        garaje.setExit("west", salonComedor);
        cocina.setExit("north", salonComedor);
        jardin.setExit("entrar", entrada);

        currentRoom = entrada;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLacationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("look")) {
            look();
        }
        else if(commandWord.equals("eat")) {
            eat();
        }
        else if(commandWord.equals("back")) {
            back();
        }
        else if(commandWord.equals("take")) {
            take(command);
        }
        else if(commandWord.equals("drop")) {
            drop(command);
        }
        else if(commandWord.equals("items")) {
            items();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Te envia a la sala en la que estabas, execpto al 
     * inicio del juego.
     */
    private void back() {
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
    private void eat() {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    /**
     * Inprime por pantalla la informacion completa de la sala
     * en la que te encuentras
     */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Permite cojer un objeto de la sala
     */
    private void take(Command comando) {
        if(!comando.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String objeto = comando.getSecondWord();

        if (currentRoom.getItems().isEmpty()) {
            System.out.println("No hay objetos en esta sala");
        }
        else {
            Item item = null;
            for (Item item2 : currentRoom.getItems()) {
                if(item2.getId().equals(objeto)) {
                    if(item2.getTake()) {
                        if(maxWeigth > totalWeigth() && 
                        maxWeigth > (item2.getWeight() + totalWeigth())) {
                            playerItems.add(item2);
                            item = item2;
                        }
                        else {
                            System.out.println("Llevas demasiado peso");
                        }
                    }
                    else {
                        System.out.println("Este objeto no se puede coger");
                    }
                }
            }
            if(item != null) {
                System.out.println("Coges el objeto sin problemas");
                currentRoom.removeItem(item.getId());
            }
        }
    }

    /**
     * Permite ver los objetos que llebas encima
     * @param command
     */
    private void items() {
        if(playerItems.isEmpty()) {
            System.out.println("No levas objetos encima");
        }
        else {
            String text = "Llevas encima ";
            for (Item item : playerItems) {
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
    private void drop(Command comando) {
        if(!comando.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        String objeto = comando.getSecondWord();

        if (playerItems.isEmpty()) {
            System.out.println("No llevas objetos");
        }
        else {
            Item item = null;
            Iterator<Item> it = playerItems.iterator();
            while(it.hasNext()) {
                Item item2 =it.next();
                String idItem = item2.getId();
                if(idItem.equals(objeto)) {
                    it.remove();
                    item = item2;
                }
            }            
            if(item != null) {
                System.out.println("Sueltas el objeto");
                currentRoom.addItem(item.getId(), item.getDescription(), item.getWeight(), 
                item.getTake());
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
    private void goRoom(Command command) 
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
            printLacationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Imprime por pantalla la informacion de la sala 
     * en la que te encuentras.
     */
    private void printLacationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }

    private int totalWeigth() {
        int weigth = 0;
        for (Item item : playerItems) {
            weigth += item.getWeight();
        }
        return weigth;
    }

}

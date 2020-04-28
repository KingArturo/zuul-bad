
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
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player(80);
        createRooms();
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
        garaje.addItem("cache", "coche marron", 500, false);
        jardin.addItem("cortacesped", "corta cesped", 25, true);
        garaje.addItem("pesas", "pesas", 10, true);
        
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

        player.setCurrentRoom(entrada);  // start game outside
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
        player.look();
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
            player.goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("look")) {
            player.look();
        }
        else if(commandWord.equals("eat")) {
            player.eat();
        }
        else if(commandWord.equals("back")) {
            player.back();
        }
        else if(commandWord.equals("take")) {
            player.take(command);
        }
        else if(commandWord.equals("items")) {
            player.items();
        }
        else if(commandWord.equals("drop")) {
            player.drop(command);
        }
        else if(commandWord.equals("train")) {
            player.train();
        }


        return wantToQuit;
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


}

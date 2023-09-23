import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;


/*
 * Class interacts directly with the command-line,
 * holds functions that call validator and SQLite functions.
 */
public class EventMain{

    private static EventsDB db = new EventsDB();
    private static int option;

    public static void main(String[] args) {

        System.out.println("Greetings!\nWelcome to the ARCHITECT'S rockin, bussin, Monolithic Application");
        Scanner scanner = new Scanner(System.in);
        String option ;

        // until the user quits
        do {
            // continually prompt for VALID action
            do {
                System.out.println("Enter the number associated with the action you'd like to complete: \n1 Insert\n2 View\n3 Quit");
                option = scanner.next();
            }
            while (!isValidOption(option));

            System.out.println("You picked option " + option + ".");

            EventMain.option = Integer.parseInt(option);
            // switch based on
            switch (EventMain.option) {
                case 1:
                    insert(scanner);
                    break;
                case 2:
                    view(scanner);
                    break;
                case 3:
                    System.out.println("See you later!!!");
                    break;
            }
        }
        while (EventMain.option != 3);
    }

    /* CLI sequence for insertion */
    private static void insert(Scanner scanner){
        // figure out which table
        Character table = tablePrompt(scanner);
        if (table == 'A')
            newEvent(scanner);
        else
            newParticipant(scanner);
    }

    /* CLI sequence for viewing */
    private static void view(Scanner scanner){
        // figure out which table
        Character table = tablePrompt(scanner);
        if (table == 'A')
            viewEvents();
        else
            viewParticipants();

    }

    private static void newEvent(Scanner scanner){

        System.out.println("You've selected to insert into the Events table.");
        System.out.println("Let's create a new Event!");

        // TODO: UNCOMMENT DO-WHILE & FILL IN WITH VALIDATION METHODS

        System.out.println("Insert UUID [Optional]\n");
        String uuid = scanner.hasNext() ? scanner.next() : UUID.randomUUID().toString();

        String date = "";
//        do {
        System.out.println("Insert Event Date [YYYY-MM-DD]");
        date = scanner.next();
//        } while (isValidDate(date));

        String time = "";
//        do {
        System.out.println("Insert Event Time [HH:MM AM/PM]");
        time = scanner.next();
//        } while (isValidTime(time));

        String title = "";
//        do {
        System.out.println("Insert Event Title [Max. 255]");
        title = scanner.next();
//        } while (isValidTitle(title));

        String description = "";
//        do {
        System.out.println("Insert Event Description [Max. 600]");
        description = scanner.next();
//        } while (isValidDescription(description));

        String email = "";
        do {
        System.out.println("Insert Event Host's Email");
        email = scanner.next();
        } while (!ParticipantValidator.isValidEmail(email));

        // TODO: insert method
        addEvent();
    }

    private static void newParticipant(Scanner scanner){
        System.out.println("You've selected to insert into the Registered table.");
        System.out.println("Let's register a participant!");

        // TODO: UNCOMMENT DO-WHILE & FILL IN WITH VALIDATION METHODS

        System.out.println("Insert Participant UUID [Optional]\n");
        String participant_uuid = scanner.hasNext() ? scanner.next() : UUID.randomUUID().toString();

        String event_UUID = "";
//        do {
        System.out.println("Insert Event ID [UUID]");
        event_UUID = scanner.next();
//        } while (isValidEventId(event_UUID));

        String name = "";
//        do {
        System.out.println("Insert Participant Name");
        name = scanner.next();
//        } while (isValidName(name));

        String email = "";
//        do {
        System.out.println("Insert Participant Email");
        email = scanner.next();
//        } while (isValidEmail(email));

        // TODO: insert method
        registerParticipant();
    }

    /* CLI sequence for determining table */
    private static Character tablePrompt(Scanner scanner){
        String table = "";
        // continually prompt for VALID table selection
        do {
            System.out.println("Pick a table you'd like to access. Enter the letter associated with the table you'd like to access:");
            System.out.println("A Events\nB Registered");
            table = scanner.next();
        }
        while (isValidTable(table));    // TODO: dk if we want a whole method for this

        return Character.toUpperCase(table.charAt(0));
    }

    /* check for valid input (table) */
    private static boolean isValidTable(String s){
        // if not one character
        if (s.length() != 1)
            return false;

        // if isn't A or B
        if (Character.toUpperCase(s.charAt(0)) != 'A' || Character.toUpperCase(s.charAt(0)) != 'B' )
            return false;

        return true;
    }

    /* check for valid input (option) */
    private static boolean isValidOption(String s){
        // if not one character
        if (s.length() != 1)
            return false;

        // if not a digit
        if (!Character.isDigit(s.charAt(0)))
            return false;

        // if not a digit in bounds (will never be negative)
        if (Integer.parseInt(s) > 3) {
            System.out.println("INVALID OPTION");
            return false;
        }

        return true;
    }

    /* Validates entry and creates an event in Events table */
    public static void addEvent(){
        //validate entry

        //SQL command to insert
    }
   
    /* Validates entry and creates a participant in Participants table */
    public static void registerParticipant(){
        //validate entry

        //SQL command to insert
    }

    /* Returns a table of participants currently registered */
    public static void viewEvents(){
        //SQL command to view table
        try {
            db.getEvents();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SOMETHING WENT WRONG!!!!!");
        }

    }

    /* Returns a table of participants currently registered */
    public static void viewParticipants(){
        //SQL command to view table
        try {
            db.getParticipants();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SOMETHING WENT WRONG!!!!!");
        }
    }
}
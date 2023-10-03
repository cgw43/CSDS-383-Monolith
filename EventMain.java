import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

/*
 * Class interacts directly with the command-line,
 * holds functions that call validator and SQLite functions.
 */
public class EventMain {

    private static EventsDB db = new EventsDB();
    private static int option;

    public static void main(String[] args) {

        System.out.println("Greetings!\nWelcome to the ARCHITECT'S Monolithic Event Planning Application");
        Scanner scanner = new Scanner(System.in);
        String option;

        // until the user quits
        do {
            // continually prompt for VALID action
            do {
                System.out.println(
                        "Enter the number associated with the action you'd like to complete: \n1 Insert\n2 View\n3 Quit");
                option = scanner.nextLine();
            } while (!Validator.isValidOption(option));

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
        } while (EventMain.option != 3);

        try {
            db.closeConnection();
        } catch (SQLException e) {

        }
    }

    /* CLI sequence for insertion */
    private static void insert(Scanner scanner) {
        // figure out which table
        Character table = tablePrompt(scanner);
        if (table == 'A')
            newEvent(scanner);
        else
            newParticipant(scanner);
    }

    /* CLI sequence for viewing */
    private static void view(Scanner scanner) {
        // figure out which table
        Character table = tablePrompt(scanner);
        if (table == 'A')
            viewEvents();
        else
            viewParticipants();

    }

    private static void newEvent(Scanner scanner) {

        System.out.println("You've selected to insert into the Events table.");
        System.out.println("Let's create a new Event!");

        // UUID INPUT
        String uuid = "";
        boolean status = false;
        boolean generate = false;

        do {
            System.out.println("Insert UUID [Optional: To generate, enter VOID]");
            uuid = scanner.nextLine();

            if (uuid.equals("VOID")) {
                generate = true;
            }
            // save the result of the check
            if (!generate) {
                status = Validator.isValidEventUUID(db, uuid, "events");
            }

            // if VOID : generate UUID
            if (generate) {
                // if randomUUID already exists in DB, generate new one
                while (!status) {
                    uuid = UUID.randomUUID().toString();
                    status = Validator.isValidEventUUID(db, uuid, "events");
                }
                System.out.println("Generated UUID: " + uuid);
            }

        } while (!status);

        // DATE INPUT
        String date = "";
        do {
            System.out.println("Insert Event Date [YYYY-MM-DD]");
            date = scanner.nextLine();
        } while (!Validator.isValidDate(date));

        // TIME INPUT
        String time = "";
        do {
            System.out.println("Insert Event Time [HH:MM AM/PM]");
            time = scanner.nextLine();
        } while (!Validator.isValidTime(time));

        // TITLE INPUT
        String title = "";
        do {
            System.out.println("Insert Event Title [Max. 255]");
            title = scanner.nextLine();
        } while (!Validator.isValidTitle(title));

        // DESCRIPTION INPUT
        String description = "";
        do {
            System.out.println("Insert Event Description [Max. 600]");
            description = scanner.nextLine();
        } while (!Validator.isValidDescription(description));

        // EMAIL INPUT
        String email = "";
        do {
            System.out.println("Insert Event Host's Email");
            email = scanner.nextLine();
        } while (!Validator.isValidEmail(email));

        addEvent(uuid, date, time, title, description, email);
    }

    private static void newParticipant(Scanner scanner) {
        System.out.println("You've selected to insert into the Registered table.");
        System.out.println("Let's register a participant!");

        // PARTICIPANT UUID INPUT
        String participant_uuid = "";
        boolean status = false;
        boolean generate = false;

        do {
            System.out.println("Insert Participant UUID [Optional: To generate, enter VOID]");
            participant_uuid = scanner.nextLine();

            if (participant_uuid.equals("VOID")) {
                generate = true;
            }
            // save the result of the check
            if (!generate) {
                status = Validator.isValidParticipantUUID(db, participant_uuid, "participants");
            }

            // if VOID : generate UUID
            if (generate) {
                // if randomUUID already exists in DB
                while (!status) {
                    participant_uuid = UUID.randomUUID().toString();
                    status = Validator.isValidParticipantUUID(db, participant_uuid, "events");
                }
                System.out.println("Generated Participant UUID: " + participant_uuid);
            }

        } while (!status);

        // EVENT UUID INPUT
        String event_UUID = "";
        do {
            System.out.println("Insert Event ID [UUID]");
            event_UUID = scanner.nextLine();
        } while (!Validator.isValidEventUUID(db, event_UUID, "participants"));

        // NAME INPUT
        String name = "";
        do {
            System.out.println("Insert Participant Name");
            name = scanner.nextLine();
        } while (!Validator.isValidName(name));

        // EMAIL INPUT
        String email = "";
        do {
            System.out.println("Insert Participant Email");
            email = scanner.nextLine();
        } while (!Validator.isValidEmail(email));

        registerParticipant(participant_uuid, event_UUID, name, email);
    }

    /* CLI sequence for determining table */
    private static Character tablePrompt(Scanner scanner) {
        String table = "";
        // continually prompt for VALID table selection
        do {
            System.out.println(
                    "Pick a table you'd like to access. Enter the letter associated with the table you'd like to access:");
            System.out.println("A Events\nB Registered");
            table = scanner.nextLine();
        } while (!Validator.isValidTable(table));

        return Character.toUpperCase(table.charAt(0));
    }

    /* Adds an event in Events table */
    public static void addEvent(String uuid, String date, String time, String title, String description, String email) {
        try {
            db.createEvent(uuid, date, time, title, description, email);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            System.out.println("Unable to add event, please try again");
        }
    }

    /* Validates entry and creates a participant in Participants table */
    public static void registerParticipant(String participant_uuid, String event_UUID, String name, String email) {
        try {
            db.registerParticipant(participant_uuid, event_UUID, name, email);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            System.out.println("Unable to register participant, please try again");
        }
    }

    /* Returns a table of participants currently registered */
    public static void viewEvents() {
        // SQL command to view table
        try {
            db.getEvents();
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            System.out.println("Unable to view events, please try again");
        }
    }

    /* Returns a table of participants currently registered */
    public static void viewParticipants() {
        // SQL command to view table
        try {
            db.getParticipants();
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            System.out.println("Unable to view participants, please try again");
        }
    }
}
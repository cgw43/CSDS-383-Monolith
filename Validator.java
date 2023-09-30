import java.util.regex.Pattern;

public class Validator {

    public static int isValidUUID(String s, String table){
        if (s.equals("VOID")){
            return 0;
        }
        // TODO: url param?
        return EventsDB.checkIfUUIDExists("", s, table) ? 1 : -1;
    }

    public static boolean isValidDate(String date){
        if (date.length() > 10 || !date.matches("[\\d-]+")){
            return false;
        }
        String[] dateValues = date.split("-");
        int year = Integer.parseInt(dateValues[0]);
        int month = Integer.parseInt(dateValues[1]);
        int day = Integer.parseInt(dateValues[2]);
        return year <= 9999 && month <= 12 && day <= 31;

    }

    public static boolean isValidTime(String time){
        return true; // TODO:
    }

    public static boolean isValidTitle(String eventTitle){
        return eventTitle.length() <= 255;
    }

    public static boolean isValidDescription(String eventDescription){
        return eventDescription.length() <= 600;
    }

    public static boolean isValidEmail(String hostEmail){
        String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(hostEmail).matches();
    }

    public static boolean isValidName(String name){
        return name.length() <= 600 && name.length() != 0;
    }


    /* check for valid input (table) */
    public static boolean isValidTable(String s){
        // if not one character
        if (s.length() != 1)
            return false;

        // return if user inputted A or B
        return Character.toUpperCase(s.charAt(0)) == 'A' || Character.toUpperCase(s.charAt(0)) == 'B';
    }

    /* check for valid input (option) */
    public static boolean isValidOption(String s){
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
}

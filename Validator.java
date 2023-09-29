import java.util.regex.Pattern;

public class Validator {

    //TODO: hardcoded for Events - to generalize
    public static int isValidUUID(String s, String table){
        if (s.equals("VOID")){
            return 0;
        }
        // TODO: url param?
        return UUIDCheck.checkIfUUIDExists("", s, table) ? 1 : -1;
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

}

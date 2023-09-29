import java.util.*;
import java.util.regex.Pattern;

public class EventValidator {
    public EventValidator(){
    }

    //TODO: hardcoded for Events - to generalize, add table input
    public static int validateUUID (String s){
        if (s.equals("VOID")){
            return 0;
        }
        // TODO: url param?
         return UUIDCheck.checkIfUUIDExists("", s, "Events") ? 1 : -1;
    }

    public static boolean validateDate (String date){
        if (date.length() > 10 || !date.matches("[\\d-]+")){
            return false;
        }
        String[] dateValues = date.split("-");
        int year = Integer.parseInt(dateValues[0]);
        int month = Integer.parseInt(dateValues[1]);
        int day = Integer.parseInt(dateValues[2]);
        return year <= 9999 && month <= 12 && day <= 31;

    }

    public static boolean validateTime (String time){
        return true; // TODO:
    }

    public static boolean validateTitle (String eventTitle){
        return eventTitle.length() <= 255;
    }

    public static boolean validateDescription (String eventDescription){
        return eventDescription.length() <= 600;
    }

    public static boolean validateHostEmail (String hostEmail){
        String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(hostEmail).matches();
    }

}

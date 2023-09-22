import java.util.*;
import java.util.regex.Pattern;

public class EventValidator {
    public EventValidator(){
    }

    public void validateUUID (UUID eventUUID){

    }

    public void validateDate (String date){
        boolean isValid = true;
        if (date.length() > 10 || !date.matches("[\\d-]+")){
            isValid = false;
        }
        String[] dateValues = date.split("-");
        int year = Integer.parseInt(dateValues[0]);
        int month = Integer.parseInt(dateValues[1]);
        int day = Integer.parseInt(dateValues[2]);
        if (year > 9999 || month > 12 || day > 31){
            isValid = false;
        }
        if(!isValid){
            throw new IllegalArgumentException("Date "+ date + " is invalid. Please try again.");
        }
    }

    public void validateTime (String time){

    }

    public void validateTitle (String eventTitle){
        if (eventTitle.length() > 255){
            throw new IllegalArgumentException("Event title "+ eventTitle + " is invalid. Please try again.");
        }
    }

    public void validateDescription (String eventDescription){
        if (eventDescription.length() > 600){
            throw new IllegalArgumentException("Event description "+ eventDescription + " is invalid. Please try again.");
        }
    }

    public void validateHostEmail (String hostEmail){
        String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if(!pattern.matcher(hostEmail).matches()){
            throw new IllegalArgumentException("E-mail "+ hostEmail + " is invalid. Please try again.");
        }
    }

}

import java.util.regex.*;
public class ParticipantValidator{

    public static void validateNameLength(String name){
        if(name.length() > 600 || name.length() == 0){
            throw new IllegalArgumentException("Name must be 1 to 600 characters long.");
        }

    }

    public static boolean validateEmail(String email){
        String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if(!pattern.matcher(email).matches()){
            return false;
            //throw new IllegalArgumentException("E-mail "+ email + " is invalid. Please try again.");
        }
        return true;
    }



}
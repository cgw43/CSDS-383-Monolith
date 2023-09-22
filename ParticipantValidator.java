import java.util.regex.*;
public class ParticipantValidator{

    public static void validateNameLength(String name){
        if(name.length() > 600 || name.length() == 0){
            throw new IllegalArgumentException("Name must be 1 to 600 characters long.");
        }

    }

    public static void validateEmail(String email){
        String EMAIL_PATTERN = "^(.+)@(\\S+)$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("E-mail "+ email + " is invalid. Please try again.");
        }
    }

}
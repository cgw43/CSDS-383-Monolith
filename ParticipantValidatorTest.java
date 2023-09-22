import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParticipantValidatorTest {
    
    @Test
    public void testEmailValidator(){
        String testEmail = "alp133@case.edu";
        assertTrue(ParticipantValidator.validateEmail(testEmail));
    }
}

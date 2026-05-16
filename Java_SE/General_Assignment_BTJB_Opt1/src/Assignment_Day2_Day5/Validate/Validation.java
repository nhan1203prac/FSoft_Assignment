package Assignment_Day2_Day5.Validate;

import Assignment_Day2_Day5.Entities.CandidateType;
import Assignment_Day2_Day5.Exception.BirthDayException;
import Assignment_Day2_Day5.Exception.EmailException;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Validation {
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    public static void validateBirthDay(String dob) throws BirthDayException {
        try {
            int year = Integer.parseInt(dob.substring(0, 4));
//            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int currentYear = LocalDate.now().getYear();
            if(year < 1900 || year > currentYear){
                throw new BirthDayException("BirthDay year must be between 1900 and " + currentYear);

            }
        }catch(NumberFormatException e){
            throw new BirthDayException("BirthDay format invalid");
        }
    }

    public static void validateEmail(String email) throws EmailException {
        if(email == null || !EMAIL_REGEX.matcher(email).matches()){
            throw new EmailException("Email format is invalid: "+email);
        }
    }

    public static void validateCandidateID(String id, CandidateType type) throws Exception {
        if(!id.startsWith(type.getPrefix())){
            throw new Exception("CandatateID must start with " + type.getPrefix() + "for type " + type);

        }
    }
}

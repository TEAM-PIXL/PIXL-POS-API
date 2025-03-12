package pos.api.teampixl.org.common.exceptions.validation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class to handle validation exceptions.
 */
public class Exceptions {

    /**
     * Throw a new exception with a message
     * @param MESSAGE The message to throw with the exception
     */
    public static void throwException( String MESSAGE ) {
        throw new RuntimeException( MESSAGE );
    }

    /**
     * Throw a new exception with a message and a cause
     * @param MESSAGE The message to throw with the exception
     * @param CAUSE The cause of the exception
     */
    public static void throwException( String MESSAGE, Throwable CAUSE ) {
        throw new RuntimeException( MESSAGE, CAUSE );
    }

    /**
     * Check if all validation codes are successful
     * @param VALIDATION_CODES A list of validation codes to check if all are successful
     * @return boolean if all validation codes are successful or not
     */
    public static boolean isSuccessful( Collection<ValidationCode> VALIDATION_CODES ) {
        return VALIDATION_CODES.stream()
                                .allMatch(
                                ValidationCode ->
                                ValidationCode == pos.api.teampixl.org.common.exceptions.validation.ValidationCode.SUCCESS
                                );
    }

    /**
     * Return a string with the message and validation codes in order to display to the user and aid in debugging
     * @param MESSAGE A defined message to return with the validation codes
     * @param VALIDATION_CODES A list of validation codes to return based on list passed
     * @return string with message and validation codes
     */
    public static String returnStatus( String MESSAGE, Collection<ValidationCode> VALIDATION_CODES ) {
        Collection<ValidationCode> ERROR_CODES = new ArrayList<>();
        VALIDATION_CODES.forEach( ValidationCode -> {
            if ( ValidationCode != pos.api.teampixl.org.common.exceptions.validation.ValidationCode.SUCCESS ) {
                ERROR_CODES.add( ValidationCode );
            }
        });
        return MESSAGE + " " + ERROR_CODES;
    }

    private static String generateErrorMessage( ValidationCode STATUS ) {
        return "The following error occurred: " + STATUS;
    }

    /**
     * Handle the validation code and return the error message
     * @param CODE The validation code to handle
     * @return the error message
     */
    public static String handleStatusCode( ValidationCode CODE ) {
        System.out.println( generateErrorMessage( CODE ) );
        return generateErrorMessage( CODE );
    }
}

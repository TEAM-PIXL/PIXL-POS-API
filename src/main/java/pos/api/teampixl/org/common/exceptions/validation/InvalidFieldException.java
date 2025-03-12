package pos.api.teampixl.org.common.exceptions.validation;

public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException(String field) {
        super("Field(s) invalid: " + field);
    }
}

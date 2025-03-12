package pos.api.teampixl.org.common.exceptions.validation;

import java.util.Collection;

public class FieldValidationException extends RuntimeException {
    public FieldValidationException(Collection<ValidationCode> validationErrors) {
        super(Exceptions.returnStatus("Field validation errors occurred with the following codes:", validationErrors));
    }
}

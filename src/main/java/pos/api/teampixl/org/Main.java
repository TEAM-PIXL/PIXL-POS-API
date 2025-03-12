package pos.api.teampixl.org;

import pos.api.teampixl.org.models.user.Validators;
import pos.api.teampixl.org.common.exceptions.validation.ValidationCode;
import pos.api.teampixl.org.common.exceptions.validation.Exceptions;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        Collection<ValidationCode> result = Validators.validateUsersByUsername("partker");
        System.out.println(Exceptions.returnStatus("The codes returned were:", result));
    }
}

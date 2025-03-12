package pos.api.teampixl.org.models.user;

import pos.api.teampixl.org.common.exceptions.UserNotFoundException;
import pos.api.teampixl.org.common.exceptions.validation.ValidationCode;
import pos.api.teampixl.org.database.repositories.UserRepository;

import java.util.Collection;
import java.util.ArrayList;

public class Validators {

    private final static UserRepository userRepository = new UserRepository();
    
    public static Collection<ValidationCode> validateUsersByUsername(String username) {
        Collection<ValidationCode> validations = new ArrayList<>();
        if (username == null) { validations.add(ValidationCode.USERNAME_NULL); return validations; }
        if (username.length() < 4) validations.add(ValidationCode.USERNAME_TOO_SHORT);
        if (username.length() > 20) validations.add(ValidationCode.USERNAME_TOO_LONG);
        if (username.chars().anyMatch(Character::isSpaceChar)) validations.add(ValidationCode.USERNAME_CONTAINS_SPACES);
        if (!username.matches("^[a-zA-Z0-9]*$")) validations.add(ValidationCode.USERNAME_INVALID_CHARACTERS);
        if (username.chars().allMatch(Character::isDigit)) validations.add(ValidationCode.USERNAME_ONLY_DIGITS);
        try {
            boolean userExists = false;
            if (userRepository.find(username) != null) userExists = true;
            validations.add(userExists ? ValidationCode.USERNAME_TAKEN : ValidationCode.SUCCESS);
            return validations;
        } catch (UserNotFoundException e) {
            validations.add(ValidationCode.SUCCESS);
            return validations;
        }
    }

}

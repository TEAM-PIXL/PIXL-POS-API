package pos.api.teampixl.org.models.user;

import java.lang.reflect.InvocationTargetException;

import pos.api.teampixl.org.common.CaseConverter;
import pos.api.teampixl.org.common.exceptions.UserNotFoundException;
import pos.api.teampixl.org.common.exceptions.validation.Exceptions;
import pos.api.teampixl.org.common.exceptions.validation.InvalidFieldException;
import pos.api.teampixl.org.common.exceptions.validation.FieldValidationException;
import pos.api.teampixl.org.common.exceptions.validation.ValidationCode;
import pos.api.teampixl.org.database.repositories.UserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Validators {

    private final static UserRepository userRepository = new UserRepository();
    
    /**
     * Validates a username.
     * 
     * @param username the username to validate.
     * @return the collection of validation codes.
     */
    public Collection<ValidationCode> validateUsersByUsername(String username) {
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

    /**
     * Validates an email address.
     *
     * @param email the email address to validate.
     * @return  the collection of validation codes.
     */
    public Collection<ValidationCode> validateUsersByEmail(String email) {
        Collection<ValidationCode> validations = new ArrayList<>();
        if (email == null) { validations.add(ValidationCode.EMAIL_NULL); return validations; }
        if (email.chars().filter(ch -> ch == '@').count() != 1) validations.add(ValidationCode.EMAIL_INVALID_FORMAT);
        if (email.chars().anyMatch(Character::isSpaceChar)) validations.add(ValidationCode.EMAIL_CONTAINS_SPACES);
        try {
            boolean userExists = false;
            if (userRepository.findByEmail(email) != null) userExists = true;
            validations.add(userExists ? ValidationCode.EMAIL_TAKEN : ValidationCode.SUCCESS);
            return validations;
        } catch (UserNotFoundException e) {
            validations.add(ValidationCode.SUCCESS);
            return validations;
        }
    }

    /**
     * Validates a first name.
     *
     * @param firstName the first name to validate.
     * @return the collection of validation codes.
     */
    public Collection<ValidationCode> validateUsersByFirstName(String firstName) {
        Collection<ValidationCode> validations = new ArrayList<>();
        boolean emptyFirstName = firstName == null || firstName.trim().isEmpty();
        validations.add(emptyFirstName ? ValidationCode.INVALID_FIRST_NAME : ValidationCode.SUCCESS);
        return validations;
    }

    /**
     * Validates a last name.
     *
     * @param lastName the last name to validate.
     * @return the collection of validation codes.
     */
    public Collection<ValidationCode> validateUsersByLastName(String lastName) {
        Collection<ValidationCode> validations = new ArrayList<>();
        boolean emptyLastname = lastName == null || lastName.trim().isEmpty();
        validations.add(emptyLastname ? ValidationCode.INVALID_LAST_NAME : ValidationCode.SUCCESS);
        return validations;
    }

    /**
     * Validates a user's role.
     *
     * @param role the role to validate.
     * @return the collection of validation codes.
     */
    public Collection<ValidationCode> validateUsersByRole(String role) {
        Collection<ValidationCode> validations = new ArrayList<>();
        boolean nullRole = role == null;
        try {
            User.UserRole.valueOf(role);
        } catch (IllegalArgumentException e) {
            validations.add(ValidationCode.INVALID_USER_ROLE);
        }
        
        validations.add(nullRole ? ValidationCode.INVALID_USER_ROLE : ValidationCode.SUCCESS);
        return validations;
    }

    /**
     * Validates a user's status.
     *
     * @param username the username of the user to validate.
     * @return the collection of validation codes.
     */
    public Collection<ValidationCode> validateUsersByStatus(String username) {
        Collection<ValidationCode> validations = new ArrayList<>();
        try {
            User user = userRepository.find(username);
            if (!(boolean) user.getMetadataValue("is_active")) validations.add(ValidationCode.USER_INACTIVE);
            validations.add(ValidationCode.SUCCESS);
            return validations;
        } catch (UserNotFoundException e) {
            validations.add(ValidationCode.USER_NOT_FOUND);
            return validations;
        }
    }

    /**
     * Validates a user's additional information.
     *
     * @param additionalInfo the additional information to validate.
     * @return the collection of validation codes.
     */
    public Collection<ValidationCode> validateUsersByAdditionalInfo(String additionalInfo) {
        Collection<ValidationCode> validations = new ArrayList<>();
        boolean nullAdditionalInfo = additionalInfo == null || additionalInfo.trim().isEmpty();
        validations.add(nullAdditionalInfo ? ValidationCode.INVALID_USER_ADDITIONAL_INFO : ValidationCode.SUCCESS);
        return validations;
    }

    /**
     * Calls all validation methods for the object.
     * 
     * @param userDTO the user information to be created.
     */
    public void validateInput(UserDTO userDTO) {
        Collection<ValidationCode> validations = new ArrayList<>();
        Collection<Collection<ValidationCode>> validationResults = new ArrayList<>();

        validationResults.add(validateUsersByFirstName(userDTO.getFirstName()));
        validationResults.add(validateUsersByLastName(userDTO.getLastName()));
        validationResults.add(validateUsersByUsername(userDTO.getUsername()));
        validationResults.add(validateUsersByEmail(userDTO.getEmail()));
        validationResults.add(validateUsersByRole(userDTO.getRole()));
        validationResults.forEach(validations::addAll);

        if (!Exceptions.isSuccessful(validations)) {
            throw new FieldValidationException(validations);
        }
    }

    /**
     * Dyanmically calls all validation methods for a map object update.
     * 
     * @param map the patch update map to be patched.
     */
    public void validatePatchInput(Map<String, Object> map) {
        Collection<ValidationCode> validations = new ArrayList<>();
        Set<String> fields = new HashSet<>();
        for (String field : map.keySet()) {
            fields.add(CaseConverter.pascalToSnakeCase(field));
        } 
        if (!UserDTO.validFields.containsAll(fields)) {
            fields.removeAll(UserDTO.validFields);
            throw new InvalidFieldException(String.format("%s", fields));
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String methodSuffix = CaseConverter.snakeToPascalCase(entry.getKey());
            String methodName = "validateUsersBy" + methodSuffix;

            try {
                Object value = entry.getValue();
                Class<?> paramType = (value != null && value.getClass() == Boolean.class)
                    ? boolean.class 
                    : (value == null ? String.class : value.getClass());

                Method validationMethod = this.getClass().getMethod(methodName, paramType);
                @SuppressWarnings("unchecked")
                Collection<ValidationCode> result = (Collection<ValidationCode>) validationMethod.invoke(this, value);
                validations.addAll(result);
            } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException | NoSuchMethodException e) {
                validations.add(ValidationCode.INTERNAL_FAILURE);
            }
        }

        if (!Exceptions.isSuccessful(validations)) {
            throw new FieldValidationException(validations);
        }
    }
}

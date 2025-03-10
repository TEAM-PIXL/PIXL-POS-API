package pos.api.teampixl.org.common.exceptions;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String username) {
        super("User not found: " + username);
    }

}

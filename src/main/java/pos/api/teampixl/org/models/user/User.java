package pos.api.teampixl.org.models.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import pos.api.teampixl.org.models.data.DataManager;
import pos.api.teampixl.org.models.data.MetadataWrapper;

/**
 * Users class is a construct for creating Users object. Implements IDataManager.
 * <p>
 * Metadata:
 * - id: UUID
 * - first_name: first name
 * - last_name: last name
 * - username: username
 * - role: role
 * - created_at: timestamp for creation
 * - updated_at: timestamp for last update
 * - is_active: boolean
 * <p>
 * Data:
 * - password: password
 * - email: email
 * - additional_info: null
 * @see DataManager
 * @see MetadataWrapper
 */
public class User extends DataManager {

    /**
     * Enumerations for UserRole
     */
    public enum UserRole {
        WAITER,
        COOK,
        ADMIN
    }

    /**
     * Constructor for Users object.
     * @param firstName first name
     * @param lastName last name
     * @param username username
     * @param passwordHash password
     * @param email email
     * @param role role
     */
    public User(String firstName, String lastName, String username, String passwordHash, String email, UserRole role) {
        super(initializeMetadata());

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("first name cannot be null or empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("last name cannot be null or empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("role cannot be null");
        }

        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new IllegalArgumentException("password cannot be null or empty");
        }

        this.data.put("first_name", firstName);
        this.data.put("last_name", lastName);
        this.data.put("username", username);
        this.data.put("role", role);
        this.data.put("password", passwordHash);
        this.data.put("email", email);
        this.data.put("additional_info", null);
    }

    private static MetadataWrapper initializeMetadata() {

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("id", UUID.randomUUID().toString());
        metadataMap.put("created_at", System.currentTimeMillis());
        metadataMap.put("updated_at", System.currentTimeMillis());
        metadataMap.put("is_active", true);

        return new MetadataWrapper(metadataMap);
    }
}

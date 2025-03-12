package pos.api.teampixl.org.models.user;

import java.util.Map;
import java.util.Set;

public class UserDTO {

    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private String role;

    public UserDTO(String firstName, String lastName, String username, String password, String email, String role) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new java.util.HashMap<>();
        map.put("firstName", this.first_name);
        map.put("lastName", this.last_name);
        map.put("username", this.username);
        map.put("password", this.password);
        map.put("email", this.email);
        map.put("role", this.role);
        return map;
    }

    public static Set<String> validFields = Set.of("first_name", "last_name", "username", "password", "email", "role");
    
}

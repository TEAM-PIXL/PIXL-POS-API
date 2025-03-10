package pos.api.teampixl.org.controllers;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import pos.api.teampixl.org.common.exceptions.UserNotFoundException;
import pos.api.teampixl.org.models.user.User;
import pos.api.teampixl.org.models.user.UserDTO;
import pos.api.teampixl.org.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "The User API")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok(Map.of("message", "User created"));
    }
    
    @GetMapping("/{username}")
    @Operation(summary = "Get a user by username")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable String username) {
        User user = userService.getUser(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        return ResponseEntity.ok(user.toMap());
    }
    
    @PutMapping("/{username}")
    @Operation(summary = "Update a user by username")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable String username, @RequestBody UserDTO userDTO) {
        userService.updateUser(username, userDTO);
        return ResponseEntity.ok(Map.of("message", "User updated"));
    }
    
    @DeleteMapping("/{username}")
    @Operation(summary = "Delete a user by username")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok(Map.of("message", "User deleted"));
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<Collection<Map<String, Object>>> getUsers() {
        Collection<User> users = userService.getUsers();
        return ResponseEntity.ok(users.stream().map(User::toMap).toList());
    }

    @PatchMapping("/{username}")
    @Operation(summary = "Update a user by username")
    public ResponseEntity<Map<String, Object>> patchUser(@PathVariable String username, @RequestBody UserDTO userDTO) {
        userService.updateUser(username, userDTO);
        return ResponseEntity.ok(Map.of("message", "User updated"));
    }

    @DeleteMapping
    @Operation(summary = "Delete all users")
    public ResponseEntity<Map<String, Object>> deleteUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok(Map.of("message", "All users deleted"));
    }

}

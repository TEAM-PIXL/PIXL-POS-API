package pos.api.teampixl.org.database.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import pos.api.teampixl.org.common.logger.Logger;
import pos.api.teampixl.org.database.SQLite;
import pos.api.teampixl.org.models.user.User;
import pos.api.teampixl.org.models.user.UserDTO;

@Repository
public class UserRepository {

    Logger LOGGER = Logger.getLogger(UserRepository.class.getName());

    public User find(String username) {
        try (Connection conn = SQLite.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        User.UserRole.valueOf(rs.getString("role"))
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding user by username: " + e.getMessage());
        }
        return null;
    }

    public void save(UserDTO userDTO) {
        User user = new User(
            userDTO.getFirstName(),
            userDTO.getLastName(),
            userDTO.getUsername(),
            userDTO.getPassword(),
            userDTO.getEmail(),
            User.UserRole.valueOf(userDTO.getRole())
        );
        try (Connection conn = SQLite.connect();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (id, first_name, last_name, username, password, email, role, created_at, updated_at, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, user.getMetadataValue("id").toString());
            stmt.setString(2, user.getMetadataValue("first_name").toString());
            stmt.setString(3, user.getMetadataValue("last_name").toString());
            stmt.setString(4, user.getMetadataValue("username").toString());
            stmt.setString(5, user.getDataValue("password").toString());
            stmt.setString(6, user.getDataValue("email").toString());
            stmt.setString(7, user.getMetadataValue("role").toString());
            stmt.setLong(8, (long) user.getMetadataValue("created_at"));
            stmt.setLong(9, (long) user.getMetadataValue("updated_at"));
            stmt.setBoolean(10, (boolean) user.getMetadataValue("is_active"));
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error saving user: " + e.getMessage());
        }
    }
    
    public void update(String username, UserDTO userDTO) {
        User user = find(username);
        if (user != null) {
            user.updateMetadata("first_name", userDTO.getFirstName());
            user.updateMetadata("last_name", userDTO.getLastName());
            user.updateMetadata("username", userDTO.getUsername());
            user.getData().put("password", userDTO.getPassword());
            user.getData().put("email", userDTO.getEmail());
            user.updateMetadata("role", userDTO.getRole());
            try (Connection conn = SQLite.connect();
                 PreparedStatement stmt = conn.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?, role = ? WHERE username = ?")) {
                stmt.setString(1, user.getMetadataValue("first_name").toString());
                stmt.setString(2, user.getMetadataValue("last_name").toString());
                stmt.setString(3, user.getMetadataValue("username").toString());
                stmt.setString(4, user.getDataValue("password").toString());
                stmt.setString(5, user.getDataValue("email").toString());
                stmt.setString(6, user.getMetadataValue("role").toString());
                stmt.setString(7, username);
                stmt.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("Error updating user by username: " + e.getMessage());
            }
        }
    }

    public void delete(String username) {
        try (Connection conn = SQLite.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting user by username: " + e.getMessage());
        }
    }

    public Collection<User> findAll() {
        Collection<User> users = new ArrayList<>();
        try (Connection conn = SQLite.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    User.UserRole.valueOf(rs.getString("role"))
                ));
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    public void deleteAll() {
        try (Connection conn = SQLite.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting all users: " + e.getMessage());
        }
    }
}

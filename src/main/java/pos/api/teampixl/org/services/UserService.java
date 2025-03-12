package pos.api.teampixl.org.services;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Service;

import pos.api.teampixl.org.database.repositories.UserRepository;
import pos.api.teampixl.org.models.user.User;
import pos.api.teampixl.org.models.user.UserDTO;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser(String username) {
        userRepository.delete(username);
    }

    public void updateUser(String username, UserDTO userDTO) {
        userRepository.update(username, userDTO);
    }

    public void patchUser(String username, Map<String, Object> patchMap) {
        userRepository.patch(username, patchMap);
    }

    public void createUser(UserDTO userDTO) {
        userRepository.save(userDTO);
    }

    public User getUser(String username) {
        return userRepository.find(username);
    }

    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
package pos.api.teampixl.org;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pos.api.teampixl.org.models.user.UserDTO;

public class Main {
    public static void main(String[] args) {
        Map<String, Object> update = new HashMap<String, Object>(){
            {
                put("firstName", "Rennie");
                put("lastName", "Parker");
                put("random", "Nothing");
            }
        };

        UserDTO user = new UserDTO("James", "Figurland", "jfigr2", "Secur3P@ssw0rd!", "jfig@gmail.com", "ADMIN");
        System.out.println(user.toMap());
        System.out.println(update);

        Map<String, Object> userMap = user.toMap();
        System.out.println(userMap.keySet());
        System.out.println(update.keySet());
        System.out.println(userMap.keySet().containsAll(update.keySet()));

        Collection<String> arr = update.keySet();
        for(String item : arr) {
            System.out.println(item);
        }
    }
}

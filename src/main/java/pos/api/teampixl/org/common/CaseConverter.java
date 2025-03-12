package pos.api.teampixl.org.common;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CaseConverter {

    /**
     * Converts snake_case to PascalCase.
     */
    public static String snakeToPascalCase(String snake) {
        if (snake == null || snake.isEmpty()) return snake;
        return Arrays.stream(snake.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase())
                .collect(Collectors.joining());
    }

    /**
     * Converts snake_case to camelCase.
     */
    public static String snakeToCamelCase(String snake) {
        String pascal = snakeToPascalCase(snake);
        return pascal.isEmpty()
            ? pascal 
            : pascal.substring(0, 1).toLowerCase() + pascal.substring(1);
    }

    /**
     * Converts PascalCase to snake_case.
     */
    public static String pascalToSnakeCase(String pascal) {
        if (pascal == null) return null;
        return pascal
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .toLowerCase();
    }

    /**
     * Converts camelCase to snake_case.
     */
    public static String camelToSnakeCase(String camel) {
        if (camel == null) return null;
        return camel
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .toLowerCase();
    }

    /**
     * Converts snake_case to SCREAMING_SNAKE_CASE.
     */
    public static String snakeToScreamingSnakeCase(String snake) {
        return snake == null ? null : snake.toUpperCase();
    }

    /**
     * Converts SCREAMING_SNAKE_CASE to snake_case.
     */
    public static String screamingSnakeToSnakeCase(String screamingSnake) {
        return screamingSnake == null ? null : screamingSnake.toLowerCase();
    }
}
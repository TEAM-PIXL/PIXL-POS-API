package pos.api.teampixl.org.controllers;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import pos.api.teampixl.org.database.SQLite;

import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Default", description = "The default API path.")
public class APIController {

    public APIController() { }

    @GetMapping("/")
    @Operation(summary = "Check connection to database.")
    public ResponseEntity<Map<String, Object>> getConnection() {
        try {
            Connection connection = SQLite.connect();
            connection.close();
        } catch (SQLException e) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("message", "Database connection is alive."));
    }
    
}

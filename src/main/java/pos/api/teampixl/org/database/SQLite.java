package pos.api.teampixl.org.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.zaxxer.hikari.HikariDataSource;

import pos.api.teampixl.org.common.logger.Logger;

/**
 * This class provides methods to establish a connection to the SQLite database and initialize the database.
 */
public class SQLite {

    private static final Logger LOGGER = Logger.getLogger(SQLite.class.getName());

    private static final String DB_URL;

    static {
        DB_URL = "jdbc:sqlite:" + getDatabaseFilePath();
    }

    private static String getDatabaseFilePath() {
        File resourceDir = new File("src/main/resources/pos/api/teampixl/org/database");
        File dbFile = new File(resourceDir, "pixlpos.db");

        return dbFile.getAbsolutePath();
    }

    private static String getQueries() {
        File resourceDir = new File("src/main/resources/pos/api/teampixl/org/database");
        File queryFile = new File(resourceDir, "schema.sql");
        StringBuilder queries = new StringBuilder();
        try (Scanner scanner = new Scanner(queryFile)) {
            while (scanner.hasNextLine()) {
            queries.append(scanner.nextLine()).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Query file not found: " + e.getMessage());
        }
        return queries.toString();
    }

    private static final HikariDataSource dataSource = new HikariDataSource();

    private static void logSql(String sql) {
        LOGGER.info("Executing SQL: " + sql);
    }

    static {
        dataSource.setJdbcUrl(DB_URL);
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection connect() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Initializes the SQLite database and creates the tables if they do not exist. Also creates 
     * the necessary indexes and constraints if they do not already exist.
     * <p>
     * Tables:
     * - users
     * - settings
     * - menu_items
     * - orders
     * - order_items
     * - ingredients
     * - stock
     * - menu_item_ingredients
     * <p>
     * The method logs the SQL statements executed to create the tables.
     */
    public static void initializeDatabase() {

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            
            String[] queries = getQueries().split(";");
            for (String query : queries) {
                if (query.trim().isEmpty()) continue; 
                
                logSql(query);
                try {
                    stmt.execute(query);
                } catch (SQLException ex) {
                    LOGGER.error("SQL Execution Failed: " + query + " - " + ex.getMessage());
                }
            }
            
            LOGGER.info("Database initialized successfully at: " + SQLite.DB_URL);
        } catch (SQLException e) {
            LOGGER.error("Database initialization error: " + e.getMessage());
        }

    }
}

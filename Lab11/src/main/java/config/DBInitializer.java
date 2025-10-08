package config;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;

public class DBInitializer {
    private static final String PROPERTIES_FILE = "src/main/resources/db.properties";

    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        Properties props = new Properties();
        props.load(new FileInputStream(PROPERTIES_FILE));

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, username, password);
    }

    public static void runSqlScript(Connection connection, String resourcePath) throws IOException, SQLException {
        InputStream inputStream = DBInitializer.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new FileNotFoundException("SQL file not found: " + resourcePath);
        }

        String sql = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.joining("\n"));

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}

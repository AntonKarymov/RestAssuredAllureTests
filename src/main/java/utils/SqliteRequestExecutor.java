package utils;

import java.sql.*;

import static utils.Properties.PROPERTIES;

public class SqliteRequestExecutor {
    public ResultSet executeQuery(String query) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            new RuntimeException("Can't find JDBC class");
        }

        String workingDir = System.getProperty("user.dir");
        String connStr = String.format("jdbc:sqlite:%s/src/service/%s", workingDir, PROPERTIES.getDbName());

        ResultSet queryResult = null;
        try (
                Connection con = DriverManager.getConnection(connStr);
                PreparedStatement psForQuery = con.prepareStatement(query)) {
            try (ResultSet rs = psForQuery.executeQuery()) {
                queryResult = rs;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryResult;
    }
}

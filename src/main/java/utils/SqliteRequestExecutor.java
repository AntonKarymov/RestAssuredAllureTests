package utils;

import java.sql.*;

import static utils.Properties.PROPERTIES;

public class SqliteRequestExecutor {
    public int getUploadsCount(String id) {
        String countByIdSql = String.format("SELECT count(*) AS total FROM uploads WHERE id = %s", id);
        return ((Integer) executeQuery(countByIdSql, "total")).intValue();
    }

    public String getUserId(String id) {
        return getUploadsRowValue(id, "user_id");
    }

    public String getPayload(String id) {
        return getUploadsRowValue(id, "payload_md5");
    }

    private Object executeQuery(String query, String columnName) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            new RuntimeException("Can't find JDBC class");
        }

        String workingDir = System.getProperty("user.dir");
        String connStr = String.format("jdbc:sqlite:%s/src/service/%s", workingDir, PROPERTIES.getDbName());

        Object queryResult = null;
        try (
                Connection con = DriverManager.getConnection(connStr);
                PreparedStatement psForQuery = con.prepareStatement(query);
                ResultSet rs = psForQuery.executeQuery()) {
            if(rs.next()){
                queryResult = rs.getObject(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryResult;
    }

    private String getUploadsRowValue(String uploadId, String column) {
        String selectByIdFromUploads = String.format("SELECT %s FROM uploads WHERE id = %s", column, uploadId);
        return String.valueOf(executeQuery(selectByIdFromUploads, column));
    }
}

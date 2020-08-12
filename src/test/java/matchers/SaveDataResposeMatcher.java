package matchers;

import objects.SaveDataResponse;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import static utils.Properties.PROPERTIES;

public class SaveDataResposeMatcher extends TypeSafeMatcher<SaveDataResponse> {

    @Override
    public void describeTo(Description description) {
        description.appendText("Status is OK, id not empty and unique, table uploads row is correct");
    }

    @Override
    protected boolean matchesSafely(SaveDataResponse saveDataResponse) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(saveDataResponse.getStatus().toString()).isEqualTo("OK");
        String id = saveDataResponse.getId();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String workingDir = System.getProperty("user.dir");
        String connStr = String.format("jdbc:sqlite:%s/src/service/%s", workingDir, PROPERTIES.getDbName());
        //todo: not good two sql query in one method
        //better create separate class for working db
        String countByIdSql = String.format("SELECT count(*) AS total FROM uploads WHERE id = %s", id);
        String selectByIdFromUploads = String.format("SELECT * FROM uploads WHERE id = %s", id);
        try (
                Connection con = DriverManager.getConnection(connStr);
                PreparedStatement psForCountByIdSql = con.prepareStatement(countByIdSql);
                PreparedStatement psForSelectByIdFromUploads = con.prepareStatement(selectByIdFromUploads)) {
            try (ResultSet rs = psForCountByIdSql.executeQuery()) {
                while (rs.next()) {
                    softAssertions.assertThat(rs.getInt("total")).isEqualTo(1);
                }
            }
            try (ResultSet rs = psForSelectByIdFromUploads.executeQuery()) {
                while (rs.next()) {
                    softAssertions.assertThat(rs.getString("user_id")).isEqualTo("supertest");
                    softAssertions.assertThat(isValidMD5(rs.getString("payload_md5")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        softAssertions.assertAll();

        return true;
    }

    public static Matcher<SaveDataResponse> isSaveDataSuccess() {
        return new SaveDataResposeMatcher();
    }

    private boolean isValidMD5(String s) {
        return s.matches("^[a-fA-F0-9]{32}$");
    }
}
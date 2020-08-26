package matchers;

import objects.SaveDataResponse;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import utils.SqliteRequestExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;

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

        SqliteRequestExecutor sqliteRequestExecutor = new SqliteRequestExecutor();

        String countByIdSql = String.format("SELECT count(*) AS total FROM uploads WHERE id = %s", id);
        ResultSet countByIdSqlRs = sqliteRequestExecutor.executeQuery(countByIdSql);
        try{
            while (countByIdSqlRs.next()) {
                softAssertions.assertThat(countByIdSqlRs.getInt("total")).isEqualTo(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String selectByIdFromUploads = String.format("SELECT * FROM uploads WHERE id = %s", id);
        ResultSet rs = sqliteRequestExecutor.executeQuery(selectByIdFromUploads);
        try{
            while (rs.next()) {
                softAssertions.assertThat(rs.getString("user_id")).isEqualTo("supertest");
                softAssertions.assertThat(isValidMD5(rs.getString("payload_md5")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //todo: fast workaround using softAssertions here
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
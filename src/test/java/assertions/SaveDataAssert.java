package assertions;

import objects.SaveDataResponse;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;
import utils.SqliteRequestExecutor;

public class SaveDataAssert extends AbstractAssert <SaveDataAssert, SaveDataResponse> {
    public SaveDataAssert(SaveDataResponse actual) {
        super(actual, SaveDataAssert.class);
    }

    public static SaveDataAssert assertThat(SaveDataResponse actual){
        return new SaveDataAssert(actual);
    }

    public SaveDataAssert isSaveDataSuccess(String username){
        SqliteRequestExecutor sqliteRequestExecutor = new SqliteRequestExecutor();
        String id = actual.getId();
        if(id == null){
            failWithMessage("Save data return status: %s, with message: %s", actual.getStatus(), actual.getError());
        }
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(sqliteRequestExecutor.getUploadsCount(id)).isEqualTo(1);
        softAssertions.assertThat(sqliteRequestExecutor.getUserId(id)).isEqualTo(username);
        softAssertions.assertThat(isValidMD5(sqliteRequestExecutor.getPayload(id)));
        softAssertions.assertAll();
        return this;
    }

    private boolean isValidMD5(String s) {
        return s.matches("^[a-fA-F0-9]{32}$");
    }
}
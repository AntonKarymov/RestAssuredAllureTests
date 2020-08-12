package objects;

public class SaveDataResponse {
    private Status status;
    private String id;
    private String error;

    public Status getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getError() {
        return error;
    }
}
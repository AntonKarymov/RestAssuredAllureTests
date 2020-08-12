package objects;

public enum Status {
    OK("OK"),
    ERROR("ERROR");

    private final String statusText;

    Status(final String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return statusText;
    }
}
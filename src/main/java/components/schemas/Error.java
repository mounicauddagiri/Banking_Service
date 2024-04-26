package components.schemas;

public class Error {
    private String message;
    private String code;

    public Error() {
        // Empty constructor
    }
    public Error(String message, String code) {
        this.message = message;
        this.code = code;
    }
    // Getter and setter methods for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter and setter methods for code
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

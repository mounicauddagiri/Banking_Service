package components.schemas;

public class AuthorizationResponse {
    private String messageId;
    private String userId;

    private ResponseCode responseCode;
    private Amount balance;

    public AuthorizationResponse() {
        // Empty constructor
    }

    public AuthorizationResponse(String messageId, String userId, ResponseCode responseCode, Amount balance) {
        this.messageId = messageId;
        this.userId = userId;
        this.responseCode = responseCode;
        this.balance = balance;
    }

    // Getter and setter methods for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter and setter methods for messageId
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    // Getter and setter methods for responseCode
    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    // Getter and setter methods for balance
    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }
}

package components.schemas;

public class LoadResponse {
    private String userId;
    private String messageId;
    private Amount balance;

    // Default constructor
    public LoadResponse() {
        // Empty constructor
    }

    // Constructor with parameters
    public LoadResponse(String userId, String messageId, Amount balance) {
        this.userId = userId;
        this.messageId = messageId;
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

    // Getter and setter methods for balance
    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }
}

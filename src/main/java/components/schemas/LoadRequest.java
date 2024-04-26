package components.schemas;

public class LoadRequest {
    private String userId;
    private String messageId;
    private Amount transactionAmount;

    // Default constructor
    public LoadRequest() {
        // Empty constructor
    }

    // Constructor with parameters
    public LoadRequest(String userId, String messageId, Amount transactionAmount) {
        this.userId = userId;
        this.messageId = messageId;
        this.transactionAmount = transactionAmount;
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

    // Getter and setter methods for transactionAmount
    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}

package components.schemas;

public class AuthorizationRequest {
    private String messageId;
    private String userId;
    private Amount transactionAmount;

    // Default constructor
    public AuthorizationRequest() {
        // Empty constructor
    }

    public AuthorizationRequest(String userId, String messageId, Amount transactionAmount) {
        this.messageId = messageId;
        this.userId = userId;
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

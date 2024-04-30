package components.requestBodies;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import components.db.Connection;
import components.db.Users;
import components.schemas.Amount;
import components.schemas.DebitCredit;
import components.schemas.Error;
import components.schemas.LoadResponse;

public class LoadRequest {

    public LoadRequest() {

    }
    public LoadRequest(Connection connection) {
        this.connection = connection;
    }
    public Connection connection = new Connection();
    public Users user = new Users();

    public  Error errorResponse = new Error();

    Gson gson = new Gson();
    public String handleLoadRequest(String req) {

        System.out.println("Load request initiated");
        System.out.println(req);
        LoadResponse res = new LoadResponse();
        JsonObject jsonBody = gson.fromJson(req, JsonObject.class);
        String userId = jsonBody.get("userId").getAsString();

        String message_id = jsonBody.get("messageId").getAsString();
        JsonObject transactionAmount = jsonBody.getAsJsonObject("transactionAmount");
        float transAmount = transactionAmount.get("amount").getAsFloat();
        if (!(transAmount > 0)) {
            errorResponse.setMessage("Amount must be non-negative");
            errorResponse.setCode("500");
            return gson.toJson(errorResponse);
        }
        System.out.println("TransAmount " + transAmount);
        String currency = transactionAmount.get("currency").getAsString();
        String debitOrCredit = transactionAmount.get("debitOrCredit").getAsString();
        String balance = null;
        try{
            user = connection.getUserDetailsFromDB(userId);
            if (user == null){
                user = new Users();
                user.setCurrency(currency);
                user.setAmount(transAmount);
                user.setId(Integer.parseInt(userId));
                if(connection.createUserInDB(user)){
                    res.setMessageId(message_id);
                    res.setUserId(userId);
                    balance = String.valueOf(String.format("%.2f", transAmount));
                    Amount a = new Amount(balance, currency, DebitCredit.valueOf(debitOrCredit));
                    user.setAmount(Float.parseFloat(balance));
                    res.setBalance(a);
                    if(!connection.updateMessageDB(userId, message_id, debitOrCredit)){
                        errorResponse.setMessage("SQL Server Error Response");
                        errorResponse.setCode("500");
                        return gson.toJson(errorResponse);
                    }
                }
            }
            else {
                res.setMessageId(message_id);
                res.setUserId(userId);
                float amount = user.getAmount();
                balance = String.valueOf(String.format("%.2f", amount + transAmount));
                Amount a = new Amount(balance, currency, DebitCredit.valueOf(debitOrCredit));
                user.setAmount(Float.parseFloat(balance));
                res.setBalance(a);
                if (!connection.updateUserDetailsInDB(user) || !connection.updateMessageDB(userId, message_id, debitOrCredit)){
                    errorResponse.setMessage("SQL Server Error Response");
                    errorResponse.setCode("500");
                    return gson.toJson(errorResponse);
                }
            }
            System.out.println("Updated balance in LoadReq: " + balance);
            
        }catch (Exception e){
            e.printStackTrace();
            errorResponse.setMessage("Server Error Response");
            errorResponse.setCode("500");
            return null;
        }
        return gson.toJson(res);
    }
}

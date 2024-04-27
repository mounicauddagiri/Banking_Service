package components.requestBodies;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import components.db.Connection;
import components.db.Users;
import components.schemas.Amount;
import components.schemas.DebitCredit;
import components.schemas.Error;
import components.schemas.LoadResponse;
import spark.Request;

public class LoadRequest {

    public String handleLoadRequest(Request req) {
        System.out.println("Load request initiated");
        LoadResponse res = new LoadResponse();
        Gson gson = new Gson();
        JsonObject jsonBody = gson.fromJson(req.body(), JsonObject.class);
        String userId = jsonBody.get("userId").getAsString();

        String message_id = jsonBody.get("messageId").getAsString();
        JsonObject transactionAmount = jsonBody.getAsJsonObject("transactionAmount");
        float transAmount = transactionAmount.get("amount").getAsFloat();
        if (!(transAmount > 0)) {
            components.schemas.Error errorResponse = new Error();
            errorResponse.setMessage("Amount must be non-negative");
            errorResponse.setCode("500");
            return gson.toJson(errorResponse);
        }
        System.out.println("TransAmount " + transAmount);
        String currency = transactionAmount.get("currency").getAsString();
        String debitOrCredit = transactionAmount.get("debitOrCredit").getAsString();
        Users user;
        String balance = null;
        try{
            user = Connection.getUserDetailsFromDB(userId);
            System.out.println(user.getId());
            if (user.getId() == 0){
                user.setCurrency(currency);
                user.setAmount(transAmount);
                user.setId(Integer.parseInt(userId));
                if(Connection.createUserInDB(user)){
                    res.setMessageId(message_id);
                    res.setUserId(userId);
                    balance = String.valueOf(String.format("%.2f", transAmount));
                    Amount a = new Amount(balance, currency, DebitCredit.valueOf(debitOrCredit));
                    user.setAmount(Float.parseFloat(balance));
                    res.setBalance(a);
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
                if (!Connection.updateUserDetailsInDB(user)) {
                    Error errorResponse = new Error();
                    errorResponse.setMessage("SQL Server Error Response");
                    errorResponse.setCode("500");
                    return gson.toJson(errorResponse);
                }
                
            }
            System.out.println("Updated balance in LoadReq: " + balance);
            
        }catch (Exception e){
            e.printStackTrace();
            components.schemas.Error errorResponse = new Error();
            errorResponse.setMessage("Server Error Response");
            errorResponse.setCode("500");
            return null;
        }
        return gson.toJson(res);
    }
}

package components.requestBodies;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import components.db.Connection;
import components.db.Users;
import components.schemas.Amount;
import components.schemas.AuthorizationResponse;
import components.schemas.DebitCredit;
import components.schemas.Error;
import components.schemas.ResponseCode;
import spark.Request;

public class AuthorizationRequest {

    public static String handleAuthorizationRequest(Request req){
        System.out.println("Authorization request initiated");
        AuthorizationResponse res = new AuthorizationResponse();
        Gson gson = new Gson();
        JsonObject jsonBody = gson.fromJson(req.body(), JsonObject.class);
        String userId = jsonBody.get("userId").getAsString();
        String message_id = jsonBody.get("messageId").getAsString();
        JsonObject transactionAmount = jsonBody.getAsJsonObject("transactionAmount");
        float transAmount = transactionAmount.get("amount").getAsFloat();
        System.out.println("TransAmount " + transAmount);
        String currency = transactionAmount.get("currency").getAsString();
        String debitOrCredit = transactionAmount.get("debitOrCredit").getAsString();
        try{
            Users user;
            user = Connection.getUserDetailsFromDB(userId);
            if (user == null){
                components.schemas.Error errorResponse = new Error();
                errorResponse.setMessage("User Not Found");
                errorResponse.setCode("500");
                return gson.toJson(errorResponse);
            }
            res.setMessageId(message_id);
            res.setUserId(userId);
            float amount = user.getAmount();
            System.out.println("Amount is " + amount);
            String balance = String.valueOf(amount);
            if(debitOrCredit.equals("CREDIT")){
                balance = String.valueOf(String.format("%.2f", amount + transAmount));
                res.setResponseCode(ResponseCode.APPROVED);
            } else if (debitOrCredit.equals("DEBIT")) {
                if(amount < transAmount){
                    res.setResponseCode(ResponseCode.DECLINED);
                    System.out.println("Insufficient balance");

                }
                else{
                    balance = String.valueOf(String.format("%.2f", amount - transAmount));
                    res.setResponseCode(ResponseCode.APPROVED);
                }
            }
            Amount a = new Amount(balance, currency, DebitCredit.valueOf(debitOrCredit));
            user.setAmount(Float.parseFloat(balance));
            res.setBalance(a);
            //  Updating the balance amount in the database
            if(res.getResponseCode().equals(ResponseCode.APPROVED)) {
                if (!Connection.updateUserDetailsInDB(user)) {
                    Error errorResponse = new Error();
                    errorResponse.setMessage("SQL Server Error Response");
                    errorResponse.setCode("500");
                    return gson.toJson(errorResponse);
                }
            }
            System.out.println("Updated balance in AuthReq: " + balance);
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

package components.responses;
import com.google.gson.Gson;
import components.schemas.Error;
import spark.Request;
import spark.Response;

import java.time.LocalDateTime;

public class Ping {
    public String getResponse(Request req, Response res) {
        try{
            components.schemas.Ping result = new components.schemas.Ping();
            result.getServerTime();
            System.out.println(result.getServerTime());
            res.status(200);
            res.type("application/json");
            return String.valueOf(result.getServerTime());
        }
        catch(Exception e){
            res.status(500);
            res.type("application/json");
            Error errorResponse = new Error();
            errorResponse.setMessage("Server Error Response");
            errorResponse.setCode("500");
            return String.valueOf(errorResponse.getMessage());
        }
    }
}

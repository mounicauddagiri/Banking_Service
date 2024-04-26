package controller;
import components.requestBodies.AuthorizationRequest;
import components.responses.Ping;

import com.google.gson.Gson;
import components.schemas.AuthorizationResponse;
import components.schemas.Error;
import spark.ResponseTransformer;

import static spark.Spark.*;
public class ApiController {
    public static class JsonTransformer implements ResponseTransformer {
        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }
    }

    public static void setupRoutes() {

        JsonTransformer jsonTransformer = new JsonTransformer();

        get("/ping", (req, res) -> {
            // Handle ping request
            Ping ping = new Ping();
            return ping.getResponse(req,res);
        });

        // Define routes for authorization and load endpoints
        put("/authorization/:messageId", (req, res) -> {
            AuthorizationRequest request = new AuthorizationRequest();
            AuthorizationResponse response = request.handleAuthorizationRequest(req);
            if (response == null){
                res.status(500);
                res.type("application/json");
                return new Error("Internal Server Error", "500");
            }
            res.status(201);
            res.type("application/json");
            return response;
        }, jsonTransformer);

        put("/load/:messageId", (req, res) -> {
            String messageId = req.params(":messageId");
            String requestBody = req.body();
            // Handle load request
            return handleLoadRequest(messageId, requestBody);
        });
    }

    private static String handleAuthorizationRequest(String messageId, String requestBody) {
        // Implement logic to handle authorization request
        return "Authorization request handled for messageId: " + messageId;
    }

    private static String handleLoadRequest(String messageId, String requestBody) {
        // Implement logic to handle load request
        return "Load request handled for messageId: " + messageId;
    }
}

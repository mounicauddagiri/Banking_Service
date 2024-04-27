package controller;
import com.google.gson.JsonObject;
import components.requestBodies.AuthorizationRequest;
import components.responses.Ping;

import com.google.gson.Gson;
import components.schemas.Error;
import components.requestBodies.LoadRequest;
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
            String response = request.handleAuthorizationRequest(req);
            if (response == null){
                res.status(500);
                res.type("application/json");
                return new Error("Internal Server Error", "500");
            }
            res.status(201);
            res.type("application/json");
            Gson gson = new Gson();
            JsonObject jsonBody = gson.fromJson(response, JsonObject.class);
            if (jsonBody.has("code")){
                res.status(500);
            }
            return jsonBody;
        }, jsonTransformer);

        put("/load/:messageId", (req, res) -> {

            LoadRequest request = new LoadRequest();
            String response = request.handleLoadRequest(req);
            if (response == null){
                res.status(500);
                res.type("application/json");
                return new Error("Internal Server Error", "500");
            }
            res.status(201);
            res.type("application/json");
            Gson gson = new Gson();
            JsonObject jsonBody = gson.fromJson(response, JsonObject.class);
            if (jsonBody.has("code")){
                res.status(500);
            }
            return jsonBody;
        }, jsonTransformer);
    }

}

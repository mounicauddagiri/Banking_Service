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

    AuthorizationRequest authorizationRequest = new AuthorizationRequest();
    LoadRequest loadRequest = new LoadRequest();

    public void setupRoutes() {

        JsonTransformer jsonTransformer = new JsonTransformer();

        get("/ping", (req, res) -> {
            // Handle ping request
            Ping ping = new Ping();
            return ping.getResponse(res);
        }, jsonTransformer);

        // Define routes for authorization endpoints
        put("/authorization/:messageId", (req, res) -> {
//            AuthorizationRequest authorizationRequest = new AuthorizationRequest();
            String response = authorizationRequest.handleAuthorizationRequest(req.body());
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

        // Define routes for load endpoints
        put("/load/:messageId", (req, res) -> {
//            LoadRequest loadRequest = new LoadRequest();
            String response = loadRequest.handleLoadRequest(req.body());
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

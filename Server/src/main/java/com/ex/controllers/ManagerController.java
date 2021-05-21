package com.ex.controllers;

import com.ex.pojos.Manager;
import com.ex.services.MongoManagerService;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;

import static com.mongodb.client.model.Filters.*;

public class ManagerController implements Controller{

    public ManagerController(MongoManagerService service) {
        this.service = service;
    }

    public ManagerController() {
    }

    private MongoManagerService service;

    @Override
    public JSONObject doPost(String path, String body) {
        if(path.equals("/login")) {
            return login(body);
        }else{
            return null;
        }
    }

    public JSONObject login(String body){
        JSONParser parser = new JSONParser();
        JSONObject req = null;
        try {
            req = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Bson filter = and(eq("username", req.get("username").toString()),
                    eq("password", req.get("password").toString()));
            List<Manager> managers = (ArrayList<Manager>)service.find(filter);
            JSONObject resp = null;

            if(managers.size() > 0) {
                resp = new JSONObject();
                for (Manager m : managers) {
                    System.out.println("manager is " + m);
                    resp.put("_id", m.getId().toString());
                    resp.put("firstName", m.getFirstName());
                    resp.put("lastName", m.getLastName());
                    resp.put("username", m.getUsername());
                    resp.put("password", m.getPassword());
                    resp.put("loginStatus", true);
                    resp.put("role", "Manager");
                }
            }
//            }else{
//                resp.put("loginStatus", false);
//            }

        return resp;
    }
}

package com.ex.controllers;

import com.ex.pojos.ReimbursementRequest;
import com.ex.services.MongoReimbursementRequestService;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

import static com.mongodb.client.model.Filters.*;

public class ReimbursementRequestController implements Controller{

    public ReimbursementRequestController() {
    }

    public ReimbursementRequestController(MongoReimbursementRequestService service) {
        this.service = service;
    }

    private MongoReimbursementRequestService service;

    @Override
    public JSONObject doPost(String path, String body) {
        switch(path){
            case "/all-pending-rrs":
                return allPendingRRs(body);
            case "/all-resolved-rrs":
                return allResolvedRRs(body);
            case "/create-rr":
                return createRR(body);
            case "/update-rr":
                return updateRR(body);
        }
        return null;
    }

    public JSONObject allPendingRRs(String body){
        List<ReimbursementRequest> rrs = (ArrayList<ReimbursementRequest>)service.find(exists("_id"));
        JSONObject resp = new JSONObject();

        for(ReimbursementRequest rr: rrs){
            System.out.println("rr is " + rr);
            JSONObject obj = new JSONObject();
            obj.put("_id", rr.getId().toString());
            obj.put("amount", rr.getAmount().toString());
            obj.put("timestamp", rr.getTimestamp().toString());
            obj.put("employee", rr.getEmployeeId().toString());
            obj.put("manager", rr.getManagerId().toString());
            obj.put("status", rr.getStatus());
            resp.put(obj.get("_id"), obj);
        }
        return resp;
    }

    public JSONObject allResolvedRRs(String body){
        List<ReimbursementRequest> rrs = (ArrayList<ReimbursementRequest>)service.find(exists("_id"));

        JSONObject resp = new JSONObject();

        for(ReimbursementRequest rr: rrs){
//                System.out.println("rr is " + rr);
            JSONObject obj = new JSONObject();
            obj.put("_id", rr.getId().toString());
            obj.put("amount", rr.getAmount().toString());
            obj.put("timestamp", rr.getTimestamp().toString());
            obj.put("employee", rr.getEmployeeId().toString());
            obj.put("manager", rr.getManagerId().toString());
            obj.put("status", rr.getStatus());
            resp.put(obj.get("_id"), obj);
        }
        return resp;
    }

    public JSONObject createRR(String body){
        ReimbursementRequest rr = new ReimbursementRequest();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //todo input validation.  make sure all fields are not null/empty
            rr.setAmount(new Integer(jsonObject.get("amount").toString()));
            rr.setTimestamp(new Date().toInstant());
            rr.setEmployeeId(new ObjectId(jsonObject.get("employee").toString()));
            rr.setManagerId(new ObjectId(jsonObject.get("employee").toString()));
            rr.setStatus("Pending");

            JSONObject resp = new JSONObject();
            service.save(rr);
            System.out.println("rr is " + rr);
            if(rr.getId() != null){
                resp.put("success", true);
            }else{
                resp.put("success", false);
            }
        return resp;
    }

    public JSONObject updateRR(String body){
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ReimbursementRequest rr = service.findOne(new ObjectId(jsonObject.get("_id").toString()));
        rr.setManagerId(new ObjectId(jsonObject.get("manager").toString()));
        rr.setStatus(jsonObject.get("status").toString());

        JSONObject resp = new JSONObject();
        service.save(rr);
        System.out.println("rr is " + rr);
        resp.put("message", "Updated Reimbursement Request " + rr);
        return resp;
    }
}

package com.ex.controllers;

import com.ex.pojos.Employee;
import com.ex.services.MongoEmployeeService;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.bson.types.ObjectId;
import org.json.simple.parser.ParseException;

import static com.mongodb.client.model.Filters.*;

import java.util.*;

public class EmployeeController implements Controller{

    public EmployeeController() {
    }

    public EmployeeController(MongoEmployeeService service) {
        this.service = service;
    }

    private MongoEmployeeService service;

    @Override
    public JSONObject doPost(String path, String body) {
        switch(path){
            case "/login":
                    return login(body);
            case "/register-employee":
                    return registerEmployee(body);
            case "/view-employee":
                    return viewEmployee(body);
            case "/all-employees":
                return allEmployees(body);
            case "/update-employee":
                return updateEmployee(body);
        }
        return null;
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
            List<Employee> employees = (ArrayList<Employee>)service.find(filter);
            JSONObject resp = null;

            if(employees.size() > 0) {
                resp = new JSONObject();
                for (Employee e : employees) {
                    System.out.println("employee is " + e);
                    resp.put("_id", e.getId().toString());
                    resp.put("firstName", e.getFirstName());
                    resp.put("lastName", e.getLastName());
                    resp.put("username", e.getUsername());
                    resp.put("password", e.getPassword());
                    resp.put("loginStatus", true);
                    resp.put("role", "Employee");
                }
            }
//            }else{
//                resp.put("loginStatus", false);
//            }
            return resp;
    }

    public JSONObject registerEmployee(String body){
        Employee e = new Employee();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(body);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        //todo input validation.  make sure all fields are not null/empty
            e.setFirstName(jsonObject.get("firstName").toString());
            e.setLastName(jsonObject.get("lastName").toString());
            e.setUsername(jsonObject.get("username").toString());
            e.setPassword(jsonObject.get("password").toString());

            JSONObject resp = new JSONObject();
            service.save(e);
            System.out.println("employee is " + e);
            if(e.getId() != null){
                resp.put("success", true);
            }else{
                resp.put("success", false);
            }
        return resp;
    }

    public JSONObject viewEmployee(String body){
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Bson filter = and(eq("firstName", jsonObject.get("firstName").toString()),
                eq("lastName", jsonObject.get("lastName").toString()));
        List<Employee> employees = (ArrayList<Employee>)service.find(filter);

        JSONObject resp = new JSONObject();
        for(Employee e: employees){
            System.out.println("employee is " + e);
            resp.put("_id", e.getId().toString());
            resp.put("firstName", e.getFirstName());
            resp.put("lastName", e.getLastName());
            resp.put("username", e.getUsername());
            resp.put("password", e.getPassword());
        }
        return resp;
    }

    public JSONObject allEmployees(String body){
        List<Employee> employees = (ArrayList<Employee>)service.find(exists("_id"));

        JSONObject resp = new JSONObject();

        for(Employee e: employees){
            System.out.println("employee is " + e);
            JSONObject obj = new JSONObject();
            obj.put("_id", e.getId().toString());
            obj.put("firstName", e.getFirstName());
            obj.put("lastName", e.getLastName());
            obj.put("username", e.getUsername());
            obj.put("password", e.getPassword());
            resp.put(obj.get("_id"), obj);
        }
        return resp;
    }

    public JSONObject updateEmployee(String body){
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject resp = new JSONObject();

            Employee e = service.findOne(new ObjectId(jsonObject.get("_id").toString()));

            //todo input validation.  make sure all fields are not null/empty
            if(e != null) {
                e.setFirstName(jsonObject.get("firstName").toString());
                e.setLastName(jsonObject.get("lastName").toString());
                e.setUsername(jsonObject.get("username").toString());
                e.setPassword(jsonObject.get("password").toString());

                service.save(e);
                System.out.println("e is " + e);
                resp.put("success", true);
            }else{
                resp.put("success", false);
            }

        return resp;
    }

}

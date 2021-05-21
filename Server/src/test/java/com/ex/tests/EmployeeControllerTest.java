package com.ex.tests;

import com.ex.MongoConnector;
import com.ex.controllers.EmployeeController;
import com.ex.daos.MongoEmployeeDao;
import com.ex.pojos.Employee;
import com.ex.pojos.Manager;
import com.ex.services.MongoEmployeeService;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.exists;

public class EmployeeControllerTest {

    private static MongoEmployeeDao ed;
    private static MongoEmployeeService es;
    private static EmployeeController ec;
    private static Employee e1;
    private static Employee e2;

    @Before
    public void beforeClass(){
        MongoConnector connector = new MongoConnector();
        connector.configure( () -> {
            CodecProvider codecProvider = PojoCodecProvider.builder().register("com.ex.pojos").build();
            CodecRegistry registry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(codecProvider));
            return MongoClientSettings.builder()
                    .applyConnectionString(connector.newConectionString("mongodb://localhost:27017/ers"))
                    .retryWrites(true)
                    .codecRegistry(registry)
                    .build();
        }).createClient();
        ed = new MongoEmployeeDao(connector,"ers", "employeestest");
        es = new MongoEmployeeService(ed);
        ec = new EmployeeController(es);
    }

    @Before
    public void setUp(){
        e1 = new Employee("Bill", "Clinton", "bill", "bill");
        e2 = new Employee("George", "Bush", "george", "george");
    }

    @After
    public void tearDown(){
        List<Employee> eList = (ArrayList<Employee>)es.find(exists("_id"));

        for(Employee e : eList){
            es.delete(e.getId());
        }
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Employee Controller Tests complete.");
    }

    @Test
    public void loginTest() {
        es.save(e1);
        String path = "/login";
        String body = "{\"username\": \"bill\", \"password\":\"bill\"}";
        JSONObject obj = ec.login(body);
        System.out.println("obj" + obj);
        String expected = "{\"firstName\":\"Bill\",\"lastName\":\"Clinton\",\"password\":\"bill\",\"role\"" +
                ":\"Employee\",\"_id\":\""+ e1.getId().toString() +"\",\"loginStatus\":true,\"username\":\"bill\"}";
        String actual = obj.toString();

        System.out.println("actual " + actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void registerEmployeeTest(){
        String path = "/register-employee";
        String body = "{\"firstName\": \"joe\", \"lastName\": \"joe\", \"username\": \"joe\", \"password\": \"joe\"}";
        System.out.println("body" + body);
        JSONObject obj = ec.registerEmployee(body);
        System.out.println("obj" + obj);
        boolean expected = true;
        boolean actual = (boolean) obj.get("success");

        System.out.println("actual " + actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void viewEmployeeTest(){
        es.save(e1);
        String path = "/view-employee";
        String body = "{\"firstName\": \"Bill\", \"lastName\":\"Clinton\"}";
        System.out.println("body" + body);
        JSONObject obj = ec.viewEmployee(body);
        System.out.println("obj" + obj);
        String expected = "{\"firstName\":\"Bill\",\"lastName\":\"Clinton\",\"password\":\"bill\",\"_id\":\""+ e1.getId().toString() +"\",\"username\":\"bill\"}";
        String actual = obj.toString();

        System.out.println("actual " + actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allEmployeesTest(){
        es.save(e1);
        es.save(e2);
        String path = "/all-employees";
        String body = "";
        System.out.println("body" + body);
        JSONObject obj = ec.allEmployees(body);
        System.out.println("obj" + obj);
        List<String> expected = new ArrayList<>();
        expected.add("{\"firstName\":\"Bill\",\"lastName\":\"Clinton\",\"password\":\"bill\",\"_id\":\"" + e1.getId().toString() + "\",\"username\":\"bill\"}");
        expected.add("{\"firstName\":\"George\",\"lastName\":\"Bush\",\"password\":\"george\",\"_id\":\"" + e2.getId().toString() + "\",\"username\":\"george\"}");

        List<String> actual = new ArrayList<>();
        actual.add(obj.get(e1.getId().toString()).toString());
        actual.add(obj.get(e2.getId().toString()).toString());

        System.out.println("actual " + actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateEmployeeTest(){
        es.save(e1);
        String path = "/update-employee";
        String body = "{\"firstName\": \"Bill\", \"lastName\": \"Clinton\", \"username\": \"joe\", \"password\": \"joe\", \"_id\": \"" + e1.getId().toString() + "\"}";
        System.out.println("body" + body);
        JSONObject obj = ec.updateEmployee(body);
        System.out.println("obj" + obj);
        boolean expected = true;
        boolean actual = (boolean) obj.get("success");

        System.out.println("actual " + actual);
        Assert.assertEquals(expected, actual);
    }

}

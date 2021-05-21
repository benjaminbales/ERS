package com.ex.tests;

import com.ex.AbstractApplication;
import com.ex.ERSApplication;
import com.ex.MongoConnector;
import com.ex.controllers.ManagerController;
import com.ex.daos.MongoManagerDao;
import com.ex.pojos.Manager;
import com.ex.pojos.ReimbursementRequest;
import com.ex.services.MongoManagerService;
import com.mongodb.MongoClientSettings;
import netscape.javascript.JSObject;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.exists;

public class ManagerControllerTest {

    private AbstractApplication app;
    private static MongoManagerDao md;
    private static MongoManagerService ms;
    private static ManagerController mc;
    private static Manager m1;

    @BeforeClass
    public static void setUpClass() {
        MongoConnector connector = new MongoConnector();
        connector.configure(() -> {
            CodecProvider codecProvider = PojoCodecProvider.builder().register("com.ex.pojos").build();
            CodecRegistry registry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(codecProvider));
            return MongoClientSettings.builder()
                    .applyConnectionString(connector.newConectionString("mongodb://localhost:27017/ers"))
                    .retryWrites(true)
                    .codecRegistry(registry)
                    .build();
        }).createClient();

        md = new MongoManagerDao(connector, "ers", "managerstest");
        ms = new MongoManagerService(md);
        mc = new ManagerController(ms);
    }

    @Before
    public void setUp(){
        m1 = new Manager("Bob", "Dole", "bob", "bob");
    }

    @After
    public void tearDown(){
        List<Manager> mList = (ArrayList<Manager>)ms.find(exists("_id"));

        for(Manager m : mList){
            ms.delete(m.getId());
        }
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Manager Controller Tests complete.");
    }

    @Test
    public void loginTest() {
        ms.save(m1);
        String path = "/login";
        String body = "{\"username\": \"bob\", \"password\":\"bob\"}";
        JSONObject obj = mc.login(body);
        System.out.println("obj" + obj);
        String expected = "{\"firstName\":\"Bob\",\"lastName\":\"Dole\",\"password\":\"bob\",\"role\"" +
                ":\"Manager\",\"_id\":\""+ m1.getId().toString() +"\",\"loginStatus\":true,\"username\":\"bob\"}";
        String actual = obj.toString();

        System.out.println("actual " + actual);
        Assert.assertEquals(expected, actual);
    }
}
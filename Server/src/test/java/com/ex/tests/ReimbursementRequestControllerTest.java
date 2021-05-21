package com.ex.tests;

import com.ex.AbstractApplication;
import com.ex.MongoConnector;
import com.ex.controllers.ReimbursementRequestController;
import com.ex.daos.MongoReimbursementRequestDao;
import com.ex.pojos.ReimbursementRequest;
import com.ex.services.MongoReimbursementRequestService;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.junit.*;

import java.time.Instant;
import java.util.*;

import static com.mongodb.client.model.Filters.exists;

public class ReimbursementRequestControllerTest {

    private AbstractApplication app;
    private static MongoReimbursementRequestDao rrd;
    private static MongoReimbursementRequestService rrs;
    private static ReimbursementRequestController rrc;
    private static ReimbursementRequest rr1;
    private static ReimbursementRequest rr2;
    private static ReimbursementRequest rr3;

    @BeforeClass
    public static void setUpClass(){
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

        rrd = new MongoReimbursementRequestDao(connector,"ers", "reimbursementrequeststest");
        rrs = new MongoReimbursementRequestService(rrd);
        rrc = new ReimbursementRequestController(rrs);
    }

    @Before
    public void setUp(){
        Date date = new Date();
        rr1 = new ReimbursementRequest(100,date.toInstant(),new ObjectId("609c222d0814b7564789732c"), new ObjectId("609c222d0814b7564789732c"), "Pending");
        rr2 = new ReimbursementRequest(200,date.toInstant(),new ObjectId("609c222d0814b7564789732c"), new ObjectId("609c222d0814b7564789732c"), "Approved");
        rr3 = new ReimbursementRequest(300,date.toInstant(),new ObjectId("609c222d0814b7564789732c"), new ObjectId("609c222d0814b7564789732c"), "Denied");
    }

    @After
    public void tearDown(){
        List<ReimbursementRequest> rrList = (ArrayList<ReimbursementRequest>)rrs.find(exists("_id"));

        for(ReimbursementRequest rr : rrList){
            rrs.delete(rr.getId());
        }
    }

    @AfterClass
    public static void tearDownClass(){
        System.out.println("Employee Controller Tests complete.");
    }

    @Test
    public void allPendingRRsTest(){
        rrs.save(rr1);
        rrs.save(rr2);
        rrs.save(rr3);
        String path = "/all-pending-rrs";
        String body = "";
        System.out.println("body" + body);
        JSONObject result = rrc.allPendingRRs(body);

        List<String> expected = new ArrayList<>();
        expected.add("{\"amount\":\"100\",\"manager\":\"609c222d0814b7564789732c\",\"_id\":\"" +
        rr1.getId().toString() + "\",\"employee\":\"609c222d0814b7564789732c\",\"timestamp\":\"" + rr1.getTimestamp().toString() + "\",\"status\":\"Pending\"}");
        expected.add("{\"amount\":\"200\",\"manager\":\"609c222d0814b7564789732c\",\"_id\":\"" + rr2.getId().toString() +
                "\",\"employee\":\"609c222d0814b7564789732c\",\"timestamp\":\"" + rr2.getTimestamp().toString() + "\",\"status\":\"Approved\"}");
        expected.add("{\"amount\":\"300\",\"manager\":\"609c222d0814b7564789732c\",\"_id\":\"" +
        rr3.getId().toString() + "\",\"employee\":\"609c222d0814b7564789732c\",\"timestamp\":\"" + rr3.getTimestamp().toString() + "\",\"status\":\"Denied\"}");

        List<String> actual = new ArrayList<>();
        actual.add(result.get(rr1.getId().toString()).toString());
        actual.add(result.get(rr2.getId().toString()).toString());
        actual.add(result.get(rr3.getId().toString()).toString());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allResolvedRRsTest(){
        rrs.save(rr1);
        rrs.save(rr2);
        rrs.save(rr3);
        String path = "/all-resolved-rrs";
        String body = "";
        System.out.println("body" + body);
        JSONObject result = rrc.allResolvedRRs(body);

        List<String> expected = new ArrayList<>();
        expected.add("{\"amount\":\"100\",\"manager\":\"609c222d0814b7564789732c\",\"_id\":\"" +
                rr1.getId().toString() + "\",\"employee\":\"609c222d0814b7564789732c\",\"timestamp\":\"" + rr1.getTimestamp().toString() + "\",\"status\":\"Pending\"}");
        expected.add("{\"amount\":\"200\",\"manager\":\"609c222d0814b7564789732c\",\"_id\":\"" + rr2.getId().toString() +
                "\",\"employee\":\"609c222d0814b7564789732c\",\"timestamp\":\"" + rr2.getTimestamp().toString() + "\",\"status\":\"Approved\"}");
        expected.add("{\"amount\":\"300\",\"manager\":\"609c222d0814b7564789732c\",\"_id\":\"" +
                rr3.getId().toString() + "\",\"employee\":\"609c222d0814b7564789732c\",\"timestamp\":\"" + rr3.getTimestamp().toString() + "\",\"status\":\"Denied\"}");

        List<String> actual = new ArrayList<>();
        actual.add(result.get(rr1.getId().toString()).toString());
        actual.add(result.get(rr2.getId().toString()).toString());
        actual.add(result.get(rr3.getId().toString()).toString());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createRRTest(){
        String path = "/create-rr";
        String body = "{\"amount\": \"a\", \"employee\":\"a\"}";
        System.out.println("body" + body);
        JSONObject obj = rrc.createRR(body);
        System.out.println("obj" + obj);
        String expected = obj.toString();
        String actual = obj.toString();

        System.out.println("actual " + actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateRR(){
        String path = "/update-rr";
        String body = "";
        System.out.println("body" + body);
        JSONObject obj = rrc.updateRR(body);
        System.out.println("obj" + obj);
        String expected = obj.toString();
        String actual = obj.toString();

        System.out.println("actual " + actual);
        Assert.assertEquals(expected, actual);
    }
}


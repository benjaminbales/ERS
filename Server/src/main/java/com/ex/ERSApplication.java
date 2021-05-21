package com.ex;

import com.ex.controllers.*;
import com.ex.services.*;
import com.ex.pojos.*;
import com.ex.daos.*;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.sql.Array;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import io.javalin.Javalin;
import com.mongodb.MongoClientSettings;
//import org.apache.log4j.*;
import org.slf4j.*;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import static com.mongodb.client.model.Filters.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.naming.ldap.Control;


public class ERSApplication extends AbstractApplication{
//    public static Logger logger = LogManager.getLogger(ERSApplication.class.getName());
    public static Logger logger = LoggerFactory.getLogger(ERSApplication.class);

    @Override
    public void run() {
        init();

        Javalin app = (Javalin)context.get("Javalin");

//        List<String> paths = Arrays.asList("/login", "/register-employee", "/view-employee", "/all-employees",
//                "/all-pending-rrs", "/all-resolved-rrs", "/create-rr", "/update-rr", "/update-employee");

        List<Controller> controllers = (ArrayList<Controller>)context.get("Controllers");

        app.post("/*", ctx->{
            JSONObject resp = null;
            for(Controller c: controllers){
                resp = c.doPost(ctx.req.getPathInfo(), ctx.body());
                if(resp != null) break;
            }

            ctx.result(resp.toJSONString());
        });

    }

    /**
     * Creates and initializes various entities relevant to program execution and stores them on the application context map.
     * Some entities, including the Lists of Managers, Employees, and Reimbursement Requests,are initialized from mongodb.
     * Some entities, such as the Manager entity, are assigned a default object, if none are present in the database.
     */
    private void init(){
        context = new HashMap<>();

        //Mongo jdbc config
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
        context.put("MongoConnector", connector);

        //config daos, services, and controllers.
        Dao<Employee, ObjectId> mongoEmployeeDao = new MongoEmployeeDao(connector, "ers", "employees");
        MongoEmployeeService mongoEmployeeService = new MongoEmployeeService(mongoEmployeeDao);
        Map<String, Method> commands = new HashMap<>();

        Controller employeeController = new EmployeeController(mongoEmployeeService);
        context.put("MongoEmployeeDao", mongoEmployeeDao);
        context.put("MongoEmployeeService", mongoEmployeeService);
        context.put("EmployeeController", employeeController);

        List<String> paths = Arrays.asList("/login", "/register-employee", "/view-employee", "/all-employees",
                "/update-employee");


        Dao<Manager, ObjectId> mongoManagerDao = new MongoManagerDao(connector, "ers", "managers");
        MongoManagerService mongoManagerService = new MongoManagerService(mongoManagerDao);
        ManagerController managerController = new ManagerController(mongoManagerService);
        context.put("MongoManagerDao", mongoManagerDao);
        context.put("MongoManagerService", mongoManagerService);
        context.put("ManagerController", managerController);

        Dao<ReimbursementRequest, ObjectId> mongoReimbursementRequestDao =
                new MongoReimbursementRequestDao(connector, "ers", "reimbursementrequests");
        MongoReimbursementRequestService mongoReimbursementRequestService =
                new MongoReimbursementRequestService(mongoReimbursementRequestDao);
        ReimbursementRequestController reimbursementRequestController =
                new ReimbursementRequestController(mongoReimbursementRequestService);
        context.put("MongoReimbursementRequestDao", mongoReimbursementRequestDao);
        context.put("MongoReimbursementRequestService", mongoReimbursementRequestService);
        context.put("ReimbursementRequestController", reimbursementRequestController);

        List<Controller> controllers = new ArrayList<>();
        controllers.add(managerController);
        controllers.add(employeeController);
        controllers.add(reimbursementRequestController);
        context.put("Controllers", controllers);

        //Javalin config
        Javalin app = Javalin.create(config -> {
                    config.enableCorsForAllOrigins();
                }
        ).start(7777);
        context.put("Javalin", app);
    }
}

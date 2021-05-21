package com.ex.daos;

import com.ex.ERSApplication;
import com.ex.MongoConnector;
import com.ex.pojos.Manager;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
//import org.apache.log4j.Level;
import org.bson.conversions.Bson;
import org.slf4j.*;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class MongoManagerDao implements Dao<Manager, ObjectId> {
    private MongoConnector connector;
    private MongoCollection<Manager> collection;
    private String databaseName;
    private String collectionName;

    public MongoManagerDao(MongoConnector connector, String databaseName , String collectionName) {
        this.connector = connector;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.collection = this.connector
                .getClient()
                .getDatabase(databaseName)
                .getCollection(collectionName, Manager.class);
    }

    @Override
    public Manager findOne(ObjectId objectId) {
        Manager manager = null;
        try{
            manager = this.collection.find(eq("_id", objectId)).first();
            ERSApplication.logger.info("Operation Succeeded: create " + objectId + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }catch(Exception ex){
            ex.printStackTrace();
            ERSApplication.logger.debug("Operation Failed: findOne " + objectId + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }
        return manager;
    }

    @Override
    public void create(Manager obj) {
        try{
            this.collection.insertOne(obj);
            ERSApplication.logger.info("Operation Succeeded: create " + obj + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }catch(Exception ex){
            ex.printStackTrace();
            ERSApplication.logger.debug("Operation Failed: create " + obj + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }
    }

    @Override
    public Collection<Manager> findAll(Bson filter) {
        Collection<Manager> managers = null;
        try{
            FindIterable<Manager> results = this.collection.find(filter); // I could return this and have the service convert to a collection
            managers = StreamSupport.stream(results.spliterator(), false).collect(Collectors.toList());
            ERSApplication.logger.info("Operation Succeeded: findAll " + filter + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }catch(Exception ex){
            ex.printStackTrace();
            ERSApplication.logger.debug("Operation Failed: findAll " + filter + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }
        return managers;
    }

    @Override
    public void update(Manager obj, ObjectId objectId) {
        try{
            this.collection.updateOne(eq("_id", objectId), combine(set("firstName", obj.getFirstName()),
                    set("lastName", obj.getLastName()), set("username", obj.getUsername()), set("password", obj.getPassword())));
            ERSApplication.logger.info("Operation Succeeded: update " + obj + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }catch (Exception ex){
            ex.printStackTrace();
            ERSApplication.logger.debug("Operation Failed: update " + obj + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }
    }

    @Override
    public void delete(ObjectId objectId) {
        try{
            this.collection.deleteOne(eq("_id", objectId));
            ERSApplication.logger.info("Operation Succeeded: delete " + objectId + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }catch (Exception ex){
            ex.printStackTrace();
            ERSApplication.logger.debug("Operation Failed: delete " + objectId + " in db: " + this.databaseName +
                    " collection: " + this.collectionName + ".");
        }
    }
}
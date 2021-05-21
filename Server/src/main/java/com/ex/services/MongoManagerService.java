package com.ex.services;

import com.ex.daos.Dao;
import com.ex.pojos.Manager;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Collection;

public class MongoManagerService implements MongoService<Manager> {

    private Dao<Manager, ObjectId> mongoManagerDao;

    public MongoManagerService(Dao<Manager, ObjectId> mongoManagerDao) {
        this.mongoManagerDao = mongoManagerDao;
    }

    @Override
    public ObjectId save(Manager obj) {
        Manager m = findOne(obj.getId());
        if(m != null){
            this.mongoManagerDao.update(obj, obj.getId());
        }else{
            this.mongoManagerDao.create(obj);
        }
        return obj.getId();
    }

    @Override
    public Manager findOne(ObjectId id) {
        return this.mongoManagerDao.findOne(id);
    }

    @Override
    public Collection<Manager> find(Bson filter) {
        return this.mongoManagerDao.findAll(filter);
    }

    @Override
    public void delete(ObjectId id) {
        this.mongoManagerDao.delete(id);
    }
}
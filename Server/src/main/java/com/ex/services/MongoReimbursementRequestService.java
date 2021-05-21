package com.ex.services;

import com.ex.daos.Dao;
import com.ex.pojos.ReimbursementRequest;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Collection;

public class MongoReimbursementRequestService implements MongoService<ReimbursementRequest> {

    private Dao<ReimbursementRequest, ObjectId> mongoReimbursementRequestDao;

    public MongoReimbursementRequestService(Dao<ReimbursementRequest, ObjectId> mongoReimbursementRequestDao) {
        this.mongoReimbursementRequestDao = mongoReimbursementRequestDao;
    }

    @Override
    public ObjectId save(ReimbursementRequest obj) {
        ReimbursementRequest e = findOne(obj.getId());
        if(e != null){
            this.mongoReimbursementRequestDao.update(obj, obj.getId());
        }else{
            this.mongoReimbursementRequestDao.create(obj);
        }
        return obj.getId();
    }

    @Override
    public ReimbursementRequest findOne(ObjectId id) {
        return this.mongoReimbursementRequestDao.findOne(id);
    }

    @Override
    public Collection<ReimbursementRequest> find(Bson filter) {
        return this.mongoReimbursementRequestDao.findAll(filter);
    }

    @Override
    public void delete(ObjectId id) {
        this.mongoReimbursementRequestDao.delete(id);
    }
}
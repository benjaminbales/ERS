package com.ex.services;

import com.ex.daos.Dao;
import com.ex.pojos.Employee;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Collection;

public class MongoEmployeeService implements MongoService<Employee> {

    private Dao<Employee, ObjectId> mongoEmployeeDao;

    public MongoEmployeeService(Dao<Employee, ObjectId> mongoEmployeeDao) {
        this.mongoEmployeeDao = mongoEmployeeDao;
    }

    @Override
    public ObjectId save(Employee obj) {
        Employee e = findOne(obj.getId());
        if(e != null){
            this.mongoEmployeeDao.update(obj, obj.getId());
        }else{
            this.mongoEmployeeDao.create(obj);
        }
        return obj.getId();
    }

    @Override
    public Employee findOne(ObjectId id) {
        return this.mongoEmployeeDao.findOne(id);
    }

    @Override
    public Collection<Employee> find(Bson filter) {
        return this.mongoEmployeeDao.findAll(filter);
    }

    @Override
    public void delete(ObjectId id) {
        this.mongoEmployeeDao.delete(id);
    }
}

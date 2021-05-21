package com.ex.services;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Collection;


public interface MongoService<E> {
    ObjectId save(E obj);
    E findOne(ObjectId id);
    Collection<E> find(Bson filter);
    void delete(ObjectId id);
}


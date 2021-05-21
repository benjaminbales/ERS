package com.ex.daos;

import org.bson.conversions.Bson;

import java.util.Collection;

public interface Dao<E, ID> {
    public void create(E obj);
    E findOne(ID id);
    public void update(E obj, ID id);
    public void delete(ID id);
    Collection<E> findAll(Bson filter);
    // this would contain other general CRUD related methods
}


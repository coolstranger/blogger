package com.abc.xyz.dao;


import com.abc.xyz.schema.BaseEntity;

public interface BaseDAO {

    void persistEntity(BaseEntity entity);

    <T> T getEntity(Class<T> type, String id);

    void deleteEntity(Class type, String id);

    void deleteEntity(BaseEntity entity);

}

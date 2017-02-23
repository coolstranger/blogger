package com.abc.xyz.dao.impl;


import com.abc.xyz.dao.BaseDAO;
import com.abc.xyz.schema.BaseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BaseDAOImpl implements BaseDAO{


    @PersistenceContext
    protected EntityManager em;

    public void persistEntity(BaseEntity entity){
        em.persist(entity);
    }

    public <T> T getEntity(Class<T> type, String id){
        return em.find(type, id);
    }

    public void deleteEntity(Class type, String id){
        em.remove(getEntity(type, id));
    }

    public void deleteEntity(BaseEntity entity){
        em.remove(entity);
    }

}

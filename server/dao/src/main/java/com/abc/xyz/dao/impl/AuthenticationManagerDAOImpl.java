package com.abc.xyz.dao.impl;

import com.abc.xyz.dao.AuthenticationManagerDAO;
import com.abc.xyz.schema.SessionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AuthenticationManagerDAOImpl extends BaseDAOImpl implements AuthenticationManagerDAO {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public SessionEntity getUserSession(String userId) {
        Query q = em.createQuery("from SessionEntity where refUser=:userId");
        q.setParameter("userId", userId);
        List<SessionEntity> res = q.getResultList();
        if (res.size() > 0) {
            return res.get(0);
        } else {
            return null;
        }
    }

    @Override
    public SessionEntity getUserSessionfromSessionId(String sessionId) {
        Query q = em.createQuery("from SessionEntity where sessionId=:sessionId");
        q.setParameter("sessionId", sessionId);
        List<SessionEntity> res = q.getResultList();
        if (res.size() > 0) {
            return res.get(0);
        } else {
            return null;
        }
    }

}

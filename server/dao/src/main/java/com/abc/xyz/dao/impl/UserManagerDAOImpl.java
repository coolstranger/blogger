package com.abc.xyz.dao.impl;

import com.abc.xyz.dao.UserManagerDAO;
import com.abc.xyz.schema.CredentialEntity;
import com.abc.xyz.schema.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserManagerDAOImpl extends BaseDAOImpl implements UserManagerDAO{

    @PersistenceContext
    protected EntityManager em;

    public UserEntity getUserFromLoginUid(String loginUid){
        Query q = em.createQuery("from UserEntity where loginUid=:loginUid");
        q.setParameter("loginUid", loginUid);
        List<UserEntity> res = q.getResultList();
        if(res.size()>0){
            return res.get(0);
        }
        return null;

    }

    public CredentialEntity getCredential(String userId){
        Query q = em.createQuery("from CredentialEntity where refUser=:userId");
        q.setParameter("userId", userId);
        List<CredentialEntity> res = q.getResultList();
        if(res.size()>0){
            return res.get(0);
        }
        return null;
    }


}

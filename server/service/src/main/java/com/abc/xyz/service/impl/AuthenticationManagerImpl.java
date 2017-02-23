package com.abc.xyz.service.impl;

import com.abc.xyz.common.BloggerException;
import com.abc.xyz.common.Constants;
import com.abc.xyz.common.DataHelper;
import com.abc.xyz.common.ErrorCodes;
import com.abc.xyz.common.data.User;
import com.abc.xyz.dao.AuthenticationManagerDAO;
import com.abc.xyz.dao.BaseDAO;
import com.abc.xyz.schema.SessionEntity;
import com.abc.xyz.schema.UserEntity;
import com.abc.xyz.service.AuthenticationManager;
import com.abc.xyz.service.ConfigManager;
import com.abc.xyz.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Component
@Transactional(value= Constants.TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    UserManager userManager;

    @Autowired
    AuthenticationManagerDAO dao;

    @Autowired
    ConfigManager configManager;

    @Override
    public String authenticate(String loginUid, String password) {
        User user = userManager.getUserFromLoginUid(loginUid);
        if(user==null){
            throw new BloggerException(ErrorCodes.AUTHENTICATION_FAILED);
        }

        boolean verify = userManager.verifyCredential(user.getId(), password);
        if(verify){

        }else{
            throw new BloggerException(ErrorCodes.AUTHENTICATION_FAILED);
        }

        String ret = createSession(user.getId());
        updateLoginTime(user.getId());
        return ret;
    }

    @Override
    public void logout(String sessionId){
        SessionEntity se = dao.getUserSessionfromSessionId(sessionId);
        if(se!=null){
            dao.deleteEntity(se);
        }
    }

    @Override
    public String createSession(String userId){
        SessionEntity se = dao.getUserSession(userId);
        if(se!=null){
            dao.deleteEntity(se);
        }

        SessionEntity nse = new SessionEntity();
        nse.setSessionId(UUID.randomUUID().toString());
        nse.setExpiryTime(System.currentTimeMillis() + configManager.getExpiryTime());
        nse.setUser(dao.getEntity(UserEntity.class, userId));
        dao.persistEntity(nse);
        return nse.getSessionId();
    }


    @Override
    public boolean verifySession(String sessionId){
        SessionEntity se = dao.getUserSessionfromSessionId(sessionId);
        if(se==null){
            return false;
        }

        if(se.getExpiryTime()>System.currentTimeMillis()){
            return true;
        }

        dao.deleteEntity(se);
        return false;
    }

    @Override
    public User getSessionUser(String sessionId){
        SessionEntity se = dao.getUserSessionfromSessionId(sessionId);
        if(se==null){
            return null;
        }

        if(se.getExpiryTime()>System.currentTimeMillis()){
            return DataHelper.getUser(se.getUser());
        }
        dao.deleteEntity(se);
        return null;

    }

    private void updateLoginTime(String userId){
        UserEntity ue = dao.getEntity(UserEntity.class, userId);
        ue.setLastLoginTime(System.currentTimeMillis());
        dao.persistEntity(ue);
    }
}

package com.abc.xyz.dao;


import com.abc.xyz.schema.SessionEntity;

public interface AuthenticationManagerDAO extends  BaseDAO{

    SessionEntity getUserSession(String userId);
    SessionEntity getUserSessionfromSessionId(String sessionId);
}

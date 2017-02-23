package com.abc.xyz.service;


import com.abc.xyz.common.data.User;

public interface AuthenticationManager {

    String authenticate(String loginUid, String password);
    String createSession(String userId);
    boolean verifySession(String sessionId);
    User getSessionUser(String sessionId);
}

package com.abc.xyz.service;


import com.abc.xyz.common.data.User;

/**
 * This Service is used to perform Authentication for user and verify session of an existing user.
 *
 */
public interface AuthenticationManager {

    String authenticate(String loginUid, String password);
    String createSession(String userId);
    boolean verifySession(String sessionId);
    User getSessionUser(String sessionId);
    void logout(String sessionId);
}

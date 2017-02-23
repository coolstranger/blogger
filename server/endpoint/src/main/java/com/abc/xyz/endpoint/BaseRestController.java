package com.abc.xyz.endpoint;


import com.abc.xyz.common.BloggerException;
import com.abc.xyz.common.Constants;
import com.abc.xyz.common.data.User;
import com.abc.xyz.security.CryptoManager;
import com.abc.xyz.service.AuthenticationManager;
import com.abc.xyz.service.BlogManager;
import com.abc.xyz.service.ConfigManager;
import com.abc.xyz.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseRestController extends PayloadClasses{


    @Autowired
    protected UserManager userManager;

    @Autowired
    protected ConfigManager configManager;

    @Autowired
    protected CryptoManager cryptoManager;

    @Autowired
    protected BlogManager blogManager;

    @Autowired
    protected AuthenticationManager authenticationManager;




    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity handleException(HttpServletRequest request, HttpServletResponse response, Throwable t){
        ExceptionPayload payload = new ExceptionPayload(t);
        return errorWithMessage(payload);
    }

    protected String getAuthToken(HttpServletRequest request){
        for(Cookie cookie : request.getCookies()){
            if(Constants.AUTH_TOKEN.equalsIgnoreCase(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    protected User getLoginUser(HttpServletRequest request){
        String sessionId = getAuthToken(request);
        User u = authenticationManager.getSessionUser(sessionId);
        return u;
    }

    protected ResponseEntity ok() {
        return new ResponseEntity(HttpStatus.OK);
    }

    protected ResponseEntity okWithPayload(Object payload) {
        return new ResponseEntity(payload, HttpStatus.OK);
    }

    protected ResponseEntity errorWithMessage(Object payload){
        return new ResponseEntity(payload, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    protected ResponseEntity notFound(Object payload) {
        return new ResponseEntity(payload, HttpStatus.NOT_FOUND);
    }

}

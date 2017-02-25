package com.abc.xyz.endpoint;

import com.abc.xyz.common.BloggerException;
import com.abc.xyz.common.ErrorCodes;
import com.abc.xyz.common.PayloadConstants;
import com.abc.xyz.common.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class AuthenticationController extends BaseRestController{

    /**
     *
     * @param payload {"j_username":"aamir","j_password":"password"}
     * @param request
     * @return session Id in response body
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestBody HashMap<String,String> payload, HttpServletRequest request){
        String sessionId = authenticationManager.authenticate(payload.get(PayloadConstants.J_USERNAME), payload.get(PayloadConstants.J_PASSWORD));
        return okWithPayload(sessionId);
    }

    /**
     *
     * @param request containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return JSON for User object in session
     */
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSession(HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        return okWithPayload(u);
    }

    /**
     *
     * @param request containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity logout(HttpServletRequest request){
        String token = getAuthToken(request);
        authenticationManager.logout(token);
        return ok();
    }

}

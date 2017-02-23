package com.abc.xyz.endpoint;

import com.abc.xyz.common.PayloadConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class AuthenticationController extends BaseRestController{

    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(HashMap<String,String> payload, HttpServletRequest request){
        String sessionId = authenticationManager.authenticate(payload.get(PayloadConstants.J_USERNAME), payload.get(PayloadConstants.J_PASSWORD));
        return okWithPayload(sessionId);
    }


}

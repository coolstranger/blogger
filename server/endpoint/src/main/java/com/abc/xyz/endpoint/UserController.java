package com.abc.xyz.endpoint;

import com.abc.xyz.common.BloggerException;
import com.abc.xyz.common.ErrorCodes;
import com.abc.xyz.common.PayloadConstants;
import com.abc.xyz.common.PayloadHelper;
import com.abc.xyz.common.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class UserController extends  BaseRestController{

    @RequestMapping(value="/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createUser(@RequestBody  HashMap<String, String> payload, HttpServletRequest request){
        User user = PayloadHelper.getUser(payload);
        String password = payload.get(PayloadConstants.U_PASSWORD);
        String confirm_password = payload.get(PayloadConstants.U_CONFIRM_PASSWORD);

        if(password==null || confirm_password==null){
            throw new BloggerException(ErrorCodes.PASSWORD_EMPTY);
        }

        if(!password.equals(confirm_password)){
            throw new BloggerException(ErrorCodes.PASSWORD_MISMATCH);
        }

        userManager.createUser(user, password);
        return ok();
    }

    @RequestMapping(value="/user", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateUser(@RequestBody HashMap<String, String> payload, HttpServletRequest request){
        User user = PayloadHelper.getUser(payload);
        userManager.updateUser(user);
        return ok();
    }


    @RequestMapping(value="/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getUser(HttpServletRequest request){
        User u = null;
        String id = request.getParameter(PayloadConstants.U_ID);
        if(id==null){
            String loginUid = request.getParameter(PayloadConstants.U_LOGIN);
            u = userManager.getUserFromLoginUid(loginUid);
        }else{
            u = userManager.getUser(id);
        }
        if(u!=null)
            return okWithPayload(u);
        else
            return notFound("User Not Found");
    }

    @RequestMapping(value = "/credentials", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity changePassword(@RequestBody HashMap<String, String> payload, HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }

        if(!payload.get(PayloadConstants.P_NEW).equals(payload.get(PayloadConstants.U_CONFIRM_PASSWORD))){
            throw new BloggerException(ErrorCodes.PASSWORD_MISMATCH);
        }

        userManager.changePassword(payload.get(PayloadConstants.P_OLD), payload.get(PayloadConstants.P_NEW), u.getId());
        return ok();
    }

}

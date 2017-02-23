package com.abc.xyz.endpoint;

import com.abc.xyz.common.BloggerException;
import com.abc.xyz.common.ErrorCodes;
import com.abc.xyz.common.PayloadConstants;
import com.abc.xyz.common.PayloadHelper;
import com.abc.xyz.common.data.Blog;
import com.abc.xyz.common.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
public class BlogController extends BaseRestController {


    @RequestMapping(value="/blog", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createBlog(HashMap<String, String> payload , HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        Blog b = PayloadHelper.getBlog(payload);
        blogManager.createBlog(b);
        return ok();
    }


    @RequestMapping(value="/blog", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateBlog(HashMap<String, String> payload , HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        Blog b = PayloadHelper.getBlog(payload);
        blogManager.updateBlog(b);
        return ok();
    }

    @RequestMapping(value="/blog", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getBlog(HttpServletRequest request){
        String id = request.getParameter(PayloadConstants.B_ID);
        Blog b  = blogManager.getBlog(id);
        return okWithPayload(b);
    }

    @RequestMapping(value="/blog", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity delete(HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        String id = request.getParameter(PayloadConstants.B_ID);
        blogManager.deleteBlog(id, u.getId());
        return ok();
    }

    @RequestMapping(value="/blogs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getBlogs(HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        List<Blog> res = blogManager.getBlogs(u.getId(), request.getParameter(PayloadConstants.B_LAST_ENTITY), Boolean.parseBoolean(request.getParameter(PayloadConstants.B_PREV)));
        return okWithPayload(res);
    }


    @RequestMapping(value="/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity searchBlog(HttpServletRequest request){
        User u = getLoginUser(request);
        String id = null;
        if(u!=null){
            id = u.getId();
        }
        List<Blog> res = blogManager.searchBlog(id, request.getParameter(PayloadConstants.B_KEYWORD), request.getParameter(PayloadConstants.B_LAST_ENTITY), Boolean.parseBoolean(request.getParameter(PayloadConstants.B_PREV)));
        return okWithPayload(res);
    }



}

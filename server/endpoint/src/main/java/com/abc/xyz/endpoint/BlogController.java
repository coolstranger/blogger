package com.abc.xyz.endpoint;

import com.abc.xyz.common.*;
import com.abc.xyz.common.data.Blog;
import com.abc.xyz.common.data.Comment;
import com.abc.xyz.common.data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
public class BlogController extends BaseRestController {


    @RequestMapping(value="/blog", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createBlog(@RequestBody HashMap<String, String> payload , HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        Blog b = PayloadHelper.getBlog(payload);
        b.setUserId(u.getId());
        String id = blogManager.createBlog(b);
        return okWithPayload(id);
    }


    @RequestMapping(value="/blog", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateBlog(@RequestBody HashMap<String, String> payload , HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        Blog b = PayloadHelper.getBlog(payload);
        b.setUserId(u.getId());
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
    public ResponseEntity delete(@RequestBody List<String> blogs,  HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        for(String id : blogs) {
            blogManager.deleteBlog(id, u.getId());
        }
        return ok();
    }

    @RequestMapping(value="/blogs", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getBlogs(HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        List<Blog> res = blogManager.getBlogs(u.getId(), request.getParameter(PayloadConstants.B_LAST_ENTITY), Boolean.parseBoolean(request.getParameter(PayloadConstants.B_PREV)), Constants.BLOG_STATE_PUBLISHED);
        return okWithPayload(res);
    }

    @RequestMapping(value="/drafts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getDrafts(HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        List<Blog> res = blogManager.getBlogs(u.getId(), request.getParameter(PayloadConstants.B_LAST_ENTITY), Boolean.parseBoolean(request.getParameter(PayloadConstants.B_PREV)), Constants.BLOG_STATE_DRAFT);
        return okWithPayload(res);
    }

    @RequestMapping(value = "/publish", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity publishBlog(@RequestBody List<String> blogs, HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }
        for(String id : blogs) {
            blogManager.publishBlog(id, u.getId());
        }
        return ok();
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

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addComment(@RequestBody HashMap<String,String> payload, HttpServletRequest request){
        User u = getLoginUser(request);
        if(u==null){
            throw new BloggerException(ErrorCodes.NO_WEB_SESSION_PRESENT);
        }

        blogManager.publishComment(payload.get(PayloadConstants.B_COMMENT), payload.get(PayloadConstants.B_ID), u.getId());
        return ok();
    }

    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getComment(HttpServletRequest request){
        List<Comment> res = blogManager.getComments(request.getParameter(PayloadConstants.B_ID));
        return okWithPayload(res);
    }

}

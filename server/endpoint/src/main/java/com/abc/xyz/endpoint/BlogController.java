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

/**
 * This is the Rest Endpoint class for Blog operations
 */

@RestController
public class BlogController extends BaseRestController {

    /**
     * This method is to create a new blog
     * @param payload  {"name":"1234","desc":"1234","body":"1234"}
     * @param request  containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return blog id is the response payload
     */
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


    /**
     *
     * @param payload {"name":"1234","desc":"1234","body":"1234", "id":"1234"}
     * @param request containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return
     */
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

    /**
     *
     * @param request containing bolg id in query parameter. ?id=BLOG_D
     * @return JSON for Blog Object in response body
     */
    @RequestMapping(value="/blog", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getBlog(HttpServletRequest request){
        String id = request.getParameter(PayloadConstants.B_ID);
        Blog b  = blogManager.getBlog(id);
        return okWithPayload(b);
    }

    /**
     *
     * @param blogs list of blog id's to be deleted ["1234", "5678"]
     * @param request containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return
     */
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

    /**
     *
     * @param request containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return list of blogs as JSON object belonging to the user in session
     */
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

    /**
     *
     * @param request containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return list of draft as JSON object belonging to the user in session
     */
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

    /**
     *
     * @param blogs list of blog id's to be published ["1234", "5678"]
     * @param request containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return
     */
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

    /**
     *
     * @param request Query parameters ?keyword=123&last_entity=567&prev=false
     * @return A JSON list og blog Objects
     */
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

    /**
     *
     * @param payload {"comment":"1234","id":"1234"}
     * @param request containing cookie BLOGGER_AUTH_TOKEN=&lt;SESSIONID&gt;
     * @return
     */
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

    /**
     *
     * @param request blog id as query parameter ?id=1234
     * @return list of JSON objects of Comments
     */
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getComment(HttpServletRequest request){
        List<Comment> res = blogManager.getComments(request.getParameter(PayloadConstants.B_ID));
        return okWithPayload(res);
    }

}

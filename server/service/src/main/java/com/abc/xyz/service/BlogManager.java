package com.abc.xyz.service;

import com.abc.xyz.common.data.Blog;
import com.abc.xyz.common.data.Comment;

import java.util.List;

public interface BlogManager {

    String createBlog(Blog blog);
    void updateBlog(Blog blog);
    void deleteBlog(String blogId, String userId);
    List<Blog> searchBlog(String userId, String keyWord, String lastEntity, boolean prev);
    List<Blog> getBlogs(String userId, String lastEntity, boolean prev, int state);
    Blog getBlog(String blogId);
    void publishComment(String comment, String blogId, String userId);
    List<Comment> getComments(String blogId);
    void publishBlog(String blogId, String userId);
}

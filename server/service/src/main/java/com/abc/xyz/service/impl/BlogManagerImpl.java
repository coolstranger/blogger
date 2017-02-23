package com.abc.xyz.service.impl;

import com.abc.xyz.common.*;
import com.abc.xyz.common.data.Blog;
import com.abc.xyz.dao.BaseDAO;
import com.abc.xyz.dao.impl.BlogManagerDAOImpl;
import com.abc.xyz.schema.BlogEntity;
import com.abc.xyz.schema.UserEntity;
import com.abc.xyz.service.BlogManager;
import com.abc.xyz.service.SearchManager;
import com.abc.xyz.service.SettingsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(value= Constants.TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
public class BlogManagerImpl implements BlogManager{

    @Autowired
    private BlogManagerDAOImpl dao;

    @Autowired
    private SettingsManager settingsManager;

    @Autowired
    private SearchManager searchManager;

    @Override
    public String createBlog(Blog blog) {
        if(blog.getName()==null || blog.getName().trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_NAME_EMPTY);
        }
        if(blog.getDesc()==null || blog.getDesc().trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_NAME_EMPTY);
        }
        if(blog.getBody()==null || blog.getBody().trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_NAME_EMPTY);
        }

        BlogEntity be = EntityHelper.getBlogEntity(null, blog);
        be.setCreationDate(System.currentTimeMillis());
        be.setLastUpdate(System.currentTimeMillis());
        be.setRevision(1);

        UserEntity ue = dao.getEntity(UserEntity.class, blog.getUserId());
        if(ue==null){
            throw new BloggerException(ErrorCodes.BLOG_USER_NOT_FOUND);
        }

        be.setUser(ue);
        dao.persistEntity(be);
        return be.getId();

    }

    @Override
    public void updateBlog(Blog blog) {
        if(blog.getName()==null || blog.getName().trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_NAME_EMPTY);
        }
        if(blog.getDesc()==null || blog.getDesc().trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_NAME_EMPTY);
        }
        if(blog.getBody()==null || blog.getBody().trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_NAME_EMPTY);
        }

        if(blog.getId()==null || blog.getId().trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_ID_EMPTY);
        }

        BlogEntity be = dao.getEntity(BlogEntity.class, blog.getId());
        if(be==null){
            throw new BloggerException(ErrorCodes.BLOG_NOT_FOUND);
        }

        if(!be.getRefUser().equals(blog.getUserId())){
            throw new BloggerException(ErrorCodes.BLOG_ANOTHER_USER);
        }

        be = EntityHelper.getBlogEntity(be, blog);
        be.setLastUpdate(System.currentTimeMillis());
        be.setRevision(be.getRevision() + 1);
        dao.persistEntity(be);
    }

    @Override
    public void deleteBlog(String blogId, String userId) {
        if(blogId==null || blogId.trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_ID_EMPTY);
        }

        BlogEntity be = dao.getEntity(BlogEntity.class, blogId);
        if(be==null){
            throw new BloggerException(ErrorCodes.BLOG_NOT_FOUND);
        }

        if(!be.getRefUser().equals(userId)){
            throw new BloggerException(ErrorCodes.BLOG_ANOTHER_USER);
        }
        dao.deleteEntity(be);
    }


    @Override
    public List<Blog> searchBlog(String userId, String keyWord, String lastEntity, boolean prev) {
        return searchManager.searchBlog(userId, keyWord, lastEntity, prev);
    }

    @Override
    public List<Blog> getBlogs(String userId, String lastEntity, boolean prev) {
        int batchSize = settingsManager.getBatchSize(userId);
        List<BlogEntity> res = dao.getBlogs(userId, lastEntity, batchSize, prev);
        List<Blog> ret = new ArrayList<>();
        for(BlogEntity be : res){
            ret.add(DataHelper.getBlog(be));
        }
        return ret;
    }

    @Override
    public Blog getBlog(String blogId) {
        if(blogId==null || blogId.trim().equals("")){
            throw new BloggerException(ErrorCodes.BLOG_ID_EMPTY);
        }

        BlogEntity be = dao.getEntity(BlogEntity.class, blogId);
        if(be==null){
            throw new BloggerException(ErrorCodes.BLOG_NOT_FOUND);
        }

        return DataHelper.getBlog(be);
    }
}

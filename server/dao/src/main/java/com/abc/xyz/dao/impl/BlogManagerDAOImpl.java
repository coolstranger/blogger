package com.abc.xyz.dao.impl;


import com.abc.xyz.dao.BlogManagerDAO;
import com.abc.xyz.schema.BlogEntity;
import com.abc.xyz.schema.CommentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogManagerDAOImpl extends BaseDAOImpl implements BlogManagerDAO{

    @PersistenceContext
    protected EntityManager em;

    @Override
    public List<BlogEntity> getBlogs(String userId, String lastEntity, int batchSize, boolean prev, int state){
        String sql = "from BlogEntity where refUser=:userId and state=:state ";
        if(lastEntity!=null){
            if(prev){
                sql += " and id<:lastEntity ";
            }else {
                sql += " and id>:lastEntity ";
            }
        }
        sql+= "order by id ";
        if(prev){
            sql+=" desc";
        }
        Query q = em.createQuery(sql);
        q.setParameter("userId", userId);
        q.setParameter("state", state);
        if(lastEntity!=null){
            q.setParameter("lastEntity", lastEntity);
        }
        q.setMaxResults(batchSize);

        List<BlogEntity> res = q.getResultList();
        if(prev) {
            List<BlogEntity> ret = new ArrayList<>();
            for(int i=res.size()-1; i>=0; i--){
                ret.add(res.get(i));
            }
            return ret;
        }
        return res;
    }

    @Override
    public List<CommentEntity> getComments(String blogId){
        Query q = em.createQuery("from CommentEntity where refBlog=:blogId order by commentTime");
        q.setParameter("blogId", blogId);
        List<CommentEntity> res = q.getResultList();
        return res;
    }

}

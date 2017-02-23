package com.abc.xyz.dao.impl;

import com.abc.xyz.dao.BaseDAO;
import com.abc.xyz.dao.SearchManagerDAO;
import com.abc.xyz.schema.BlogEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Repository
public class SearchManagerDAOImpl extends BaseDAOImpl implements SearchManagerDAO {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public List<BlogEntity> searchBlog(String keyword, String lastEntity, int batchSize, boolean prev){
        String sql = "from BlogEntity where (name like '%:name%' or description like '%:desc%' or body like '%:body%') ";
        if(lastEntity!=null){
            if(prev){
                sql += " and id<:lastEntity ";
            }else {
                sql += " and id>:lastEntity ";
            }
        }
        sql+= " order by id ";
        if(prev){
            sql+=" desc";
        }
        Query q = em.createQuery(sql);
        q.setParameter("name", keyword);
        q.setParameter("desc", keyword);
        q.setParameter("body", keyword);
        if(lastEntity!=null){
            q.setParameter("lastEntity", lastEntity);
        }
        q.setMaxResults(batchSize+1);

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


}

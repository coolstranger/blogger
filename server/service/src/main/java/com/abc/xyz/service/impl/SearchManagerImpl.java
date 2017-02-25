package com.abc.xyz.service.impl;


import com.abc.xyz.common.Constants;
import com.abc.xyz.common.DataHelper;
import com.abc.xyz.common.data.Blog;
import com.abc.xyz.dao.impl.SearchManagerDAOImpl;
import com.abc.xyz.schema.BlogEntity;
import com.abc.xyz.service.SearchManager;
import com.abc.xyz.service.SettingsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This is basic implementation for blog searching based on sql like %...% operation.
 * Another implementation can be used based on Elastic Search or Lucene
 */
@Component
@Transactional(value= Constants.TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
public class SearchManagerImpl implements SearchManager{

    @Autowired
    private SearchManagerDAOImpl dao;

    @Autowired
    private SettingsManager settingsManager;

    @Override
    public List<Blog> searchBlog(String userId, String keyWord, String lastEntity, boolean prev) {
        int batch = settingsManager.getGlobalSearchSize();

        if(userId!=null){
           batch = settingsManager.getSearchSize(userId);
        }

        List<BlogEntity> res = dao.searchBlog(keyWord, lastEntity, batch, prev);
        List<Blog> ret = new ArrayList<>();
        for(BlogEntity be : res){
            ret.add(DataHelper.getBlog(be));
        }
        return ret;
    }
}

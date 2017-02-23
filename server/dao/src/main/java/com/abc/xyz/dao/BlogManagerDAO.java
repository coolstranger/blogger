package com.abc.xyz.dao;


import com.abc.xyz.schema.BlogEntity;

import java.util.List;

public interface BlogManagerDAO extends BaseDAO{

    List<BlogEntity> getBlogs(String userId, String lastEntity, int batchSize, boolean prev);

}

package com.abc.xyz.dao;

import com.abc.xyz.schema.BlogEntity;

import java.util.List;

public interface SearchManagerDAO extends BaseDAO{

    List<BlogEntity> searchBlog(String keyword, String lastEntity, int batchSize, boolean prev);

}

package com.abc.xyz.service;


import com.abc.xyz.common.data.Blog;

import java.util.List;

/**
 * This service is used to search blogs and present relevant blog with the search keywords provided.
 */
public interface SearchManager {

    List<Blog> searchBlog(String userId, String keyWord, String lastEntity, boolean prev);
}

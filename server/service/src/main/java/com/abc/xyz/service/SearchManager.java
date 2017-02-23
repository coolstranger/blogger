package com.abc.xyz.service;


import com.abc.xyz.common.data.Blog;

import java.util.List;

public interface SearchManager {

    List<Blog> searchBlog(String userId, String keyWord, String lastEntity, boolean prev);
}

package com.beauty.admin.service;

import java.util.List;

import com.beauty.admin.response.JsTreeNode;
import com.beauty.response.CommonResponse;
import com.beauty.response.result.CategoryResult;

public interface AdminCategoryService {

	public List<CategoryResult> getCategoryList();
	
	public List<JsTreeNode> getCategory(String parentId, int level);
	public List<Object[]> getCategoryLv3();
	
	public CommonResponse add(String categoryName, Long categoryId);
	public CommonResponse delete(Long categoryId, int visible);
	public CommonResponse update(Long categoryId, String categoryName);

}

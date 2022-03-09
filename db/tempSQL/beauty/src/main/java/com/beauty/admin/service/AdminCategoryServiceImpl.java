package com.beauty.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beauty.admin.response.JsTreeNode;
import com.beauty.entity.Category;
import com.beauty.repository.CategoryRepository;
import com.beauty.response.CommonResponse;
import com.beauty.response.result.CategoryResult;
import com.beauty.response.result.ResultCode;

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryResult> getCategoryList() {
		List<Object[]> categoryList = categoryRepository.findByProductCategory2();
		List<CategoryResult> resultList = new ArrayList<>();
		CategoryResult categoryResult;
		CategoryResult childResult;
		List<CategoryResult> childList = null;
		// c2.menu_id lv2_id, c2.menu_name lv2, c3.menu_id lv3_id, c3.menu_name lv3, c3.menu_parent lv3_p
		for(Object[] obj:categoryList) {
			if(resultList.size() > 0) {
				List<CategoryResult> result = resultList.stream()
						.filter(item -> item.getCtg_id().equals(Long.parseLong(obj[0].toString())))
						.collect(Collectors.toList());
				if(result.size() > 0) {
					CategoryResult c = result.get(0);
					if(obj[2] != null) {
						childList = c.getChidList();
						childResult = new CategoryResult();
						childResult.setCtg_id(Long.parseLong(obj[2].toString()));
						childResult.setCtg(obj[3].toString());	
						childResult.setCtg_p(Long.parseLong(obj[4].toString()));
						childList.add(childResult);
					}
				} else {
					categoryResult = new CategoryResult();
					categoryResult.setCtg_id(Long.parseLong(obj[0].toString()));
					categoryResult.setCtg(obj[1].toString());
					if(obj[2] != null) {
						childList = new ArrayList<>();
						childResult = new CategoryResult();
						childResult.setCtg_id(Long.parseLong(obj[2].toString()));
						childResult.setCtg(obj[3].toString());	
						childResult.setCtg_p(Long.parseLong(obj[4].toString()));
						childList.add(childResult);
						categoryResult.setChidList(childList);
					}
					resultList.add(categoryResult);
				}
			} else {
				categoryResult = new CategoryResult();
				categoryResult.setCtg_id(Long.parseLong(obj[0].toString()));
				categoryResult.setCtg(obj[1].toString());
				if(obj[2] != null) {
					childList = new ArrayList<>();
					childResult = new CategoryResult();
					childResult.setCtg_id(Long.parseLong(obj[2].toString()));
					childResult.setCtg(obj[3].toString());	
					childResult.setCtg_p(Long.parseLong(obj[4].toString()));
					childList.add(childResult);
					categoryResult.setChidList(childList);
				}
				resultList.add(categoryResult);
			}
		}


		return resultList;
	}

	@Override
	public List<JsTreeNode> getCategory(String parentId, int level) {
		Long id = 0L;

		if(parentId.equals("#")) {
			id = -1L;
		} else {
			id = Long.parseLong(parentId);
		}

		List<JsTreeNode> jstree = new ArrayList<>();
		JsTreeNode treenode = null;
//		if(level == 3) {		// 상품 List
//			Category category = categoryRepository.findOne(id);
//			List<ProductCategory> categoryList = productCategoryRepository.findByCategory(category);
//			for(ProductCategory productCategory : categoryList) {
//				treenode = new JsTreeNode();
//				treenode.setId("p_" + productCategory.getProduct().getProductId());
//				treenode.setText(productCategory.getProduct().getProdName());
//				treenode.setType("file");
//				treenode.setChildren(true);
//				jstree.add(treenode);
//			}
//		} else {
			List<Category> categoryList = categoryRepository.findByMenuParent(id);
			for(Category category : categoryList) {
				treenode = new JsTreeNode();
				treenode.setId(""+category.getMenuId());
				treenode.setText(category.getMenuName());
				if(categoryRepository.findByMenuParent(category.getMenuId()).size() > 0 || level == 2) {
					treenode.setChildren(true);
				}

				if(category.getMenuVisible() == 0) {
					treenode.setType("folder-close");
				} else {
					treenode.setType("folder-close-visible");	
				}
				jstree.add(treenode);
			}

//		}
		return jstree;
	}


	@Override
	public CommonResponse add(String categoryName, Long categoryId) {
		Category category = new Category();
		category.setMenuName(categoryName);
		category.setMenuParent(categoryId);
		category.setMenuSort(1);
		category.setMenuVisible(1);
		categoryRepository.save(category);
		return CommonResponse.of("등록 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse delete(Long categoryId, int visible) {
		Category category = categoryRepository.findOne(categoryId);
		category.setMenuVisible(visible);

		categoryRepository.save(category);
		return CommonResponse.of("상태가 변경 되었습니다.", ResultCode.SUCCESS);
	}

	@Override
	public CommonResponse update(Long categoryId, String categoryName) {
		Category category = categoryRepository.findOne(categoryId);
		category.setMenuName(categoryName);

		categoryRepository.save(category);
		return CommonResponse.of("수정 되었습니다.", ResultCode.SUCCESS);
	}


	@Override
	public List<Object[]> getCategoryLv3() {
		return categoryRepository.findByCategoryLv3();
	}


}

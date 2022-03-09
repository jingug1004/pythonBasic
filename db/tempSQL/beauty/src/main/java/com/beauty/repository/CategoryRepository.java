package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Category;


@Repository
@Qualifier(value = "categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query(value = "select c1.menu_id lv1_id, c1.menu_name lv1, c2.menu_id lv2_id, c2.menu_name lv2, c3.menu_id lv3_id, c3.menu_name lv3 from app_category c1 left join app_category c2 on c2.menu_parent=c1.menu_id AND c2.menu_visible=1 left join app_category c3 on c3.menu_parent=c2.menu_id AND c3.menu_visible=1 where c1.menu_id=1 and c1.menu_visible=1", nativeQuery = true)
	public List<Object[]> findByProductCategory();

	@Query(value = "select c2.menu_id lv2_id, c2.menu_name lv2, c3.menu_id lv3_id, c3.menu_name lv3, c3.menu_parent lv3_p from app_category c1 left join app_category c2 on c2.menu_parent=c1.menu_id and c2.menu_visible =1 left join app_category c3 on c3.menu_parent=c2.menu_id and c3.menu_visible = 1 where c1.menu_id=1 and c1.menu_visible=1", nativeQuery = true)
	public List<Object[]> findByProductCategory2();
	
	@Query(value = "select c3.menu_id lv3_id, c3.menu_name lv3, c3.menu_parent lv3_p, c4.menu_id lv4_id, c4.menu_name lv4, c4.menu_parent lv4_p from app_category c1 left join app_category c2 on c2.menu_parent=c1.menu_id and c2.menu_visible=1 left join app_category c3 on c3.menu_parent=c2.menu_id and c3.menu_visible=1 left join app_category c4 on c4.menu_parent=c3.menu_id and c4.menu_visible=1 where c1.menu_parent=-1 and c1.menu_visible=1 and c2.menu_id IS NOT NULL", nativeQuery = true)
	public List<Object[]> findByCategoryLv3();
	
	
	public List<Category> findByMenuParent(Long menu_parent);
	public List<Category> findByMenuParentAndMenuId(Long menu_parent, Long menuId);
	
	public List<Category> findByMenuParentIn(List<Long> parents);
}



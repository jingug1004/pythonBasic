package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.MainMenu;


@Repository
@Qualifier(value = "mainMenuRepository")
public interface MainMenuRepository extends JpaRepository<MainMenu, Long> {
	
	public List<MainMenu> findByShowYn(String show);
	public MainMenu findFirstByOrderBySortDesc();
	
	public List<MainMenu> findBySortLessThanEqualAndSortGreaterThan(int neworder, int oldorder);
	public List<MainMenu> findBySortLessThanAndSortGreaterThanEqual(int oldorder, int neworder);
}

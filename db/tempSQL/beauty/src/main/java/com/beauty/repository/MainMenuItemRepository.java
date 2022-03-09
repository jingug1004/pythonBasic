package com.beauty.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beauty.entity.MainMenu;
import com.beauty.entity.MainMenuItem;


@Repository
@Qualifier(value = "mainMenuItemRepository")
public interface MainMenuItemRepository extends JpaRepository<MainMenuItem, Long> {
	@Modifying
	@Transactional
	@Query(value="delete from MainMenuItem m where m.mainMenu = ?1")
	public void deleteByMainMenu(MainMenu mainMenu);
	
	@Modifying
	@Transactional
	@Query(value="delete from MainMenuItem m where m.mainMenu in ?1")
	public void deleteByMainMenu(List<MainMenu> mainMenu);
}

package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.MainTenMenu;


@Repository
@Qualifier(value = "mainTenMenuRepository")
public interface MainTenMenuRepository extends JpaRepository<MainTenMenu, Long> {
	public List<MainTenMenu> findByShowYn(String show);
}

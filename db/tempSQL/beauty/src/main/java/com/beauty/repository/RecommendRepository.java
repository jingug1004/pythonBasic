package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Recommend;

@Repository
@Qualifier(value = "recommendRepository")
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
	
	public Long countByReCode(String recode);
}
	

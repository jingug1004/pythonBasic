package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.DetailTopContent;

@Repository
@Qualifier(value = "DetailTopContentRepository")
public interface DetailTopContentRepository extends JpaRepository<DetailTopContent, String>, JpaSpecificationExecutor<DetailTopContent> {
	
}

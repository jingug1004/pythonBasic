package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.DeliveryCompany;

@Repository
@Qualifier(value = "DeliveryCompanyRepository")
public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, String>, JpaSpecificationExecutor<DeliveryCompany> {
    
	public String countByName(String name);
}

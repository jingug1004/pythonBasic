package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.SmsCert;


@Repository
@Qualifier(value = "smsCertRepository")
public interface SmsCertRepository extends JpaRepository<SmsCert, String> {
	public SmsCert findByPhoneAndCertNumber(String phone, String cert);
	
}

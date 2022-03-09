package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Payments;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "paymentRepository")
public interface PaymentRepository extends JpaRepository<Payments, String>, JpaSpecificationExecutor<Payments> {
	
	public Payments findByPaymentIdAndUser(String paymentId, User user);
	
	public Long countByUser(User user);
	
	public List<Payments> findByUserAndStatus(User user, String status);
	
	public Page<Payments> findByStatus(String status, Pageable pageRequest);
	
	public List<Payments> findByImpUidAndMerchantUid(String impUid, String merchantUid);
}

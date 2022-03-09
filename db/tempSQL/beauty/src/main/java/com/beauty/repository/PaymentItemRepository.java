package com.beauty.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beauty.entity.PaymentItem;
import com.beauty.entity.Payments;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "paymentItemRepository")
public interface PaymentItemRepository extends JpaRepository<PaymentItem, Long> {
	
	public List<PaymentItem> findByPayments(Payments payments);
	public List<PaymentItem> findByPaymentsIn(List<Payments> payments);
	public Page<PaymentItem> findByUserAndStatusAndProdConfirmAndDeliveryNumberNull(User userId, String status, int confirm, Pageable pageRequest);
	public Page<PaymentItem> findByUserAndStatusAndDeliveryNumberNotNull(User userId, String status, Pageable pageRequest);
	
	
	public Long countByPaymentsAndProdConfirm(Payments payment, int confirm);
	public List<PaymentItem> findByProdConfirmAndPaymentsIn(int confirm, List<Payments> payments);
	
	@Modifying
	@Transactional
	@Query("UPDATE PaymentItem p set p.status = :status where p.payments = :payments")
	public void updateStatus(@Param("status") String status, @Param("payments") Payments payments);
	
}

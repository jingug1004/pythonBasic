package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Product;
import com.beauty.entity.ReviewWait;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "reviewWaitRepository")
public interface ReviewWaitRepository extends JpaRepository<ReviewWait, Long> {

	
	public List<ReviewWait> findByUserAndReviewWrite(User user, String reviewWrite);
	
	public ReviewWait findByUserAndProductAndPaymentId(User user, Product product, String paymentId);
	
	public ReviewWait findByUserAndReviewId(User user, Long reviewId);
}

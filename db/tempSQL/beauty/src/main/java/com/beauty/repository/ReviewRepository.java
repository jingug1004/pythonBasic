package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Product;
import com.beauty.entity.Review;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "reviewRepository")
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	public Page<Review> findByProduct(Product product, Pageable pageable);
	public Page<Review> findByProductAndReviewType(Product product, int reviewType,  Pageable pageable);
	public Review findByReviewIdAndUser(Long reviewId, User user);
	public List<Review> findByUser(User user);
}

package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Review;
import com.beauty.entity.ReviewLike;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "reviewLikeRepository")
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
	public ReviewLike findByReviewAndUser(Review review, User user);
	
	public List<ReviewLike> findByReview(Review review);
	public List<ReviewLike> findByUser(User user);
	public List<ReviewLike> findByReviewIn(List<Review> review);
}

package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Review;
import com.beauty.entity.ReviewPhoto;


@Repository
@Qualifier(value = "reviewPhotoRepository")
public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {
	
	public List<ReviewPhoto> findByReviewId(Review review);
}

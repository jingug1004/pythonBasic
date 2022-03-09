package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.ReviewWait;
import com.beauty.entity.ReviewWaitItem;

@Repository
@Qualifier(value = "reviewWaitItemRepository")
public interface ReviewWaitItemRepository extends JpaRepository<ReviewWaitItem, Long> {
	
	public List<ReviewWaitItem> findByReviewWait(ReviewWait reviewWait);
}

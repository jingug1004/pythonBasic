package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.User;
import com.beauty.entity.UserPoint;

@Repository
@Qualifier(value = "userRepository")
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {

	 public Page<UserPoint> findByUser(User user, Pageable pageable);
	 public List<UserPoint> findByUser(User user);
	 
}

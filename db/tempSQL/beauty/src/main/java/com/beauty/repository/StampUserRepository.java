package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.StampUser;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "StampUserRepository")
public interface StampUserRepository extends JpaRepository<StampUser, Long> {
	
	public List<StampUser> findByUserAndUseMonth(User user, Long useMonth);
}

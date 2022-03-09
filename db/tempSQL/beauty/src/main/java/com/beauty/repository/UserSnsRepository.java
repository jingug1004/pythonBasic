package com.beauty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.User;
import com.beauty.entity.UserSns;


@Repository
@Qualifier(value = "userRepository")
public interface UserSnsRepository extends JpaRepository<UserSns, String> {
	
//	public UserSns findBySnsIdAndJoinType(String snsId, String joinType);
	public Optional<UserSns> findBySnsIdAndJoinType(String snsId, String joinType);
	public List<UserSns> findByUser(User user);
	public Optional<UserSns> findBySnsIdAndJoinTypeAndUser(String snsId, String joinType, User user);
	public UserSns findByJoinTypeAndUser(String joinType, User user);
}

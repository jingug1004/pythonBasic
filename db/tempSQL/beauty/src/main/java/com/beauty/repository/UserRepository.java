package com.beauty.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Role;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "userRepository")
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
	
    @Query("select u from User u left join fetch u.roles r where u.delYn='N' and r.id.role=:role order by u.regDate desc")
    public List<User> findByRole(@Param("role") Role role);
    
	@Query("select u from User u left join fetch u.roles r where u.userId=:userId")
    public Optional<User> findByUserId(@Param("userId") String userId);
	
	@Query("select u from User u left join fetch u.roles r where u.userId=:userId or u.email=:userId")
    public User findByUserIdOrEmail(@Param("userId") String userId);
	
	public User findByMyCode(String myCode);
	
	public User findByPhone(String phone);
	
	public User findByNameAndUserId(String name, String phone);
	
	public User findByEmailAndUserId(String email, String phone);
	public User findByEmailAndName(String email, String name);
}

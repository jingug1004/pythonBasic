package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.UserRole;


/**
 * UserRepository
 * 
 * @author vladimir.stankovic
 *
 * Aug 16, 2016
 */

@Repository
@Qualifier(value = "UserRoleRepository")
public interface UserRoleRepository extends JpaRepository<UserRole, UserRole.Id> {
	
}

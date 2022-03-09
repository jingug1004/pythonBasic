package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Stamp;


@Repository
@Qualifier(value = "StampRepository")
public interface StampRepository extends JpaRepository<Stamp, Long> {
	
	public List<Stamp> findByStampMonth(Long month);
}

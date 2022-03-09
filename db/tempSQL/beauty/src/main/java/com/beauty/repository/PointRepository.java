package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Point;


@Repository
@Qualifier(value = "PointRepository")
public interface PointRepository extends JpaRepository<Point, String> {
	
}

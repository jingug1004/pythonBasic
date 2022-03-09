package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.DetailTop;

@Repository
@Qualifier(value = "DetailTopRepository")
public interface DetailTopRepository extends JpaRepository<DetailTop, Long> {
}

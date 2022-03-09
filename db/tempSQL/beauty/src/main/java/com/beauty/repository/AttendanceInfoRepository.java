package com.beauty.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.AttendanceInfo;


@Repository
@Qualifier(value = "AttendanceInfoRepository")
public interface AttendanceInfoRepository extends JpaRepository<AttendanceInfo, Long> {
}

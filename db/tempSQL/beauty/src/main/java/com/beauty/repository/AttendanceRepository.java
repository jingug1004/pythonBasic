package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Attendance;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "attendanceRepository")
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	public int countByUserAndAttendMonthAndAttendDay(User user, Long attendMonth, Long attendDay);
	public List<Attendance> findByUserAndAttendMonth(User user, Long attendMonth);
}

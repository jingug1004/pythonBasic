package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Brand;
import com.beauty.entity.Event;


@Repository
@Qualifier(value = "eventRepository")
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Brand> {
	public Page<Event> findByStopEvent(int stopEvent, Pageable pageable);
	public Event findFirstByOrderBySortDesc();
	
	public List<Event> findBySortLessThanEqualAndSortGreaterThan(int neworder, int oldorder);
	public List<Event> findBySortLessThanAndSortGreaterThanEqual(int oldorder, int neworder);
	
	public List<Event> findByEtypeAndStopEvent(int eType, int stopEvent);
	
	public List<Event> findByStopEvent(int stopEvent);
	
	// DELETE FROM APP_EVENT WHERE thumbnail = :thumbnail
	//public void deleteByThumbnail(String thumbnail);
}

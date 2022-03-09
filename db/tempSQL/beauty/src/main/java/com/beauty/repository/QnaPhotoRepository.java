package com.beauty.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.beauty.entity.QnaPhoto;


@Repository
@Qualifier(value = "qnaPhotoRepository")
public interface QnaPhotoRepository extends JpaRepository<QnaPhoto, Long> {
	@Modifying
	@Transactional
	@Query(value="delete from QnaPhoto p where p.qnaId = ?1")
	public void deleteByQnaId(Long qnaId);
}

package com.beauty.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beauty.entity.Qna;
import com.beauty.entity.User;


@Repository
@Qualifier(value = "qnaRepository")
public interface QnaRepository extends JpaRepository<Qna, Long> {
	
	public List<Qna> findByUserOrderByRegDateDesc(User user);
	public Qna findByIdAndUser(Long id, User user);
	public void deleteByUser(User user);
	
	public int countByReplyYn(String replyYn);
	
}

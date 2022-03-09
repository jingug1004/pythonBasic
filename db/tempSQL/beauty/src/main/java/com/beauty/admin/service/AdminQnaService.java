package com.beauty.admin.service;

import java.util.List;

import com.beauty.entity.Qna;

public interface AdminQnaService {

	public List<Qna> findByQnaList();
	public Qna findByQna(Long id);
	public String replyQna(Long id, String reply);
	
}

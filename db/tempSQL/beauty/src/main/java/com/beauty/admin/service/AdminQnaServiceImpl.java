package com.beauty.admin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.beauty.entity.Qna;
import com.beauty.repository.QnaRepository;

@Service
public class AdminQnaServiceImpl implements AdminQnaService {

	@Autowired
	private QnaRepository qnaRepository;

	@Override
	public List<Qna> findByQnaList() {
		return qnaRepository.findAll(new Sort(Direction.ASC, "replyYn").and(new Sort(Direction.DESC, "regDate")));
	}

	@Override
	public Qna findByQna(Long id) {
		return qnaRepository.findOne(id);
	}

	@Override
	public String replyQna(Long id, String reply) {
		try {
			Qna qna = qnaRepository.findOne(id);
			qna.setReply_content(reply);
			qna.setReplyYn("Y");
			qna.setReplyDate(new Date());
			qnaRepository.save(qna);
		} catch (Exception e) {
			return "failed";
		}
		return "success";
	}

}

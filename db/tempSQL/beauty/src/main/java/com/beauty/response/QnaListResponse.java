package com.beauty.response;

import java.util.Date;
import java.util.List;

import com.beauty.entity.Qna;
import com.beauty.response.result.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
public class QnaListResponse {
	
	@ApiModelProperty(value = "200 성공 99 실패")
	@Getter 
	private final ResultCode resultCode;

	@ApiModelProperty(value = "성공시 success 실패시 실패 내용 확인")
	@Getter 
    private final String message;

	@Getter 
    private final Date timestamp;
    
	@ApiModelProperty(value = "Qna리스트")
	@Getter 
	private List<Qna> qnaList;
	
    protected QnaListResponse(final String message, final ResultCode resultCode, final List<Qna> qnaList) {
        this.message = message;
        this.resultCode = resultCode;
        this.qnaList = qnaList;
        this.timestamp = new java.util.Date();
    }

    public static QnaListResponse of(final String message, final ResultCode resultCode,  final List<Qna> qnaList) {
        return new QnaListResponse(message, resultCode, qnaList);
    }
}

package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_RECOMMENT")
@EqualsAndHashCode
@ToString
@ApiModel
public class Recommend {

	@Id 
    @Column(name="re_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long reId;
	
    
    @ApiModelProperty(value = "추천코드")
    @Column(name="re_code", nullable=false, length=20)
    @Getter @Setter
	private String reCode;
    
	@ApiModelProperty(value = "추천인")
	@ManyToOne
	@JoinColumn(name="user_id")
	@Getter @Setter
	private User user;
	
	@ApiModelProperty(value = "등록일")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	
}

package com.beauty.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_PLAN")
@ApiModel
public class Plan {

	@ApiModelProperty(value = "기획전 ID")
	@Id
	@Column(name="p_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long pid;
	
	@ApiModelProperty(value = "제목")
	@Column(name="p_title")
	@Getter @Setter
	private String title;
	
	@ApiModelProperty(value = "썸네일")
	@Column(name="thumbnail")
	@Getter @Setter
	private String thumbnail;
	
	@Column(name="stop_plan", columnDefinition="int(1) default '0'")
	@ApiModelProperty(value = "진행여부  0 : 진행중  1 : 종료")
	@Getter @Setter
	private int stopPlan = 0;
	
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	
    @ApiModelProperty(value = "순서")
	@Column(name="sort")
	@Getter @Setter
    private int sort;
    
    @ApiModelProperty(value = "기획전 그룹")
    @OneToMany(cascade=CascadeType.ALL, mappedBy="plan")
    @OrderBy("sort ASC")
	@Getter @Setter
	public Set<PlanGroup> planGroup;
    
}

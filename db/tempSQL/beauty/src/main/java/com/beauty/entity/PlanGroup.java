package com.beauty.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="APP_PLAN_GROUP")
@EqualsAndHashCode
@ApiModel
public class PlanGroup {

	@ApiModelProperty(value = "기획전 그룹 ID")
	@Id
	@Column(name="pg_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long pgId;
	
	@ApiModelProperty(value = "썸네일")
	@Column(name="thumbnail")
	@Getter @Setter
	private String thumbnail;
	
    @ApiModelProperty(value = "기획전")
	@ManyToOne
    @JoinColumn(name="p_id")
	@Getter @Setter
	@JsonBackReference
	private Plan plan;

	@Column(name="stop_group", columnDefinition="int(1) default '0'")
	@ApiModelProperty(value = "진행여부  0 : 진행중  1 : 종료")
	@Getter @Setter
	private int stopGroup = 0;
	
    @ApiModelProperty(value = "기획전 상품 목록")
    @OneToMany(cascade=CascadeType.ALL, mappedBy="planGroup")
	@Getter @Setter
	public List<PlanProduct> planProduct;
    
    @ApiModelProperty(value = "순서")
	@Column(name="sort")
	@Getter @Setter
    private int sort;
    
}

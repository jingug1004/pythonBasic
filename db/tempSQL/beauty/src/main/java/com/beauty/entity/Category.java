package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_CATEGORY")
@EqualsAndHashCode
@ToString
@ApiModel
public class Category {

	@ApiModelProperty(value = "카테고리 ID")
    @Id
    @Column(name="menu_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
	private Long menuId;
	
    @ApiModelProperty(value = "카테고리명")
    @Column(name="menu_name")
    @Getter @Setter
	private String menuName;
	
	// 부모
    @ApiModelProperty(value = "부모카테고리 코드")
    @Column(name="menu_parent")
    @Getter @Setter
	private Long menuParent;
    
	// 순서
    @JsonIgnore
    @Column(name="menu_sort")
    @Getter @Setter
    private int menuSort;
	
    @Column(name="menu_depth")
    @Getter @Setter
    private int menuDepth;
    
	// 사용여부
    @ApiModelProperty(value = "사용여부 0 : 미사용  1 : 사용")
    @Column(name="menu_visible")
    @Getter @Setter
	private int menuVisible;
	
}

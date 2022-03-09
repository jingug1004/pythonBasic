package com.beauty.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_MAIN_MENU")
@EqualsAndHashCode
@ToString
@ApiModel
public class MainMenu {

	@Id 
    @Column(name="m_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long mid;
	
	@ApiModelProperty(value = "이름")
	@Getter @Setter
	private String name;
	
	@ApiModelProperty(value = "노출 여부")
    @Column(name="show_yn", columnDefinition="char(1) default 'N'")
	@Getter @Setter
	private String showYn = "N";
	
    @ApiModelProperty(value = "정렬")
    @Column(name="sort", columnDefinition="int(3) default '0'")
	@Getter @Setter
	private int sort;
	
    @ApiModelProperty(value = "메인 메뉴 상품 목록")
    @OneToMany(cascade=CascadeType.ALL, mappedBy="mainMenu")
	@Getter @Setter
	public List<MainMenuItem> mainMenuItem;
    
	@ApiModelProperty(value = "등록일")
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
	
}

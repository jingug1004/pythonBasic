package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_MAIN_ITEM")
@EqualsAndHashCode
@ToString
@ApiModel
public class MainMenuItem {

	@Id 
    @Column(name="mi_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Getter @Setter
	private Long miId;
	
    @ApiModelProperty(value = "상품")
	@ManyToOne
	@JoinColumn(name="m_id")
    @JsonBackReference
	@Getter @Setter
	private MainMenu mainMenu;
    
    @ApiModelProperty(value = "상품")
	@ManyToOne
	@JoinColumn(name="product_id")
	@Getter @Setter
	private Product product;
    
    @ApiModelProperty(value = "정렬")
    @Column(name="sort", columnDefinition="int(3) default '0'")
	@Getter @Setter
	private int sort;
}

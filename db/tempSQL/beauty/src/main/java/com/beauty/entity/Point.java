package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/*
 * 포인트 테이블
 * 포인트ID	VARCHAR(50)
 * 포인트이름	VARCHAR(20)
 * 포인트점수	INT(5)
 */
@Entity
@Table(name="APP_POINT")
@EqualsAndHashCode
@ToString
public class Point {
    @Id 
    @Column(name="ID", length=50)
    @Getter @Setter
    private String id;
    
    @ApiModelProperty(value = "포인트이름")
    @Column(name="point_name", nullable=false, length=20)
    @Getter @Setter
    private String pointName;
    
    @ApiModelProperty(value = "포인트점수")
    @Column(name="point", columnDefinition="INT(5)")
    @Getter @Setter
    private Long point;
    
    @ApiModelProperty(value = "포인트타입  0 : 구매금액 %,  1 : 포인트")
    @Column(name="p_type", columnDefinition="INT(1)")
    @Getter @Setter
    private int pointType=0;
    
}

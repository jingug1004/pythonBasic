package com.beauty.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_DELIVERY_COMPANY")
@EqualsAndHashCode
@ToString
@ApiModel
public class DeliveryCompany {
	
    @Id
    @ApiModelProperty(value = "택배사 코드")
    @Column(name="code")
    @Getter @Setter
	private String code;
    
    @ApiModelProperty(value = "택배사")
    @Column(name="name", nullable=true, length=20)
    @Getter @Setter
	private String name;
}

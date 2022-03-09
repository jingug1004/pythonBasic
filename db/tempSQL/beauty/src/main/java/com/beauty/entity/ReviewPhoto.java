package com.beauty.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="APP_REVIEW_PHOTO")
@EqualsAndHashCode
@ToString
public class ReviewPhoto {
	
	@JsonIgnore
	@Id
	@GeneratedValue
	@Column(name="photo_id")
	@Getter @Setter
	private Long photoId;
	
	@ApiModelProperty(value = "리뷰 ID")
	@ManyToOne
	@JoinColumn(name="review_id")
	@JsonBackReference
	@Getter @Setter
	private Review reviewId;
	
	@Column(name="photo_path")
	@Getter @Setter
	private String photoPath;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_date")
	@Getter @Setter
	private Date regDate = new Date();
}

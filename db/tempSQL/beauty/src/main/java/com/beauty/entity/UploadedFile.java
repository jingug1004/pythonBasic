package com.beauty.entity;

import lombok.Getter;
import lombok.Setter;

public class UploadedFile {


	@Getter @Setter
	private Long id;
	@Getter @Setter
	private String name;
	@Getter @Setter
	private String location;
	@Getter @Setter
	private Long size;
	@Getter @Setter
	private String type;
}
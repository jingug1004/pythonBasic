package com.beauty.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class DataTablesResult {

	@Getter @Setter
	private int draw;
	
	@Getter @Setter
	private int recordsTotal;
	
	@Getter @Setter
	private Long recordsFiltered;
	
	@Getter @Setter
	private List data;
}

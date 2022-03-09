package com.beauty.admin.response;

import lombok.Getter;
import lombok.Setter;

public class JsTreeNode {
//	[{
//		  "id":1,"text":"Root node","children":[
//		    {"id":2,"text":"Child node 1","children":true},
//		    {"id":3,"text":"Child node 2"}
//		  ]
//		}]
	
	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String text;
	
	@Getter @Setter
	private boolean children;
	
	@Getter @Setter
	private String type;
	
}

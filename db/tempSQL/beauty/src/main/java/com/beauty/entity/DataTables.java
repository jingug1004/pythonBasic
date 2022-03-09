package com.beauty.entity;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.ToString;

@ToString
public class DataTables {

    private int draw;
    private List<HashMap<String, String>> columns;
    private List<HashMap<String, String>> order;
    private int start;
    private int length;
    private HashMap<String, String> search;
    
    public int getDraw() {
        return draw;
    }
    public void setDraw(int draw) {
        this.draw = draw;
    }
    public List<HashMap<String, String>> getColumns() {
        return columns;
    }
    public void setColumns(List<HashMap<String, String>> columns) {
        this.columns = columns;
    }
    public List<HashMap<String, String>> getOrder() {
        return order;
    }
    public void setOrder(List<HashMap<String, String>> order) {
        this.order = order;
    }
    public int getStart() {
        return start/getLength();
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
	public HashMap<String, String> getSearch() {
		return search;
	}
	public void setSearch(HashMap<String, String> search) {
		this.search = search;
	}
    
	
	public Sort getSort(String defSortName) {
		Direction direction = Direction.DESC;
		Sort sort = null;
		if(getOrder() != null) {
			for(HashMap<String, String> order:getOrder()) {
				defSortName = getColumns().get(Integer.parseInt(order.get("column"))).get("name");
				if(order.get("dir").equals("asc")) {
					direction = Direction.ASC;
				}
				if(sort == null) {
					sort = new Sort(direction, defSortName);
				} else {
					sort.and(new Sort(direction, defSortName));
				}
			}
		}
		return sort;
	}
}
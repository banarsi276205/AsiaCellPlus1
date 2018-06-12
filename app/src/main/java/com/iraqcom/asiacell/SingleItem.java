package com.iraqcom.asiacell;

public class SingleItem {
	String images;
	String text;
	String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SingleItem(String images,String text) {
		this.images=images;
		this.text=text;
}
}
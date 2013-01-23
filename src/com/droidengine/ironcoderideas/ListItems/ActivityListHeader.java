package com.droidengine.ironcoderideas.ListItems;


public class ActivityListHeader implements Item{

	private String headerName;
	
	public void setHeader(String header){
		headerName = header;
	}
	
	public String getHeader(){
		return headerName;
	}
	
	@Override
	public boolean isSection() {
		return true;
	}

}

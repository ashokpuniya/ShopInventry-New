package com.ashok.shopInventory.web.enums;

public enum ShopItemStatusEnum {
	File_Upload("File Upload"),
	Video_Upload("Video Upload"),
	DEAD("DEAD"),
	PROCESSED("PROCESSED");
	
	String name;

	ShopItemStatusEnum(String name){
		this.name= name;
	}
	public String getName() {
		return name;
	}
	
}

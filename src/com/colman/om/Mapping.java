package com.colman.om;

import org.simpleframework.xml.Attribute;

public class Mapping {
	
	@Attribute
	private String key;
	@Attribute
	private String value;

	public Mapping(){
		
	}
	
	public Mapping(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

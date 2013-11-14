package com.colman.om;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Activity {
	
	@ElementList(inline = true)
	private List<Recreation> recreationList = new ArrayList<Recreation>();

	public void setRecreationList(List<Recreation> recreationList) {
		this.recreationList = recreationList;
	}

	public List<Recreation> getRecreationList() {
		return recreationList;
	}
}

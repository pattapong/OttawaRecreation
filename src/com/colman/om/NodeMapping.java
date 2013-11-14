package com.colman.om;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class NodeMapping {
	@ElementList(inline = true)
	private List<Mapping> nodeMappingList = new ArrayList<Mapping>();

	public NodeMapping() {
	}

	public void setNodeMappingList(List<Mapping> nodeMappingList) {
		this.nodeMappingList = nodeMappingList;
	}

	public List<Mapping> getNodeMappingList() {
		return nodeMappingList;
	}
}

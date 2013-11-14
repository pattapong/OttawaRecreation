package com.colman.om;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class QueryParameter {

	@ElementList(inline=true)
	private List<Mapping> queryList = new ArrayList<Mapping>();

	public void setQueryList(List<Mapping> queryList) {
		this.queryList = queryList;
	}

	public List<Mapping> getMappings() {
		return queryList;
	}
}

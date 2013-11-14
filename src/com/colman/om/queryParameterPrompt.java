package com.colman.om;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;

public class queryParameterPrompt {
	@ElementList(inline = true)
	private List<Mapping> queryParameterPromptList = new ArrayList<Mapping>();

	public void setQueryParameterPromptList(List<Mapping> queryParameterPromptList) {
		this.queryParameterPromptList = queryParameterPromptList;
	}

	public List<Mapping> getQueryParameterPromptList() {
		return queryParameterPromptList;
	}


}

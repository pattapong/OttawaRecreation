package com.colman.om;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Recreation {
	@Attribute
	private String host;
	@Attribute
	private String port;
	@Attribute
	private String path;

	@Attribute
	private String name;
	
	@Element
	private QueryParameter queryParameter = new QueryParameter();

	@Element
	private queryParameterPrompt queryParameterPrompt = new queryParameterPrompt();
	
	@Element
	private NodeMapping nodeMapping = new NodeMapping();


	public Recreation(){
		
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPort() {
		return port;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setParameter(QueryParameter parameter) {
		this.queryParameter = parameter;
	}

	public QueryParameter getParameter() {
		return queryParameter;
	}

	public void setNodeMapping(NodeMapping nodeMapping) {
		this.nodeMapping = nodeMapping;
	}

	public NodeMapping getNodeMapping() {
		return nodeMapping;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setQueryParameterPrompt(queryParameterPrompt queryParameterPrompt) {
		this.queryParameterPrompt = queryParameterPrompt;
	}

	public queryParameterPrompt getQueryParameterPrompt() {
		return queryParameterPrompt;
	}
}

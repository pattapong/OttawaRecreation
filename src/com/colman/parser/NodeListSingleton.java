package com.colman.parser;

import java.util.List;

import org.w3c.dom.NodeList;

public class NodeListSingleton {

	private static NodeListSingleton instance;

	private List<String> breadcrumb;

	private NodeList nodeList;

	private NodeListSingleton() {

	}

	public static NodeListSingleton getInstance() {
		if (instance == null) {
			instance = new NodeListSingleton();

			return instance;
		}

		return instance;

	}

	public NodeList getNodeList() {
		return nodeList;
	}

	public void setNodeList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	public void setBreadcrumb(List<String> breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	public List<String> getBreadcrumb() {
		return breadcrumb;
	}
}

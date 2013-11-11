package com.colman.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PoolManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSchedule() {

		NodeList docList = PoolManager.getSchedule("Brewer+Pool", "Lane+Swim", "today");

		List<String> day = PoolManager
				.getAttributeValue(docList.item(0), "day");

		assertTrue(day.get(0).equalsIgnoreCase("Monday"));

	}

	@Test
	public void testGetAttributeValue() {

		Document document;

		document = XmlParser.getDom(new File(
				"./src/com/colman/parser/resultSample.xml"));

		final NodeList docList = document.getElementsByTagName("doc");

		List<String> day = PoolManager
				.getAttributeValue(docList.item(0), "day");

		assertTrue(day.get(0).equalsIgnoreCase("Monday"));

	}
}

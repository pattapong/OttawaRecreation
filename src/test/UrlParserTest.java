package test;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.colman.parser.UrlParser;

public class UrlParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetQueryValue() {
		URL url;
		try {
			url = new URL(
					"http://apps104.ottawa.ca/emap?emapver=lite&amp;LAT=45.389361&amp;LON=-75.691916&amp;featname=Brewer Pool%5Cn151 Brewer&amp;lang=en");
			assertTrue(UrlParser.getQueryValue(url, "LAT").equalsIgnoreCase(
					"45.389361"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

package com.colman.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;

import com.colman.rest.RequestManager;

public class PoolManager {

	final static Map<String, String> nodeAttributeMap = new HashMap<String, String>();

	final static Map<String, String> dayMap = new HashMap<String, String>();

	static {
		nodeAttributeMap.put("day", "arr");
		nodeAttributeMap.put("dayNo", "arr");
		nodeAttributeMap.put("end_date", "date");
		nodeAttributeMap.put("end_time", "str");
		nodeAttributeMap.put("event", "arr");
		nodeAttributeMap.put("id", "str");
		nodeAttributeMap.put("lang", "str");
		nodeAttributeMap.put("location", "arr");
		nodeAttributeMap.put("location_alt", "arr");
		nodeAttributeMap.put("location_map", "str");
		nodeAttributeMap.put("location_url", "str");
		nodeAttributeMap.put("popularity", "int");
		nodeAttributeMap.put("region", "arr");
		nodeAttributeMap.put("session_type", "arr");
		nodeAttributeMap.put("start_date", "date");
		nodeAttributeMap.put("start_time", "str");
		nodeAttributeMap.put("timestamp", "date");

		dayMap.put("today", "day_today");
		dayMap.put("Monday", "sq_day_monday");
		dayMap.put("Tuesday", "sq_day_tuesday");
		dayMap.put("Wednesday", "sq_day_wednesday");
		dayMap.put("Thursday", "sq_day_thursday");
		dayMap.put("Friday", "sq_day_friday");
		dayMap.put("Saturday", "sq_day_saturday");
		dayMap.put("Sunday", "sq_day_sunday");
	}

	public static List<String> getAllPools(final Context context) {

		final List<String> poolList = new ArrayList<String>();

		Document locationDom;
		try {

			AssetManager am = context.getAssets();

			InputStream is = am.open("Location.xml");

			locationDom = XmlParser.getDom(is);

			final NodeList nList = locationDom.getElementsByTagName("Pool");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				final Pool newPool = new Pool();

				final Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					newPool.setClassName(eElement.getAttribute("class"));

					newPool.setName(eElement.getAttribute("value"));
				}

				poolList.add(newPool.getName());
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return poolList;
	}

	public static List<String> getAllTypes(Context context) {

		final List<String> typeList = new ArrayList<String>();

		AssetManager am = context.getAssets();

		InputStream is;
		try {
			is = am.open("SwimType.xml");

			final Document locationDom = XmlParser.getDom(is);

			final NodeList nList = locationDom.getElementsByTagName("Option");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				final Type newType = new Type();

				final Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					newType.setClassName(eElement.getAttribute("class"));

					newType.setName(eElement.getAttribute("value"));
				}

				typeList.add(newType.getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return typeList;
	}

	public static List<String> getAllDays(final Context context) {

		final List<String> dayList = new ArrayList<String>();

		AssetManager am = context.getAssets();

		InputStream is;
		try {
			is = am.open("Day.xml");

			final Document locationDom = XmlParser.getDom(is);

			final NodeList nList = locationDom.getElementsByTagName("Day");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				final Day Day = new Day();

				final Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					dayList.add(eElement.getAttribute("value"));
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dayList;
	}

	public static NodeList getSchedule(String poolName, String swimType, String day) {

		poolName = poolName.replaceAll(" ", "+");

		swimType = swimType.replaceAll(" ", "+");

		final String sqDate = dayMap.get(day);
		
		final List<BasicNameValuePair> qparams = new ArrayList<BasicNameValuePair>();

		qparams.add(new BasicNameValuePair("stylesheet",
				"http://app06.ottawa.ca/templates/xslt/public_swimming_results_en.xsl"));
		qparams.add(new BasicNameValuePair("sq_event", "Swimming"));
		qparams.add(new BasicNameValuePair("sq_lang", "en"));
		qparams.add(new BasicNameValuePair("sort",
				"location+asc,start_date+asc,dayNo+asc,start_time+asc,session_type+asc"));
		qparams.add(new BasicNameValuePair("sq_location", poolName));
		qparams.add(new BasicNameValuePair("sq_session_type", swimType));
		qparams.add(new BasicNameValuePair(sqDate, day));

		final String query = RequestManager.getQuery(qparams);

		final HttpGet httpGet = RequestManager.getHttpGet("app06.ottawa.ca",
				-1, "/cgi-bin/schedulesearch/searchschedule.pl", query);

		System.out.println(httpGet.getURI());

		InputStream inputStream = RequestManager.getResponse(httpGet);

		Document document;
		try {
			document = getDom(inputStream);

			final NodeList docList = document.getElementsByTagName("doc");

			return docList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static Document getDom(final InputStream str) throws IOException,
			ParserConfigurationException, SAXException {

		return XmlParser.getDom(str);

	}

	public static List<String> getAttributeValue(Node doc, String attributeName) {

		final List<String> strList = new ArrayList<String>();

		try {

			XPath xpath = XPathFactory.newInstance().newXPath();

			Object evaluate;

			final String nodeType = nodeAttributeMap.get(attributeName);

			if (nodeType.equalsIgnoreCase("arr")) {

				evaluate = xpath.evaluate("arr[@name='" + attributeName
						+ "']//str", doc, XPathConstants.NODESET);

				final NodeList arrNodes = (NodeList) evaluate;

				for (int i = 0; i < arrNodes.getLength(); i++) {

					strList.add(arrNodes.item(i).getTextContent());
				}

			} else if (nodeType.equalsIgnoreCase("str")) {

				evaluate = xpath.evaluate("str[@name='" + attributeName + "']",
						doc, XPathConstants.STRING);

				strList.add(evaluate.toString());

			} else if (nodeType.equalsIgnoreCase("date")) {

				evaluate = xpath.evaluate(
						"date[@name='" + attributeName + "']", doc,
						XPathConstants.STRING);

				strList.add(evaluate.toString());

			}

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strList;
	}
}

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

import com.colman.om.Mapping;
import com.colman.om.Recreation;
import com.colman.rest.ConfigurationManager;
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

			// final String nodeType = nodeAttributeMap.get(attributeName);

			final ConfigurationManager instance = ConfigurationManager
					.getInstance();

			final Recreation recreation = instance.getRecreation(instance
					.getRecreationName());

			final String nodeType = ConfigurationManager.getNodeAttribute(
					recreation, attributeName);

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

	public static List<String> getSpinnerItems(final Context context,
			final String itemFile) {

		final List<String> itemList = new ArrayList<String>();

		Document locationDom;
		try {

			AssetManager am = context.getAssets();

			InputStream is = am.open(itemFile);

			locationDom = XmlParser.getDom(is);

			final NodeList nList = locationDom.getElementsByTagName("Option");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				final Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					itemList.add(eElement.getAttribute("qValue"));
				}

			}

		} catch (final ParserConfigurationException e) {

			e.printStackTrace();
		} catch (final SAXException e) {

			e.printStackTrace();
		} catch (final IOException e) {

			e.printStackTrace();
		}

		return itemList;
	}

	public static Mapping getNameValuePair(final Context context,
			final String itemFile, final String value) {

		try {

			AssetManager am = context.getAssets();

			InputStream is = am.open(itemFile);

			return getNameValuePair(value, XmlParser.getDom(is));

		} catch (final ParserConfigurationException e) {

			e.printStackTrace();
		} catch (final SAXException e) {

			e.printStackTrace();
		} catch (final IOException e) {

			e.printStackTrace();
		}

		return null;
	}

	public static Mapping getNameValuePair(final String value,
			Document locationDom) {

		Mapping mapping = new Mapping();

		final NodeList nList = locationDom.getElementsByTagName("Option");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			final Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				if (eElement.getAttribute("qValue").equalsIgnoreCase(value)) {
					mapping.setKey(eElement.getAttribute("qName"));
					mapping.setValue(eElement.getAttribute("qValue"));
					return mapping;
				}
			}

		}
		return null;
	}

	public static NodeList getNodeList(final String hostName,
			final int portNumber, final String path,
			List<Mapping> queryParameters) {

		encode(queryParameters);

		final List<BasicNameValuePair> qparams = new ArrayList<BasicNameValuePair>();

		for (final Mapping nextMapping : queryParameters) {
			qparams.add(new BasicNameValuePair(nextMapping.getKey(),
					nextMapping.getValue()));
		}

		final String query = RequestManager.getQuery(qparams);

		final HttpGet httpGet = RequestManager.getHttpGet(hostName, portNumber,
				path, query);

		System.out.println(httpGet.getURI());

		InputStream inputStream = RequestManager.getResponse(httpGet);

		Document document;
		try {
			document = getDom(inputStream);

			final NodeList docList = document.getElementsByTagName("doc");

			return docList;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return null;

	}

	private static void encode(final List<Mapping> queryParameters) {
		for (final Mapping nextMapping : queryParameters) {
			nextMapping.setValue(nextMapping.getValue().replaceAll(" ", "+"));
			nextMapping.setKey(nextMapping.getKey().replaceAll(" ", "+"));
		}

	}
}

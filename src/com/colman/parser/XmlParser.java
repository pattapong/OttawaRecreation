package com.colman.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;

public class XmlParser {

	public static Document getDom(final InputStream is)
			throws ParserConfigurationException, SAXException, IOException {

		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory
				.newInstance();

		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		final Document doc = dBuilder.parse(is);

		doc.getDocumentElement().normalize();

		return doc;
	}

	public static Document getDom(final File file) {

		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory
				.newInstance();

		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();

			final Document doc = dBuilder.parse(file);

			doc.getDocumentElement().normalize();

			return doc;

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

		return null;
	}

	public static Document getDom(final String str)
			throws ParserConfigurationException, SAXException, IOException {

		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory
				.newInstance();

		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		final Document doc = dBuilder.parse(str);

		doc.getDocumentElement().normalize();

		return doc;
	}

	public static Document getDom(Context context, String string) {

		Document locationDom;

		AssetManager am = context.getAssets();

		InputStream is;
		try {
			is = am.open(string);
			
			locationDom = XmlParser.getDom(is);

			return locationDom;
			
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

}

package com.colman.parser;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlParser {

	public static String getQueryValue(final URL url, final String queryKey) {

		try {
			final Map<String, String> queryMap = splitQuery(url);

			String value = queryMap.get(queryKey);

			return value;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Map<String, String> splitQuery(final URL url)
			throws UnsupportedEncodingException {
		final Map<String, String> query_pairs = new LinkedHashMap<String, String>();

		final String query = url.getQuery();

		final String[] pairs = query.split("&amp;");

		for (final String pair : pairs) {
			final int idx = pair.indexOf("=");

			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
					URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
		}
		return query_pairs;
	}
}

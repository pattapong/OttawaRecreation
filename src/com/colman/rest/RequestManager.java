package com.colman.rest;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class RequestManager {

	public static HttpGet getHttpGet(final String host, final int port,
			final String path, final String query) {
		try {

			URI uri = URIUtils.createURI("http", host, port, path, query, null);

			HttpGet httpget = new HttpGet(uri);

			return httpget;

		} catch (final URISyntaxException e) {

			e.printStackTrace();
		}

		return null;
	}

	public static String getQuery(final List<BasicNameValuePair> qparams) {
		final StringBuffer buffer = new StringBuffer();

		for (final BasicNameValuePair next : qparams) {
			buffer.append(next.getName());
			buffer.append("=");
			buffer.append(next.getValue());
			buffer.append("&");
		}
		return buffer.toString();
	}

	public static InputStream getResponse(final HttpGet httpGet) {

		try {

			final HttpClient httpclient = new DefaultHttpClient();

			final HttpResponse response = httpclient.execute(httpGet);

			return response.getEntity().getContent();

		} catch (final Exception e) {

			e.printStackTrace();
		}

		return null;
	}

}

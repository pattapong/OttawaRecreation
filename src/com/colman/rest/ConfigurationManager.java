package com.colman.rest;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.content.res.AssetManager;

import com.colman.om.Activity;
import com.colman.om.Mapping;
import com.colman.om.Recreation;

public class ConfigurationManager {

	private static ConfigurationManager configurationManager;

	private Activity activity = null;

	private String recreationName = null;

	private Context context;

	private Recreation recreation = null;

	public void init() {

		final Serializer serializer = new Persister();

		if (activity == null) {
			
			if (context == null) {
				try {

					this.activity = serializer.read(Activity.class, new File(
							"././assets/Configuration.xml"));

				} catch (final Exception e) {

					e.printStackTrace();
				}

			} else {
				final AssetManager am = context.getAssets();

				try {
					final InputStream is = am.open("Configuration.xml");

					this.activity = serializer.read(Activity.class, is);

				} catch (final Exception e) {

					e.printStackTrace();
				}
			}
		}
		if (activity != null && recreationName != null) {

			setRecreation(getRecreation(recreationName));

		}
	}

	public static ConfigurationManager getInstance() {

		if (configurationManager == null) {

			configurationManager = new ConfigurationManager();

		}

		return configurationManager;
	}

	public Activity getActivity() {

		return activity;
	}

	public Recreation getRecreation(final String recreationName) {
		for (final Recreation next : activity.getRecreationList()) {
			if (next.getName().equalsIgnoreCase(recreationName)) {
				return next;
			}
		}
		return null;
	}

	public static String getNodeAttribute(final Recreation recreation,
			final String attributeName) {

		final List<Mapping> nodeMapping = recreation.getNodeMapping()
				.getNodeMappingList();

		for (final Mapping next : nodeMapping) {
			if (next.getKey().equalsIgnoreCase(attributeName)) {
				return next.getValue();
			}
		}

		return null;
	}

	public void setRecreationName(String recreationName) {
		this.recreationName = recreationName;
	}

	public String getRecreationName() {
		return recreationName;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public void setRecreation(Recreation recreation) {
		this.recreation = recreation;
	}

	public Recreation getRecreation() {
		return recreation;
	}

}

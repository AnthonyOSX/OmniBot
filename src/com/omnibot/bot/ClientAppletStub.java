package com.omnibot.bot;

import com.omnibot.bot.gui.BotFrame;
import com.omnibot.bot.updater.InjectionUpdater;
import com.omnibot.utils.Constants;
import com.omnibot.utils.Utilities;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/13/2014
 */

public class ClientAppletStub implements AppletStub {

	private static final Pattern codeRegex = Pattern.compile("code=(.*) ");
	private static final Pattern archiveRegex = Pattern.compile("archive=(.*) ");
	private static final Pattern parameterRegex = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");

	private Applet applet;
	private URLClassLoader urlClassLoader;
	private URL codeBase, documentBase;
	private Map<String, String> parameters;

	public ClientAppletStub(String url, int width, int height) {
		this.parameters = new HashMap<>();
		try {
			String pageSource = Utilities.downloadPage(new URL(url));
			Matcher codeMatcher = codeRegex.matcher(pageSource);
			Matcher archiveMatcher = archiveRegex.matcher(pageSource);
			if (codeMatcher.find() && archiveMatcher.find()) {
				String archive = archiveMatcher.group(1);
				String jarLocation = url + archive;
				String code = codeMatcher.group(1).replaceAll(".class", "");
				Matcher parameterMatcher = parameterRegex.matcher(pageSource);
				codeBase = new URL(jarLocation);
				documentBase = new URL(url);
				while (parameterMatcher.find()) {
					parameters.put(parameterMatcher.group(1), parameterMatcher.group(2));
				}
				BotFrame.splashScreen.setText("Downloading client jar file...");
				Utilities.downloadFile(new URL(jarLocation), Constants.ORIGINAL_JAR_LOCATION);
				new InjectionUpdater().run();
				urlClassLoader = new URLClassLoader(new URL[] {new URL("file:" + Constants.MODIFIED_JAR_LOCATION)});
				applet = (Applet) urlClassLoader.loadClass(code).newInstance();
				applet.setStub(this);
				applet.setPreferredSize(new Dimension(width, height));
			}
		}
		catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error loading client!");
		}
	}

	public void initialize() {
		if (applet != null) {
			applet.init();
			applet.start();
		}
	}

	public void destruct() {
		if (applet != null) {
			applet.stop();
			applet.destroy();
			applet = null;
		}
		if (urlClassLoader != null) {
			try {
				urlClassLoader.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Applet getApplet() {
		return applet;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public URL getDocumentBase() {
		return documentBase;
	}

	@Override
	public URL getCodeBase() {
		return codeBase;
	}

	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}

	@Override
	public AppletContext getAppletContext() {
		return null;
	}

	@Override
	public void appletResize(int width, int height) {

	}

}
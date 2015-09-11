package com.omnibot.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/13/2014
 */

public class Utilities {

	private static final Utilities instance = new Utilities();
	private static final Random random = new Random();

	private Utilities() {

	}

	public static Utilities getInstance() {
		return instance;
	}

	public static int random(int max) {
		return random.nextInt(max);
	}

	public static int random(int min, int max) {
		return random(Math.abs(max - min)) + min;
	}

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void downloadFile(URL url, String filePath) throws IOException {
		URLConnection connection = url.openConnection();
		try (InputStream is = connection.getInputStream(); FileOutputStream fos = new FileOutputStream(filePath);) {
			byte[] buffer = new byte[1024];
			int read;
			while ((read = is.read(buffer)) != -1) {
				fos.write(buffer, 0, read);
			}
		}
	}

	public static String downloadPage(URL url) throws IOException {
		try {
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("Protocol", "HTTP/1.1");
			connection.addRequestProperty("Connection", "keep-alive");
			connection.addRequestProperty("Keep-Alive", "200");
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
			byte[] buffer = new byte[connection.getContentLength()];
			try (DataInputStream dis = new DataInputStream(connection.getInputStream())) {
				dis.readFully(buffer);
			}
			return new String(buffer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Image scaleImage(int width, int height, Image image) {
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	public Image loadResourceImage(String path) {
		try {
			return ImageIO.read(getClass().getResource(path));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ImageIcon loadResourceImageIcon(String path) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getResource(path)));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
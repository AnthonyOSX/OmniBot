package com.omnibot.bot.gui;

import com.omnibot.bot.ClientAppletStub;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/16/2014
 */

public class BotPanel extends JPanel {

	private ClientAppletStub clientAppletStub;

	public BotPanel(String url, int width, int height) {
		setLayout(new BorderLayout(0, 0));
		this.clientAppletStub = new ClientAppletStub(url, width, height);
		add(clientAppletStub.getApplet(), BorderLayout.CENTER);
	}

	public ClientAppletStub getClientAppletStub() {
		return clientAppletStub;
	}

}

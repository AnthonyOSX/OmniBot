package com.omnibot.bot;

import com.omnibot.bot.gui.BotFrame;
import com.omnibot.bot.gui.BotPanel;
import com.omnibot.utils.Utilities;
import com.omnibot.bot.input.BotInputManager;

import java.applet.Applet;
import java.awt.*;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/13/2014
 */

public class Bot {

	private boolean paintDebug;
	private BotPanel botPanel;
	private BotInputManager botInputManager;

	public Bot(String url, int width, int height) {
		this.botPanel = new BotPanel(url, width, height);
		BotFrame.splashScreen.setText("Starting up...");
		botPanel.getClientAppletStub().initialize();
		while (getCanvas() == null || getCanvas().getMouseListeners() == null || getCanvas().getKeyListeners() == null) {
			Utilities.sleep(100);
		}
		this.botInputManager = new BotInputManager(this);
	}

	public Applet getApplet() {
		return (Applet) botPanel.getComponent(0);
	}

	public Canvas getCanvas() {
		return (getApplet().getComponentCount() > 0) ? (Canvas) getApplet().getComponent(0) : null;
	}

	public BotPanel getBotPanel() {
		return botPanel;
	}

	public BotInputManager getBotInputManager() {
		return botInputManager;
	}

	public void setPaintDebug(boolean paintDebug) {
		this.paintDebug = paintDebug;
	}

	public boolean isPaintDebugEnabled() {
		return paintDebug;
	}

}
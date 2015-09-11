package com.omnibot.bot.input;

import com.omnibot.bot.Bot;

import java.awt.*;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/13/2014
 */

public class BotInputManager {

	private Canvas canvas;
	private BotMouseListener botMouseListener;
	private BotKeyListener botKeyListener;

	public BotInputManager(Bot bot) {
		this.canvas = bot.getCanvas();
		this.botMouseListener = new BotMouseListener(bot);
		this.botKeyListener = new BotKeyListener(bot);
		canvas.addMouseListener(botMouseListener);
		canvas.addKeyListener(botKeyListener);
	}

	public void setKeyboardEnabled(boolean keyboardEnabled) {
		botKeyListener.setKeyboardEnabled(keyboardEnabled);
	}

	public void setMouseEnabled(boolean mouseEnabled) {
		botMouseListener.setMouseEnabled(mouseEnabled);
	}

	public Point getMousePosition() {
		return botMouseListener.getMousePoint();
	}

	public boolean isMouseOnCanvas() {
		return botMouseListener.isMouseOnCanvas();
	}

}

package com.omnibot.bot.input;

import com.omnibot.bot.Bot;
import com.omnibot.bot.input.events.BotEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/14/2014
 */

public class BotKeyListener implements KeyListener {

	private boolean keyboardEnabled;
	private Canvas canvas;
	private KeyListener[] defaultKeyListeners;

	public BotKeyListener(Bot bot) {
		this.keyboardEnabled = true;
		this.canvas = bot.getCanvas();
		this.defaultKeyListeners = canvas.getKeyListeners();
		addBotListeners();
	}

	private void addBotListeners() {
		for (KeyListener l : defaultKeyListeners) {
			canvas.removeKeyListener(l);
		}
		canvas.addKeyListener(this);
	}

	public void setKeyboardEnabled(boolean keyboardEnabled) {
		this.keyboardEnabled = keyboardEnabled;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (keyboardEnabled || e instanceof BotEvent) {
			for (KeyListener l : defaultKeyListeners) {
				l.keyTyped(e);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (keyboardEnabled || e instanceof BotEvent) {
			for (KeyListener l : defaultKeyListeners) {
				l.keyPressed(e);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (keyboardEnabled || e instanceof BotEvent) {
			for (KeyListener l : defaultKeyListeners) {
				l.keyReleased(e);
			}
		}
	}

}
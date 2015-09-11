package com.omnibot.bot.input.events;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/14/2014
 */

public class BotKeyEvent extends KeyEvent implements BotEvent {

	public BotKeyEvent(Component source, int id, long when, int modifiers, int keyCode, char keyChar, int keyLocation) {
		super(source, id, when, modifiers, keyCode, keyChar, keyLocation);
	}

	public BotKeyEvent(Component source, int id, long when, int modifiers, int keyCode, char keyChar) {
		super(source, id, when, modifiers, keyCode, keyChar);
	}

}
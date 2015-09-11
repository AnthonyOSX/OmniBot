package com.omnibot.bot.input.events;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/14/2014
 */

public class BotMouseEvent extends MouseEvent implements BotEvent {

	public BotMouseEvent(Component source, int id, long when, int modifiers, int x, int y, int clickCount, boolean popupTrigger, int button) {
		super(source, id, when, modifiers, x, y, clickCount, popupTrigger, button);
	}

	public BotMouseEvent(Component source, int id, long when, int modifiers, int x, int y, int clickCount, boolean popupTrigger) {
		super(source, id, when, modifiers, x, y, clickCount, popupTrigger);
	}

	public BotMouseEvent(Component source, int id, long when, int modifiers, int x, int y, int xAbs, int yAbs, int clickCount, boolean popupTrigger, int button) {
		super(source, id, when, modifiers, x, y, xAbs, yAbs, clickCount, popupTrigger, button);
	}

}
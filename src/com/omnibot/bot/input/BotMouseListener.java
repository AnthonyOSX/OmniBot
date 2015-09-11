package com.omnibot.bot.input;

import com.omnibot.bot.Bot;
import com.omnibot.bot.input.events.BotEvent;

import java.awt.*;
import java.awt.event.*;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/13/2014
 */

public class BotMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

	private boolean mouseOnCanvas, mouseEnabled;
	private Canvas canvas;
	private Point mousePoint;
	private MouseListener[] defaultMouseListeners;
	private MouseMotionListener[] defaultMouseMotionListeners;
	private MouseWheelListener[] defaultMouseWheelListeners;

	public BotMouseListener(Bot bot) {
		this.mouseEnabled = true;
		this.canvas = bot.getCanvas();
		this.defaultMouseListeners = canvas.getMouseListeners();
		this.defaultMouseMotionListeners = canvas.getMouseMotionListeners();
		this.defaultMouseWheelListeners = canvas.getMouseWheelListeners();
		this.mousePoint = new Point(-1, -1);
		addBotListeners();
	}

	public void addBotListeners() {
		for (MouseListener l : defaultMouseListeners) {
			canvas.removeMouseListener(l);
		}
		for (MouseMotionListener l : defaultMouseMotionListeners) {
			canvas.removeMouseMotionListener(l);
		}
		for (MouseWheelListener l : defaultMouseWheelListeners) {
			canvas.removeMouseWheelListener(l);
		}
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
	}

	public void setMouseEnabled(boolean mouseEnabled) {
		this.mouseEnabled = mouseEnabled;
	}

	public Point getMousePoint() {
		return mousePoint;
	}

	public boolean isMouseOnCanvas() {
		return mouseOnCanvas;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (mouseEnabled || e instanceof BotEvent) {
			for (MouseListener l : defaultMouseListeners) {
				l.mouseClicked(e);
			}
			mouseOnCanvas = true;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (mouseEnabled || e instanceof BotEvent) {
			for (MouseListener l : defaultMouseListeners) {
				l.mousePressed(e);
			}
			mouseOnCanvas = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (mouseEnabled || e instanceof BotEvent) {
			for (MouseListener l : defaultMouseListeners) {
				l.mouseReleased(e);
			}
			mouseOnCanvas = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (mouseEnabled || e instanceof BotEvent) {
			for (MouseListener l : defaultMouseListeners) {
				l.mouseEntered(e);
			}
			mousePoint = e.getPoint();
			mouseOnCanvas = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (mouseEnabled || e instanceof BotEvent) {
			for (MouseListener l : defaultMouseListeners) {
				l.mouseExited(e);
			}
			mousePoint = new Point(-1, -1);
			mouseOnCanvas = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (mouseEnabled || e instanceof BotEvent) {
			for (MouseMotionListener l : defaultMouseMotionListeners) {
				l.mouseDragged(e);
			}
			mousePoint = e.getPoint();
			mouseOnCanvas = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (mouseEnabled || e instanceof BotEvent) {
			for (MouseMotionListener l : defaultMouseMotionListeners) {
				l.mouseMoved(e);
			}
			mousePoint = e.getPoint();
			mouseOnCanvas = true;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (mouseEnabled || e instanceof BotEvent) {
			for (MouseWheelListener l : defaultMouseWheelListeners) {
				l.mouseWheelMoved(e);
			}
			mouseOnCanvas = true;
		}
	}

}
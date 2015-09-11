package com.omnibot.bot.graphics;

import com.omnibot.bot.gui.BotFrame;

import javax.accessibility.Accessible;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/17/2014
 */

public class BotCanvas extends Canvas implements Accessible {

	private BufferedImage gameBuffer;
	private BufferedImage botPaintBuffer;

	public BotCanvas() {
		this.gameBuffer = new BufferedImage(765, 553, BufferedImage.TYPE_INT_ARGB);
		this.botPaintBuffer = new BufferedImage(765, 553, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public Graphics getGraphics() {
		if (BotFrame.getBot().isPaintDebugEnabled()) {
			Graphics2D g = (Graphics2D) botPaintBuffer.getGraphics();
			g.drawImage(gameBuffer, 0, 0, null);
			g.setColor(Color.GREEN);
            g.drawString("Canvas class injected!", 7, 15);
			Point p = BotFrame.getBot().getBotInputManager().getMousePosition();
			g.drawString("Mouse position: " + p, 7, 33);

			if (BotFrame.getBot().getBotInputManager().isMouseOnCanvas()) {
				g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
				g.setColor(new Color(255, 255, 255, 150));
				g.fillOval(p.x - 4, p.y - 4, 8, 8);
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.GREEN);
				g.drawOval(p.x - 4, p.y - 4, 8, 8);
			}

			g.dispose();
			super.getGraphics().drawImage(botPaintBuffer, 0, 0, null);

			return gameBuffer.getGraphics();
		}
		return super.getGraphics();
	}

}

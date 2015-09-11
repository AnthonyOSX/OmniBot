package com.omnibot;

import com.omnibot.bot.gui.BotFrame;
import com.omnibot.utils.Constants;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/13/2014
 */

public class Main {

	public static void main(String... args) {
		BotFrame botFrame = new BotFrame(Constants.BOT_NAME + " v" + Constants.VERSION);
		botFrame.setVisible(true);
	}

}
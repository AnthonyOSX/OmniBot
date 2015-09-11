package com.omnibot.bot.gui;

import com.omnibot.utils.Constants;
import com.omnibot.utils.Utilities;
import com.omnibot.bot.Bot;
import com.omnibot.bot.gui.console.BotConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/13/2014
 */

public class BotFrame extends JFrame {

	public static final BotConsole console = new BotConsole();
	public static JLabel splashScreen;

	private static Bot bot;

	private boolean loaded;
	private JButton btnAddClient;
	private JToggleButton tglDebug, tglKeyboardInput, tglMouseInput;

	public BotFrame(String name) {
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		setTitle(name);
		setPreferredSize(new Dimension(765, 690));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout(0, 0));
		setLocation(100, 100);
		setBackground(Color.BLACK);

		createToolbar();
		addListeners();

		Image image = Utilities.getInstance().loadResourceImage("/resources/splashScreen.png");
		setIconImage(image);
		addSplashScreen(new ImageIcon(image));
		add(console.createScrollPane(), "South");
		pack();

		console.log("Welcome to " + Constants.BOT_NAME + " v" + Constants.VERSION + "!");
	}

	private void createToolbar() {
		JToolBar toolBar = new JToolBar();
		btnAddClient = new JButton();
		tglDebug = new JToggleButton();
		tglMouseInput = new JToggleButton();
		tglKeyboardInput = new JToggleButton();

		btnAddClient.setIcon(new ImageIcon(Utilities.getInstance().scaleImage(16, 16, Utilities.getInstance().loadResourceImage("/resources/loadClient.png"))));
		tglDebug.setIcon(new ImageIcon(Utilities.getInstance().scaleImage(16, 16, Utilities.getInstance().loadResourceImage("/resources/debug.png"))));
		tglMouseInput.setIcon(Utilities.getInstance().loadResourceImageIcon("/resources/mouseInput.png"));
		tglKeyboardInput.setIcon(Utilities.getInstance().loadResourceImageIcon("/resources/keyboardInput.png"));

		btnAddClient.setToolTipText("Load client");

		btnAddClient.setSize(30, 30);
		tglDebug.setSize(30, 30);
		tglMouseInput.setSize(30, 30);
		tglKeyboardInput.setSize(30, 30);
		btnAddClient.setFocusable(false);
		tglDebug.setFocusable(false);
		tglMouseInput.setFocusable(false);
		tglKeyboardInput.setFocusable(false);
		tglDebug.setEnabled(false);
		tglMouseInput.setEnabled(false);
		tglKeyboardInput.setEnabled(false);
		toolBar.setFloatable(false);
		toolBar.setFocusable(false);

		toolBar.add(btnAddClient);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(tglDebug);
		toolBar.add(tglKeyboardInput);
		toolBar.add(tglMouseInput);

		add(toolBar, BorderLayout.NORTH);
	}

	private void addListeners() {
		btnAddClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!loaded) {
					addClient();
				}
				else {
					console.log(new LogRecord(Level.SEVERE, "The client is currently in the process of loading!"));
				}
			}
		});
		tglDebug.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (bot != null) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						bot.setPaintDebug(true);
						tglDebug.setToolTipText("Hide paint debug");
					}
					else {
						bot.setPaintDebug(false);
						tglDebug.setToolTipText("Show paint debug");
					}
				}
			}
		});
		tglKeyboardInput.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (bot != null) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						bot.getBotInputManager().setKeyboardEnabled(true);
						tglKeyboardInput.setToolTipText("Disable keyboard input");
					}
					else {
						bot.getBotInputManager().setKeyboardEnabled(false);
						tglKeyboardInput.setToolTipText("Enable keyboard input");
					}
				}
			}
		});
		tglMouseInput.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (bot != null) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						bot.getBotInputManager().setMouseEnabled(true);
						tglMouseInput.setToolTipText("Disable mouse input");
					}
					else {
						bot.getBotInputManager().setMouseEnabled(false);
						tglMouseInput.setToolTipText("Enable mouse input");
					}
				}
			}
		});
	}

	private void modifyFrameForClient() {
		removeSplashScreen();
		add(bot.getBotPanel(), BorderLayout.CENTER);
		pack();
		btnAddClient.setEnabled(false);
		tglDebug.setEnabled(true);
		tglDebug.setToolTipText("Show paint debug");
		tglKeyboardInput.setEnabled(true);
		tglKeyboardInput.getModel().setSelected(true);
		tglKeyboardInput.setToolTipText("Disable keyboard input");
		tglMouseInput.setEnabled(true);
		tglMouseInput.getModel().setSelected(true);
		tglMouseInput.setToolTipText("Disable mouse input");
	}

	public void addSplashScreen(ImageIcon imageIcon) {
		splashScreen = new JLabel(imageIcon, SwingConstants.CENTER);
		splashScreen.setText("Click the play button to load the client!");
		splashScreen.setHorizontalTextPosition(JLabel.CENTER);
		splashScreen.setVerticalTextPosition(JLabel.BOTTOM);
		add(splashScreen, BorderLayout.CENTER);
		pack();
	}

	public void removeSplashScreen() {
		if (splashScreen != null) {
			remove(splashScreen);
			splashScreen = null;
		}
	}

	public void addClient() {
		loaded = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				bot = new Bot("http://oldschool69.runescape.com/", 765, 553);
				modifyFrameForClient();
			}
		}).start();
	}

	public static Bot getBot() {
		return bot;
	}

}

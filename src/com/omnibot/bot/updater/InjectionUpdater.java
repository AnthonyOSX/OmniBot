package com.omnibot.bot.updater;

import com.omnibot.bot.gui.BotFrame;
import com.omnibot.bot.updater.transformers.AbstractTransformer;
import com.omnibot.bot.updater.transformers.CanvasTransformer;
import com.omnibot.bot.updater.transformers.ClientTransformer;
import com.omnibot.bot.updater.transformers.NodeTransformer;
import com.omnibot.utils.Constants;
import com.omnibot.utils.JarUtils;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.*;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/15/2014
 */

public class InjectionUpdater {

	public static final Map<String, String> identifiedClasses = new HashMap<>();

	private List<AbstractTransformer> transformers;

	public InjectionUpdater() {
		this.transformers = new ArrayList<>();
	}

	public void run() {
		try {
			JarUtils.parseJar(new JarFile(Constants.ORIGINAL_JAR_LOCATION));
			BotFrame.console.log(new LogRecord(Level.CONFIG, "Parsed " + Constants.CLASSES.size() + " classes!"));
			BotFrame.splashScreen.setText("Applying injections for revision " + getRevision() + "...");
			loadTransformers();
			runTransformers();
			JarUtils.dumpJar(Constants.MODIFIED_JAR_LOCATION);
			BotFrame.console.log(new LogRecord(Level.CONFIG, "Dumped jar file as " + Constants.MODIFIED_JAR_LOCATION + "!"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int getRevision() {
		ClassNode cnClient = Constants.CLASSES.get("client");
		ListIterator<?> li = cnClient.methods.listIterator();

		while (li.hasNext()) {
			MethodNode mn = (MethodNode) li.next();

			if (mn.name.equals("init")) {
				ListIterator<?> ainli = mn.instructions.iterator();

				while (ainli.hasNext()) {
					AbstractInsnNode ain = (AbstractInsnNode) ainli.next();

					if (ain instanceof IntInsnNode && ((IntInsnNode) ain).operand == 503) {
						return ((IntInsnNode) ainli.next()).operand;
					}
				}
			}
		}

		return -1;
	}

	private void loadTransformers() {
		transformers.add(new ClientTransformer());
		transformers.add(new CanvasTransformer());
		transformers.add(new NodeTransformer());
	}

	private void runTransformers() {
		for (AbstractTransformer t : transformers) {
			for (ClassNode cn : Constants.CLASSES.values()) {
				t.run(cn);
				if (t.isFinished()) {
					break;
				}
			}
		}
	}

}
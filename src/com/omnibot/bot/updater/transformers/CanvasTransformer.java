package com.omnibot.bot.updater.transformers;

import com.omnibot.bot.gui.BotFrame;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/17/2014
 */

public class CanvasTransformer extends AbstractTransformer {

	private boolean finished;

	@Override
	protected boolean canRun(ClassNode classNode) {
		return classNode.superName.contains("Canvas");
	}

	@Override
	protected void transform(ClassNode classNode) {
		String superName = "com/omnibot/bot/graphics/BotCanvas";
		classNode.superName = superName;
		ListIterator mli = classNode.methods.listIterator();
		search:
		while (mli.hasNext()) {
			MethodNode mn = (MethodNode) mli.next();
			if (mn.name.equals("<init>")) {
				ListIterator li = mn.instructions.iterator();
				while (li.hasNext()) {
					AbstractInsnNode ain = (AbstractInsnNode) li.next();
					if (ain.getOpcode() == Opcodes.INVOKESPECIAL) {
						MethodInsnNode min = (MethodInsnNode) ain;
						min.owner = superName;
						break search;
					}
				}
			}
		}
		BotFrame.console.log(new LogRecord(Level.CONFIG, "Injected canvas!"));
		finished = true;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}
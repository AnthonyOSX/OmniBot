package com.omnibot.bot.updater.transformers;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/15/2014
 */

public abstract class AbstractTransformer {

	public void run(ClassNode classNode) {
		if (canRun(classNode)) {
			transform(classNode);
		}
	}

	protected abstract boolean canRun(ClassNode classNode);
	protected abstract void transform(ClassNode classNode);
	public abstract boolean isFinished();

}
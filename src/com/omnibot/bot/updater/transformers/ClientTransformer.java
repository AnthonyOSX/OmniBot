package com.omnibot.bot.updater.transformers;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/21/2014
 */

public class ClientTransformer extends AbstractTransformer {

	private boolean finished;

	@Override
	protected boolean canRun(ClassNode classNode) {
		return classNode.name.equals("client");
	}

	@Override
	protected void transform(ClassNode classNode) {
		System.out.println("Identified client as: " + classNode.name);
		finished = true;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}
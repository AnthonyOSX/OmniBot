package com.omnibot.bot.updater.transformers;

import com.omnibot.bot.updater.InjectionUpdater;
import com.omnibot.utils.ASMUtils;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/17/2014
 */

public class NodeTransformer extends AbstractTransformer {

	private boolean finished;

	@Override
	protected boolean canRun(ClassNode classNode) {
		if (!classNode.superName.equals("java/lang/Object")) {
			return false;
		}
		int longTypeCount = 0;
		int selfTypeCount = 0;
		ListIterator<?> fnli = classNode.fields.listIterator();
		while (fnli.hasNext()) {
			FieldNode fn = (FieldNode) fnli.next();
			if ((fn.access & Opcodes.ACC_STATIC) == 0) {
				String desc = fn.desc;
				if (desc.equals("L" + classNode.name + ";")) {
					selfTypeCount++;
				}
				else if (desc.equals("J")) {
					longTypeCount++;
				}
			}
		}
		return longTypeCount == 1 && selfTypeCount == 2;
	}

	@Override
	protected void transform(ClassNode classNode) {
		InjectionUpdater.identifiedClasses.put("Node", classNode.name);
		hookId(classNode);
		hookNextAndPrevious(classNode);
		finished = true;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	private void hookId(ClassNode classNode) {
		ListIterator<?> li = classNode.fields.listIterator();
		while (li.hasNext()) {
			FieldNode fn = (FieldNode) li.next();
			if ((fn.access & Opcodes.ACC_STATIC) == 0 && fn.desc.equals("J")) {
				ASMUtils.createGetter(classNode, fn, "getId", "J");
				System.out.println("Hooked ID!");
				return;
			}
		}
	}

	private void hookNextAndPrevious(ClassNode classNode) {
		int count = 0;
		ListIterator<?> li = classNode.methods.listIterator();
		while (li.hasNext()) {
			MethodNode mn = (MethodNode) li.next();
			if (!mn.name.equals("<init>") && (mn.access & Opcodes.ACC_STATIC) == 0 && mn.desc.equals("V")) {
				ListIterator<?> ili = mn.instructions.iterator();
				while (ili.hasNext()) {
					AbstractInsnNode ain = (AbstractInsnNode) ili.next();
					if (ain.getOpcode() == Opcodes.GETFIELD) {
						FieldNode fn = ASMUtils.getField(classNode, ((FieldInsnNode) ain).name);
						switch (count++) {
							case 0:
								ASMUtils.createGetter(classNode, fn, "getNext", "Lcom/omnibot/bot/hooks/Node");
								System.out.println("Hooked next!");
								break;
							case 2:
								ASMUtils.createGetter(classNode, fn, "getPrevious", "Lcom/omnibot/bot/hooks/Node");
								System.out.println("Hooked previous!");
								return;
						}
					}
				}
			}
		}
	}

}
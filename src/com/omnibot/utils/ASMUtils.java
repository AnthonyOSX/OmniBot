package com.omnibot.utils;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/17/2014
 */

public class ASMUtils {

	public static FieldNode getField(ClassNode classNode, String name) {
		ListIterator<?> li = classNode.fields.listIterator();

		while (li.hasNext()) {
			FieldNode fn = (FieldNode) li.next();
			if (fn.name.equals(name)) {
				return fn;
			}
		}

		return null;
	}

	public static void createGetter(ClassNode classNode, FieldNode fieldNode, String name, String desc) {
		MethodNode mn = new MethodNode(Opcodes.ACC_PUBLIC, name, "()" + desc, null, null);

		mn.visitVarInsn(Opcodes.ALOAD, 0);
		mn.visitFieldInsn(Opcodes.GETFIELD, classNode.name, fieldNode.name, fieldNode.desc);
		mn.visitInsn(Type.getType(fieldNode.desc).getOpcode(Opcodes.IRETURN));
		mn.visitMaxs(0, 0);
		classNode.methods.add(mn);
	}

}

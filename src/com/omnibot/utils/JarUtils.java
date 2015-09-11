package com.omnibot.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/15/2014
 */

public class JarUtils {

	public static void parseJar(JarFile jar) {
		try {
			Enumeration<JarEntry> enumeration = jar.entries();
			while (enumeration.hasMoreElements()) {
				JarEntry je = enumeration.nextElement();
				if (je.getName().endsWith(".class")) {
					ClassReader classReader = new ClassReader(jar.getInputStream(je));
					ClassNode classNode = new ClassNode();
					//accept makes the given visitor visit the Java class of the ClassReader
					classReader.accept(classNode, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
					Constants.CLASSES.put(classNode.name, classNode);
				}
			}
			jar.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void dumpJar(String jarName) {
		JarOutputStream jos = null;

		try {
			jos = new JarOutputStream(new FileOutputStream(new File(jarName)));

			for (ClassNode cn : Constants.CLASSES.values()) {
				jos.putNextEntry(new JarEntry(cn.name + ".class"));
				ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				cn.accept(writer);
				jos.write(writer.toByteArray());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (jos != null) {
				try {
					jos.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
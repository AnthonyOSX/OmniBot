package com.omnibot.utils;

import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 7/15/2014
 */

public class Constants {

	public static final double VERSION = 0.1;

	public static final String BOT_NAME = "OmniBot";
	public static final String ORIGINAL_JAR_LOCATION = "gamepack.jar";
	public static final String MODIFIED_JAR_LOCATION = "modifiedGamepack.jar";

	public static final Map<String, ClassNode> CLASSES = new HashMap<>();

}
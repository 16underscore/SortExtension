package me.sixteen_.sort_extension.sort_impl;

import org.lwjgl.glfw.GLFW;

import me.sixteen_.sort.api.IConfig;

public class Config implements IConfig {

	@Override
	public int getKeycode() {
		return GLFW.GLFW_KEY_R;
	}
}
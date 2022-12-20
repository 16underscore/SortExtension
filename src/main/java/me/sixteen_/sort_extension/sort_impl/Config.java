package me.sixteen_.sort_extension.sort_impl;

import org.lwjgl.glfw.GLFW;

import me.sixteen_.sort.api.IConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;

public class Config implements IConfig {

	private KeyBinding keyBinding;

	public Config() {
		keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.sort_extension.sort",
				GLFW.GLFW_KEY_R,
				"category.sort_extension.sort"));
	}

	@Override
	public int getKeycode() {
		return InputUtil.fromTranslationKey(keyBinding.getBoundKeyTranslationKey()).getCode();
	}

	public void setKey(Key key) {
		keyBinding.setBoundKey(key);
	}
}
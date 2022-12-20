package me.sixteen_.sort_extension.sort_impl;

import me.sixteen_.sort.api.IOrder;
import net.minecraft.item.Item;
import net.minecraft.screen.slot.Slot;

public class Order implements IOrder {

	private String order = "";

	@Override
	public int getOrder(Slot slot) {
		return switch (order) {
			case "alphabetic" -> alphabetic(slot);
			case "creative" -> creative(slot);
			default -> id(slot);
		};
	}

	public void setOrder(String order) {
		this.order = order;
	}

	private int alphabetic(Slot slot) {
		if (slot.getStack().isEmpty()) {
			return Integer.MAX_VALUE;
		}
		byte[] name = slot.getStack().getName().getString().getBytes();
		StringBuilder builder = new StringBuilder();
		for (byte letter : name) {
			builder.append((int) letter);
		}
		return Integer.parseInt(builder.toString());
	}

	private int creative(Slot slot) {
		return 0;
	}

	private int id(Slot slot) {
		return slot.getStack().isEmpty() ? Integer.MAX_VALUE : Item.getRawId(slot.getStack().getItem());
	}
}
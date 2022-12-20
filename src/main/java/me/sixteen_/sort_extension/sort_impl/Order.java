package me.sixteen_.sort_extension.sort_impl;

import me.sixteen_.sort.api.IOrder;
import net.minecraft.item.Item;
import net.minecraft.screen.slot.Slot;

public class Order implements IOrder {

	private String order = "";

	@Override
	public int getOrder(Slot slot) {
		return switch (order) {
			default -> id(slot);
		};
	}

	public void setOrder(String order) {
		this.order = order;
	}

	private int id(Slot slot) {
		return checkOrReturn(slot, Item.getRawId(slot.getStack().getItem()));
	}

	private int checkOrReturn(Slot slot, int order) {
		return slot.getStack().isEmpty() ? Integer.MAX_VALUE : order;
	}
}
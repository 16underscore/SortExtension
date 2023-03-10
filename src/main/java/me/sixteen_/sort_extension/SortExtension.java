package me.sixteen_.sort_extension;

import me.sixteen_.sort.Sort;
import me.sixteen_.sort.api.SortClientModInitializer;
import me.sixteen_.sort_extension.sort_impl.Config;
import me.sixteen_.sort_extension.sort_impl.Order;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class SortExtension implements SortClientModInitializer {

	private static SortExtension instance;
	private Config config;
	private Order order;

	@Override
	public void onInitializeSortClient() {
		instance = this;
		config = new Config();
		order = new Order();
		ClientCommandRegistrationCallback.EVENT.register(SortCommand::register);
		Sort.setConfig(config);
		Sort.setOrder(order);
	}

	public static SortExtension getInstance() {
		return instance;
	}

	public Config getConfig() {
		return config;
	}

	public Order getOrder() {
		return order;
	}
}
package me.sixteen_.sort_extension;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;
import net.minecraft.command.CommandRegistryAccess;

public class SortCommand {

	private static Set<String> orders = new HashSet<>();
	private static Set<String> keys = new HashSet<>();

	static {
		orders.addAll(List.of("creative", "id"));
		for (char i = 'a'; i < 'z'; i++) {
			keys.add(String.valueOf(i));
		}
	}

	public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
			CommandRegistryAccess registryAccess) {
		dispatcher.register(literal("sort")
				.then(literal("bind")
						.then(argument("key", SortCommand::parseKeys).suggests(SortCommand::suggestKeys)
								.executes(SortCommand::bind)))
				.then(literal("order")
						.then(argument("order", SortCommand::parseOrder).suggests(SortCommand::suggestOrder)
								.executes(SortCommand::order))));
	}

	private static String parseKeys(StringReader reader) throws CommandSyntaxException {
		String keyString = boilerplate(reader);
		if (keys.contains(keyString.toLowerCase())) {
			return keyString;
		}
		throw new SimpleCommandExceptionType(() -> "a-z are valid keybinds").create();
	}

	private static String parseOrder(StringReader reader) throws CommandSyntaxException {
		String orderString = boilerplate(reader);
		if (orders.contains(orderString.toLowerCase())) {
			return orderString;
		}
		throw new SimpleCommandExceptionType(() -> "this order does not exist!").create();
	}

	private static CompletableFuture<Suggestions> suggestKeys(CommandContext<FabricClientCommandSource> context,
			SuggestionsBuilder builder)
			throws CommandSyntaxException {
		for (String key : keys) {
			builder.suggest(key);
		}
		return builder.buildFuture();
	}

	private static CompletableFuture<Suggestions> suggestOrder(CommandContext<FabricClientCommandSource> context,
			SuggestionsBuilder builder)
			throws CommandSyntaxException {
		for (String order : orders) {
			builder.suggest(order);
		}
		return builder.buildFuture();
	}

	private static int bind(CommandContext<FabricClientCommandSource> context) {
		String letter = context.getArgument("key", String.class);
		Key key = InputUtil.Type.KEYSYM.createFromCode(letter.toUpperCase().charAt(0));
		SortExtension.getInstance().getConfig().setKey(key);
		return 0;
	}

	private static int order(CommandContext<FabricClientCommandSource> context) {
		String order = context.getArgument("order", String.class);
		SortExtension.getInstance().getOrder().setOrder(order);
		return 0;
	}

	private static String boilerplate(StringReader reader) {
		int argBeginning = reader.getCursor();
		if (!reader.canRead()) {
			reader.skip();
		}
		while (reader.canRead() && reader.peek() != ' ') {
			reader.skip();
		}
		return reader.getString().substring(argBeginning, reader.getCursor());
	}
}
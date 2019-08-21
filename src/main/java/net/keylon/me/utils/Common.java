package net.keylon.me.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import net.keylon.me.commands.PlayerCommand;

public class Common {
	
	public static void tell(CommandSender toWhom, String... messages) {
		for (String message : messages)
			tell(toWhom, message);
	}
	
	public static void tell(CommandSender toWhom, String message) {
		toWhom.sendMessage(colorize(message));
	}
	
	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static void registerCommand(PlayerCommand command) {
		try {
			final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);
			
			final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
			commandMap.register(command.getLabel(), command);
			
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

	public static void tellList(CommandSender toWhom, List<String> stringList) {
		for (String message : stringList)
			tell(toWhom, message);
		
	}

}

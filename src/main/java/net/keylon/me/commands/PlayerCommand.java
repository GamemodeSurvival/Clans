package net.keylon.me.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.keylon.me.utils.Common;

public abstract class PlayerCommand extends Command {
	
	protected PlayerCommand(String name) {
		super(name);
	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {

			Common.tell(sender, "&cMust be a player to use this command!");

			return false;
		}
		run((Player) sender, args);
		return true;
	}
	
	protected abstract void run(Player player, String[] args);

}

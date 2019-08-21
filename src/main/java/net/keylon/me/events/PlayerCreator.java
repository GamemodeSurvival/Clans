package net.keylon.me.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.keylon.me.utils.PlayerData;

public class PlayerCreator implements Listener {
	
	@SuppressWarnings("unused")
	@EventHandler
	public void setupPlayer(PlayerJoinEvent e) {	
		Player player = e.getPlayer();
		PlayerData pl = new PlayerData(player);
	}

}

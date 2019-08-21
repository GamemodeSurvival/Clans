package net.keylon.me.pointsmanager;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import net.keylon.me.utils.PlayerData;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class HitsToNexus implements Listener {

	@EventHandler
	public void onNexusHit(BlockBreakEvent e) {
		if (e.getBlock().getType().equals(Material.END_STONE)) {
			Player p = e.getPlayer();
			PlayerData dt = new PlayerData(p);
			String clan = dt.getClan();
			if (clan != "None") {
				File file = new File("plugins//ClanSystem//Clans//" + clan.toLowerCase() + ".yml");
				YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
				int current = inv.getInt("Clan.Stats.hitsToNexus");
				current++;
				inv.set("Clan.Stats.hitsToNexus", current);
			}

		}
	}

}

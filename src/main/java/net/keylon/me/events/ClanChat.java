package net.keylon.me.events;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.keylon.me.Core;
import net.keylon.me.config.SimpleConfig;
import net.keylon.me.utils.Common;

public class ClanChat {

	static SimpleConfig cfg = new SimpleConfig("config.yml");

	public static void clanChat(Player player, String clan, String[] args) {
		File file = new File("plugins//ClanSystem//Clans//" + clan.toLowerCase() + ".yml");
		YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
		String msg = "";
		for (int i = 1; i < args.length; i++)
			msg += args[i] + " ";
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		String messageToSend = Common.colorize("&8[&a" + inv.getString("Clan.Name") + "&8] &7" + msg);
		for (String name : inv.getStringList("Clan.Members")) {
			try {
				out.writeUTF("Message");
				out.writeUTF(name);
				out.writeUTF(messageToSend);
			} catch (IOException e) {
				e.printStackTrace();
			}

			player.sendPluginMessage(Core.getInstance(), "BungeeCord", b.toByteArray());
		}
	}

}

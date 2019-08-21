package net.keylon.me.utils;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import net.keylon.me.Core;
import net.keylon.me.config.SimplePlayerFile;

public class PlayerData {

	private UUID uuid;
	private Player player;
	Boolean clanchat;
	Integer kills;
	Integer deaths;
	SimplePlayerFile dataF;

	public PlayerData(UUID uuid) {
		this.uuid = uuid;
		this.player = Bukkit.getPlayer(uuid);
		dataF = new SimplePlayerFile(uuid.toString() + ".yml");
		reloadData();
	}

	public PlayerData(Player player) {
		this.uuid = player.getUniqueId();
		this.player = player;
		dataF = new SimplePlayerFile(uuid.toString() + ".yml");
		reloadData();
	}

	public UUID getUUID() {
		return uuid;
	}

	public Boolean getLeader() {
		return dataF.getConfig().getBoolean("data.clanleader");
	}

	public String getClan() {
		return dataF.getConfig().getString("data.clan");
	}

	public Boolean getClanChat() {
		return dataF.getConfig().getBoolean("data.clanchat");
	}

	public void setClan(String clan) {
		dataF.getConfig().set("data.clan", clan);
		dataF.saveFile();
	}

	public void setClanLeader(Boolean isLeader) {
		dataF.getConfig().set("data.clanleader", isLeader);
		dataF.saveFile();
	}

	public void setClanChat(Boolean isEnabled) {
		dataF.getConfig().set("data.clanchat", isEnabled);
		dataF.saveFile();
	}

	public void reloadData() {
		File dataFolder = new File(Core.getInstance().getDataFolder() + File.separator + "players" + File.separator);
		dataFolder.mkdirs();
		SimplePlayerFile dataF = new SimplePlayerFile(uuid.toString() + ".yml");
		if (!dataF.getConfig().isSet("data.clan")) {
			dataF.getConfig().set("data.clan", "None");
		}
		if (!dataF.getConfig().isSet("data.clanchat")) {
			dataF.getConfig().set("data.clanchat", false);
		}
		if (dataF.getConfig().isSet("data.kills")) {
			this.kills = dataF.getConfig().getInt("data.kills");
		} else {
			this.kills = 0;
			dataF.getConfig().set("data.kills", player.getStatistic(Statistic.PLAYER_KILLS));
		}
		if (dataF.getConfig().isSet("data.deaths")) {
			this.deaths = dataF.getConfig().getInt("data.deaths");
		} else {
			this.deaths = 0;
			dataF.getConfig().set("data.deaths", player.getStatistic(Statistic.DEATHS));
		}

		dataF.saveFile();

	}

}

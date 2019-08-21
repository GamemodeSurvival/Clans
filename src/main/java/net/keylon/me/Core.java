package net.keylon.me;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.keylon.me.utils.PlayerData;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class Core extends Plugin {

	private PlayerData playerdata;

	public static Core instance;

	@Override
	public void onEnable() {

		instance = this;
		System.out.println("ClanSystem enabled for Minecub ~ made by Loosened");

		this.getProxy().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getProxy().getChannels().registerIncomingPluginChannel(this, "BungeeCord", this);

	}

	public void onPluginMessageReceived1(String channel, ProxiedPlayer player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("clanChat")) {
		}
	}

	public Core() {
		instance = this;
	}

	public static Core getInstance() {
		return instance;
	}

	public PlayerData getPlayerData() {
		return playerdata;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	}

}

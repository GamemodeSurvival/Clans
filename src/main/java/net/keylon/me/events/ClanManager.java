package net.keylon.me.events;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.keylon.me.config.SimpleConfig;
import net.keylon.me.utils.Common;
import net.keylon.me.utils.PlayerData;

public class ClanManager {

	static SimpleConfig cfg = new SimpleConfig("clans.yml");
	static SimpleConfig uri = new SimpleConfig("config.yml");
	static SimpleConfig messages = new SimpleConfig("messages.yml");

	public static void createClan(Player p, String clan) {
		PlayerData dt = new PlayerData(p);
		if (dt.getClan().equalsIgnoreCase("None")) {
			ArrayList<String> list = new ArrayList<>();
			String playername = p.getName();
			File file = new File("plugins//ClanSystem//Clans//" + clan.toLowerCase() + ".yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
					Common.tell(p, messages.getString("PREFIX")
							+ messages.getString("CLAN_CREATE").replaceAll("{clan}", clan));
				} catch (IOException e) {
					Common.tell(p, messages.getString("PREFIX")
							+ messages.getString("CLAN_NAME_INUSE").replaceAll("{clan}", clan));
				}
				list.add(playername);
				YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
				inv.set("Clan.Name", clan);
				inv.set("Clan.Leader", playername);
				inv.set("Clan.Stats.wins", 0);
				inv.set("Clan.Stats.loss", 0);
				inv.set("Clan.Stats.objectivesCompleted", 0);
				inv.set("Clan.Stats.hitsToNexus", 0);
				inv.set("Clan.Settings.open", false);
				inv.set("Clan.Settings.isOnCooldown", false);
				inv.set("Clan.Members", list);
				cfg.set(playername, clan.toUpperCase());
				dt.setClan(clan);
				dt.setClanChat(false);
				dt.setClanLeader(true);
				try {
					inv.save(file);
					cfg.saveConfig();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			Common.tell(p, messages.getString("PREFIX") + messages.getString("IN_CLAN").replaceAll("{clan}", clan));
			return;
		}
	}

	public static void addMember(Player p, String[] args, String clan) {
		Player playerToAdd = Bukkit.getServer().getPlayer(args[0]);
		PlayerData dtr = new PlayerData(p);
		PlayerData dt = new PlayerData(playerToAdd);
		if (dtr.getClan().equalsIgnoreCase("None")) {
			Common.tell(p, messages.getString("PREFIX") + messages.getString("NOT_IN_CLAN").replaceAll("{clan}", clan));
			return;
		}
		if (dt.getClan().equalsIgnoreCase("None")) {
			String clanname = "";
			if (!dtr.getLeader()) {
				clanname = dtr.getClan();
			} else {
				Common.tell(p,
						messages.getString("PREFIX") + messages.getString("NOT_LEADER").replaceAll("{clan}", clan));
				return;
			}
			if (dtr.getClan().equals(dt.getClan())) {
				Common.tell(p,
						messages.getString("PREFIX") + messages.getString("USER_IN_CLAN").replaceAll("{clan}", clan));
				return;
			}
			File file = new File("plugins//ClanSystem//Clans//" + clanname.toLowerCase() + ".yml");
			if (file.exists()) {
				YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
				List<String> list = inv.getStringList("Clan.Members");
				if (list.size() >= uri.getInt("Max-Members")) {
					Common.tell(p, messages.getString("PREFIX")
							+ messages.getString("MEMBER_LIMIT_REACHED").replaceAll("{clan}", clan));
					return;
				} else {
					String member = args[0];
					list.add(member);
					inv.set("Clan.Members", list);
					dt.setClan(clan);
					dt.setClanChat(false);
					dt.setClanLeader(false);
				}
			}

		} else {
			Common.tell(p,
					messages.getString("PREFIX") + messages.getString("PLAYER_IN_CLAN").replaceAll("{clan}", clan));
		}

	}

	public static void removeMember(Player p, String[] args, String clan) {
		Player playerToAdd = Bukkit.getServer().getPlayer(args[0]);
		PlayerData dtr = new PlayerData(p);
		PlayerData dt = new PlayerData(playerToAdd);
		if (!dtr.getClan().equalsIgnoreCase(clan)) {
			Common.tell(p, messages.getString("PREFIX") + messages.getString("NOT_IN_CLAN").replaceAll("{clan}", clan));
			return;
		}
		if (dt.getClan().equalsIgnoreCase(clan)) {
			String clanname = "";
			if (!dtr.getLeader()) {
				clanname = dtr.getClan();
			} else {
				Common.tell(p,
						messages.getString("PREFIX") + messages.getString("NOT_LEADER").replaceAll("{clan}", clan));
				return;
			}
			File file = new File("plugins//ClanSystem//Clans//" + clanname.toLowerCase() + ".yml");
			if (file.exists()) {
				YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
				List<String> list = inv.getStringList("Clan.Members");

				String member = args[0];
				list.remove(member);
				inv.set("Clan.Members", list);
				dt.setClan("None");
				dt.setClanChat(false);
				dt.setClanLeader(false);

			}

		} else {
			Common.tell(p,
					messages.getString("PREFIX") + messages.getString("NOT_IN_YOUR_CLAN").replaceAll("{clan}", clan));
		}

	}

	public static void deleteClan(Player p, String clan) {
		PlayerData dtr = new PlayerData(p);
		if (dtr.getClan().equalsIgnoreCase("None")) {
			Common.tell(p, messages.getString("PREFIX") + messages.getString("NOT_IN_CLAN").replaceAll("{clan}", clan));
			return;
		}
		String clanname = "";
		if (!dtr.getLeader()) {
			clanname = dtr.getClan();
		} else {
			Common.tell(p, messages.getString("PREFIX") + messages.getString("NOT_LEADER").replaceAll("{clan}", clan));
			return;
		}
		File file = new File("plugins//ClanSystem//Clans//" + clanname.toLowerCase() + ".yml");
		if (file.exists()) {
			YamlConfiguration inv = YamlConfiguration.loadConfiguration(file);
			for (String player : inv.getStringList("Clan.Members")) {
				Player member = Bukkit.getServer().getPlayer(player);
				PlayerData dt = new PlayerData(member);
				dt.setClan("None");
				dt.setClanChat(false);
				dt.setClanLeader(false);
			}
			Common.tell(p, messages.getString("PREFIX") + messages.getString("CLAN_DELETE").replaceAll("{clan}", clan));

		} else {
			Common.tell(p, messages.getString("PREFIX") + messages.getString("ERROR"));
		}

	}

}

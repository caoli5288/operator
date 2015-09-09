package com.mengcraft.operator;

import static org.bukkit.GameMode.SURVIVAL;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		String[] strings = {
                ChatColor.GREEN + "梦梦家高性能服务器出租店",
                ChatColor.GREEN + "shop105595113.taobao.com"
        };
        getServer().getConsoleSender().sendMessage(strings);
	}

	@EventHandler
	public void handle(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if (p.isOp()) {
			p.setOp(false);
			if (p.getGameMode() != SURVIVAL) {
				p.setGameMode(SURVIVAL);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof ConsoleCommandSender || args.length < 1) {
			return false;
		}
		String name = sender.getName();
		String pass = args[0];
		String secu = getConfig().getString(name, null);
		if (pass.equals(secu)) {
			sender.setOp(true);
			sender.sendMessage(ChatColor.GREEN + "OK!");
		}
		return false;
	}

}

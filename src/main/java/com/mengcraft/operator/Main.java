package com.mengcraft.operator;

import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.i5mc.OpEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.GameMode.SURVIVAL;

public class Main extends JavaPlugin implements Listener {

    private List<String> list;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        list = new ArrayList<>();
        getServer().getPluginManager().registerEvents(this, this);
        String[] i = {
                ChatColor.GREEN + "梦梦家高性能服务器出租店",
                ChatColor.GREEN + "shop105595113.taobao.com"
        };
        getServer().getConsoleSender().sendMessage(i);

        Package pkg = Package.getPackage("org.bukkit.event.i5mc");
        if (pkg == null) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "该服务端不支持高级权限控制");
        } else {
            EventExecutor ex = (th, event) -> {
                val e = (OpEvent) event;
                if (e.isOp() && !list.contains(e.getWho().getName())) {
                    run(10, () -> e.getWho().setOp(false));
                }
            };
            val l = new RegisteredListener(this, ex, EventPriority.MONITOR, this, true);
            OpEvent.getHandlerList().register(l);
        }
    }

    private void run(int i, Runnable r) {
        getServer().getScheduler().runTaskLater(this, r, i);
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
    public boolean onCommand(CommandSender sender, Command l, String label, String[] i) {
        if (sender.isOp() || i.length < 1) {
            return false;
        }
        String name = sender.getName();
        String input = i[0];
        String pw = getConfig().getString(name, null);
        if (input.equals(pw)) {
            list.add(sender.getName());
            sender.setOp(true);
            sender.sendMessage(ChatColor.GREEN + "OK!");
        }
        return false;
    }

}

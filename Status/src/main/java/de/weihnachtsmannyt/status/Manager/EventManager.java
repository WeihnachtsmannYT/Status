package de.weihnachtsmannyt.status.Manager;

import de.weihnachtsmannyt.status.Status;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import static de.weihnachtsmannyt.status.Manager.PrefixManager.*;

public class EventManager implements Listener {

    private String JoinMessage;
    private String LeaveMassage;

    public EventManager(Plugin plugin){
        this.JoinMessage = plugin.getConfig().getString("JoinMessage");
        this.LeaveMassage = plugin.getConfig().getString("LeaveMassage");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();
        Player p = event.getPlayer();
        PrefixManager.setScoreboard();
        for (Player all : Bukkit.getOnlinePlayers()){
            all.setScoreboard(PrefixManager.getScoreboard());
        }

        if (statusData.getString(p.getName()) == null) {
            FileManager.savePlayer(p, "Default", "§f");
            PrefixManager.getScoreboard().getTeam(Spieler).addPlayer(p);
        }

        for (Player all : Bukkit.getOnlinePlayers()){
            if (statusData.getString(all.getName()+".status").equals("Default")){
                PrefixManager.getScoreboard().getTeam(Spieler).addPlayer(all);
            }else {
                PrefixManager.createandsetteam(all,statusData.getString(all.getName()+".status"),statusData.getString(all.getName()+".color"));
            }
        }
        if (statusData.getString(p.getName()+".status").equals("Default")) {
            event.setJoinMessage(JoinMessage + " §f[" + statusData.getString(p.getName() + ".color") + "Spieler" + "§f] " + p.getName());
        }else {
            event.setJoinMessage(JoinMessage + " §f[" + statusData.getString(p.getName() + ".color") + statusData.getString(p.getName()+".status") + "§f] " + p.getName());
        }
        p.setScoreboard(PrefixManager.getScoreboard());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();
        Player p = e.getPlayer();
        if (statusData.getString(p.getName()+".status").equals("Default")) {
            e.setFormat("<"+p.getScoreboard().getTeam(Spieler).getPrefix() + p.getDisplayName() + "§f> §r" + e.getMessage());
        }else {
            e.setFormat("<"+p.getScoreboard().getTeam(getTeamByPlayer(p)).getPrefix() + p.getDisplayName() + "§f> §r" + e.getMessage());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();
        Player p = event.getPlayer();
        if (statusData.getString(p.getName()+".status").equals("Default")) {
            event.setQuitMessage(LeaveMassage + " §f[" + statusData.getString(p.getName() + ".color") + "Spieler" + "§f] " + p.getName());
        }else {
            event.setQuitMessage(LeaveMassage + " §f[" + statusData.getString(p.getName() + ".color") + statusData.getString(p.getName()+".status") + "§f] " + p.getName());
        }
    }
}

package de.weihnachtsmannyt.status.Manager;

import de.weihnachtsmannyt.status.Status;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

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

        statusData.set(event.getPlayer().getUniqueId()+".afk",false);

        Player p = event.getPlayer();
        PrefixManager.setScoreboard();
        for (Player all : Bukkit.getOnlinePlayers()){
            all.setScoreboard(PrefixManager.getScoreboard());
        }

        if (!FileManager.playerIsRegistered(event.getPlayer())) {
            FileManager.savePlayerInStatus(p, "Default", "§f");
            PrefixManager.getScoreboard().getTeam(Spieler).addPlayer(p);
        }

        for (Player all : Bukkit.getOnlinePlayers()){
            if (Objects.equals(statusData.getString(all.getUniqueId() + ".status"), "Default")){
                PrefixManager.getScoreboard().getTeam(Spieler).addPlayer(all);
            }else {
                PrefixManager.createandsetteam(all);
            }
        }
        if (Objects.equals(statusData.getString(p.getUniqueId() + ".status"), "Default")) {
            event.setJoinMessage(JoinMessage + " §f[" + statusData.getString(p.getUniqueId() + ".color") + "Spieler" + "§f] " + statusData.getString(p.getUniqueId() + ".player"));
        }else {
            event.setJoinMessage(JoinMessage + " §f[" + statusData.getString(p.getUniqueId() + ".color") + ChatColor.translateAlternateColorCodes('&', statusData.getString(p.getUniqueId()+".status")) + "§f] " + statusData.getString(p.getUniqueId() + ".player"));
        }
        p.setScoreboard(PrefixManager.getScoreboard());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();

        statusData.set(e.getPlayer().getUniqueId() + ".afk",false);

        Player p = e.getPlayer();
        final String m = e.getMessage().trim();
        final String message = ChatColor.translateAlternateColorCodes('&',e.getMessage());
        float uppercaseLetter = 0;
        for (int i =0; i < m.length();i++){
            if (Character.isUpperCase(m.charAt(i)) && Character.isLetter(m.charAt(i))){
                uppercaseLetter++;
            }
        }
        if (FileManager.StringIsBlocked(e.getMessage())) {
            e.setCancelled(true);
            p.sendMessage(Status.getInstance().getStatus_Prefix()+"§7Diese Nachricht enthält §9blockierte §7Wörter!");
        }else if (uppercaseLetter / (float) m.length() > 0.3 && m.length() > 5){
            e.setCancelled(true);
            p.sendMessage(Status.getInstance().getStatus_Prefix()+"§9Bitte benutze nicht so viele Großbuchstaben!");
        }else {
            if (statusData.getString(p.getUniqueId()+".status").equals("Default")) {
                e.setFormat(p.getScoreboard().getTeam(Spieler).getPrefix() + p.getDisplayName() + "§f: §r" + message);
            }else {
                e.setFormat(p.getScoreboard().getTeam(getTeamByPlayer(p)).getPrefix() + p.getDisplayName() + "§f: §r" + message);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();

        statusData.set(event.getPlayer().getUniqueId()+".afk",false);

        Player p = event.getPlayer();
        if (statusData.getString(p.getUniqueId()+".status").equals("Default")) {
            event.setQuitMessage(LeaveMassage + " §f[" + statusData.getString(p.getUniqueId() + ".color") + "Spieler" + "§f] " + statusData.getString(p.getUniqueId() + ".player"));
        }else {
            event.setQuitMessage(LeaveMassage + " §f[" + statusData.getString(p.getUniqueId() + ".color") + ChatColor.translateAlternateColorCodes('&', statusData.getString(p.getUniqueId()+".status")) + "§f] " + statusData.getString(p.getUniqueId() + ".player"));
        }
    }

    @EventHandler
    public void onAFK(PlayerMoveEvent event){

    }
}

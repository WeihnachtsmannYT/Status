package de.weihnachtsmannyt.status.Manager;

import de.weihnachtsmannyt.status.Status;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class PrefixManager {

    private static Scoreboard scoreboard;

    static String Spieler = "001Spieler";

    public static void setScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        scoreboard.registerNewTeam(Spieler);

        scoreboard.getTeam(Spieler).setPrefix("§f[Spieler] §f");
    }

    public static void createandsetteam(Player player){
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();
        String team = "001"+player;

        if (player.isOnline()){
            try {
                scoreboard.registerNewTeam(team);
            }catch (Exception e){}

            if (statusData.getString(player.getUniqueId()+".status").equals("Default")){
                scoreboard.getTeam(team).setPrefix("§f["+ "Spieler" + "§f] §f");
            }else {
                scoreboard.getTeam(team).setPrefix("§f[" + statusData.getString(player.getUniqueId()+".color") + ChatColor.translateAlternateColorCodes('&', statusData.getString(player.getUniqueId()+".status"))  + "§f] §f");
            }

            try {
                scoreboard.getTeam(team).addPlayer(player);
            }catch (Exception e){}

            player.setScoreboard(scoreboard);
        }
    }

    public static String getTeamByPlayer(Player player){
        return "001"+player;
    }

    public static Boolean isColorAColor(String Color){
        switch (Color.toLowerCase()) {
            case "§" + "0":
            case "§" + "1":
            case "§" + "2":
            case "§" + "3":
            case "§" + "4":
            case "§" + "5":
            case "§" + "6":
            case "§" + "7":
            case "§" + "8":
            case "§" + "9":
            case "§" + "a":
            case "§" + "b":
            case "§" + "c":
            case "§" + "d":
            case "§" + "e":
            case "§" + "f":

            case "black":
            case "dark_blue":
            case "dark_green":
            case "dark_aqua":
            case "dark_red":
            case "dark_purple":
            case "gold":
            case "gray":
            case "dark_gray":
            case "blue":
            case "green":
            case "aqua":
            case "red":
            case "light_purple":
            case "yellow":
            case "white":
                return true;
            default:
                return false;
        }
    }

    public static String getColorFromRaw(String raw){
        String color = "";
        switch (raw.toLowerCase()){
            case "§0":
                color = "Black";
                break;
            case "§1":
                color = "Dark Blue";
                break;
            case "§2":
                color = "Dark Green";
                break;
            case "§3":
                color = "Dark Aqua";
                break;
            case "§4":
                color = "Dark Red";
                break;
            case "§5":
                color = "Dark Purple";
                break;
            case "§6":
                color = "Gold";
                break;
            case "§7":
                color = "Gray";
                break;
            case "§8":
                color = "Dark Gray";
                break;
            case "§9":
                color = "Blue";
                break;
            case "§a":
                color = "Green";
                break;
            case "§b":
                color = "Aqua";
                break;
            case "§c":
                color = "Red";
                break;
            case "§d":
                color = "Light Purple";
                break;
            case "§e":
                color = "Yellow";
                break;
            case "§f":
                color = "White";
                break;
            default:
                color = "default";
                break;
        }
        return color;
    }

    public static String getRawFromColor(String Color){
        String raw = "";
        switch (Color.toLowerCase()){
            case "black":
                raw = "§0";
                break;
            case "dark_blue":
                raw = "§1";
                break;
            case "dark_green":
                raw = "§2";
                break;
            case "dark_aqua":
                raw = "§3";
                break;
            case "dark_red":
                raw = "§4";
                break;
            case "dark_purple":
                raw = "§5";
                break;
            case "gold":
                raw = "§6";
                break;
            case "gray":
                raw = "§7";
                break;
            case "dark_gray":
                raw = "§8";
                break;
            case "blue":
                raw = "§9";
                break;
            case "green":
                raw = "§a";
                break;
            case "aqua":
                raw = "§b";
                break;
            case "red":
                raw = "§c";
                break;
            case "light_purple":
                raw = "§d";
                break;
            case "yellow":
                raw = "§e";
                break;
            case "white":
                raw = "§f";
                break;
            default:
                raw = Color;
                break;
        }
        return raw;
    }

    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static String getSpieler() {
        return Spieler;
    }
}

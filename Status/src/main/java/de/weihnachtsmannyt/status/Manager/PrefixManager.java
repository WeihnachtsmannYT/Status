package de.weihnachtsmannyt.status.Manager;

import org.bukkit.Bukkit;
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

    public static void createandsetteam(Player player,String status,String color){
        String team = "001"+player;
        try {
            scoreboard.registerNewTeam(team);
        }catch (Exception e){}

        scoreboard.getTeam(team).setPrefix("§f["+color+status+"§f] §f");
        scoreboard.getTeam(team).addPlayer(player);

        player.setScoreboard(scoreboard);
    }

    public static String getTeamByPlayer(Player player){
        return "001"+player;
    }

    public static String getcolorfromraw(String raw){
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

    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static String getSpieler() {
        return Spieler;
    }
}

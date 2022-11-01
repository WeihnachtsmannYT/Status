package de.weihnachtsmannyt.status.Command;

import de.weihnachtsmannyt.status.Manager.FileManager;
import de.weihnachtsmannyt.status.Manager.PrefixManager;
import de.weihnachtsmannyt.status.Status;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import static de.weihnachtsmannyt.status.Manager.FileManager.*;

public class Command implements CommandExecutor {

    YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (sender instanceof Player) {
            if (args.length == 0) {
                sendUsage(sender);
                return true;
            }

            String operator = args[0].toLowerCase();
            switch (operator) {
                case "help":
                    sendUsage(p);
                    break;
                case "reset":
                    if (args.length == 1) {
                        if (!statusData.getString(p.getUniqueId()+".status").equals("Default")) {
                            FileManager.savePlayerInStatus(p, "Default", "§f");
                            PrefixManager.createandsetteam(p);
                            p.sendMessage(Status.getInstance().getStatus_Prefix()+"§7Dein Status wurde §9zurück §7gesetzt!");
                        }else {
                            p.sendMessage(Status.getInstance().getStatus_Prefix()+"§7Dein Status ist schon auf §9Default§7!");
                        }
                    }
                    if (args.length == 2) {
                        if (p.hasPermission("status.admin")) {
                            Player target = Bukkit.getPlayerExact(args[1]);
                            if (playerIsRegistered(target)){
                                FileManager.savePlayerInStatus(target, "Default", "§f");
                                PrefixManager.createandsetteam(target);
                                p.sendMessage(Status.getInstance().getStatus_Prefix()
                                        + "§7Der Status von§9 "
                                        + target.getName()
                                        + "§7 wurde §9zurück §7gesetzt!");
                            }else {
                                p.sendMessage(Status.getInstance().getStatus_Prefix()+"§9Dieser Spieler wurde noch nicht registriert!");
                            }
                        }else {
                            p.sendMessage(Status.getInstance().getStatus_Prefix()+"§9Du darfst dies nicht!");
                        }
                    }
                    break;
                case "set":
                    if (args.length == 2) {
                        if (args[1].length() <= 10) {
                            String status = args[1];
                            FileManager.savePlayerInStatus(p, status, "§f");
                            PrefixManager.createandsetteam(p);
                            p.sendMessage(Status.getInstance().getStatus_Prefix() + "§7Dein Status wurde auf §f" + ChatColor.translateAlternateColorCodes('&', status) + "§7 gesetzt!");
                        } else p.sendMessage(Status.getInstance().getStatus_Prefix() + "§7Dieser Status ist §9zu lang§7 (max10) Zeichen!");
                    }
                    if (args.length == 3) {
                        if (args[1].length()<=10){
                            String status = args[1];
                            String arg2 = args[2].replace('&', '§');
                            String color = PrefixManager.getRawFromColor(arg2);
                            if (PrefixManager.isColorAColor(color)) {
                                FileManager.savePlayerInStatus(p, status, color);
                                PrefixManager.createandsetteam(p);
                                p.sendMessage(Status.getInstance().getStatus_Prefix()+"§7Dein Status wurde auf " + color + ChatColor.translateAlternateColorCodes('&', status) + "§7 mit der Farbe " + color + PrefixManager.getColorFromRaw(color) + "§7 gesetzt!");
                            }else p.sendMessage(Status.getInstance().getStatus_Prefix()+"§7Diese Farbe ist §9ungültig§7 und dein Status hat sich nicht geändert!");
                        }else p.sendMessage(Status.getInstance().getStatus_Prefix()+"§7Dieser Status ist §9zu lang§7 (max10) Zeichen!");
                    }
                    if (args.length == 4) {
                        if (p.hasPermission("status.admin")) {
                            String status = args[1];
                            String arg2 = args[2].replace('&', '§');
                            String color = PrefixManager.getRawFromColor(arg2);
                            if (!FileManager.StringIsBlocked(args[1])) {
                                if (args[1].length()<=10){
                                    Player target = Bukkit.getPlayerExact(args[3]);
                                    if (PrefixManager.isColorAColor(color)) {
                                        FileManager.savePlayerInStatus(target, status, color);
                                        PrefixManager.createandsetteam(target);
                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            all.setScoreboard(PrefixManager.getScoreboard());
                                        }
                                        p.sendMessage(Status.getInstance().getStatus_Prefix()
                                                + "§7Der Status von§9 "
                                                + target.getName()
                                                + "§7 wurde auf "
                                                + color
                                                + ChatColor.translateAlternateColorCodes('&', status)
                                                + "§7 mit der Farbe "
                                                + color
                                                + PrefixManager.getColorFromRaw(color)
                                                + "§7 gesetzt!"
                                        );
                                        target.sendMessage(Status.getInstance().getStatus_Prefix()
                                                + "§7 Der Spieler §9"
                                                + p.getName()
                                                + "§7 hat den Status von dir auf "
                                                + color
                                                + ChatColor.translateAlternateColorCodes('&', status)
                                                + "§7 mit der Farbe "
                                                + color
                                                + PrefixManager.getColorFromRaw(color)
                                                + "§7 gesetzt!"
                                        );
                                    }else p.sendMessage(Status.getInstance().getStatus_Prefix()+"§7Diese Farbe ist §9ungültig§7 und der Status von "+ target.getName() +" hat sich nicht geändert!");
                                }else p.sendMessage(Status.getInstance().getStatus_Prefix()+"§9 Dieser Spieler wurde noch nicht registriert!");
                            }else p.sendMessage(Status.getInstance().getStatus_Prefix()+"§9Dieser Status ist zu lang§7 (max10) §9Zeichen!");
                        }else p.sendMessage(Status.getInstance().getStatus_Prefix()+"§9 Du darfst dies nicht!");
                    }
                    break;
                case "get":
                    YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();
                    String player = args[1];
                    if (args.length == 1){
                        if (!statusData.getString(Bukkit.getPlayerExact(p.getName()).getUniqueId() + ".status").equals("Default")) {
                            p.sendMessage(Status.getInstance().getStatus_Prefix() + "§9 Dein Status ist " + statusData.getString(p.getUniqueId() + ".status") + "mit der Farbe " + statusData.getString(p.getUniqueId() + ".color") + PrefixManager.getColorFromRaw(statusData.getString(p.getUniqueId() + ".color")));
                        }else {
                            p.sendMessage(Status.getInstance().getStatus_Prefix() + "§9 Dein Status ist §fSpieler");
                        }
                    }
                    if (args.length == 2){
                        if (playerIsRegistered(Bukkit.getPlayerExact(player))) {
                            if (p.getName().equalsIgnoreCase(player)) {
                                if (!statusData.getString(Bukkit.getPlayerExact(player).getUniqueId() + ".status").equals("Default")) {
                                    p.sendMessage(Status.getInstance().getStatus_Prefix() + "§9 Dein Status ist " + statusData.getString(player + ".status") + "mit der Farbe " + statusData.getString(player + ".color") + PrefixManager.getColorFromRaw(statusData.getString(player + ".color")));
                                }else {
                                    p.sendMessage(Status.getInstance().getStatus_Prefix() + "§9 Dein Status ist §fSpieler");
                                }
                            } else{
                                if (!statusData.getString(Bukkit.getPlayerExact(player).getUniqueId() + ".status").equals("Default")) {
                                    p.sendMessage(Status.getInstance().getStatus_Prefix() + "§9 Der Status von " + player + " ist " + statusData.getString(player + ".status") + "mit der Farbe " + statusData.getString(player + ".color") + PrefixManager.getColorFromRaw(statusData.getString(player + ".color")));
                                }else {
                                    p.sendMessage(Status.getInstance().getStatus_Prefix() + "§9 Dein Status ist §fSpieler");
                                }
                            }
                        }else {
                            p.sendMessage(Status.getInstance().getStatus_Prefix()+"§9 Dieser Spieler wurde noch nicht registriert!");
                        }
                    }
                    break;
                default:
                    operator = null;
                    break;
            }
            if (operator == null) {
                sendUsage(p);
                return true;
            }
            return true;
        }
        p.sendMessage(Status.getInstance().getStatus_Prefix()+"Du musst ein Spieler sein!");
        return true;
    }

    private void sendUsage(CommandSender sender){
        if (sender.hasPermission("status.admin")){
            sender.sendMessage("§a|§a"+"---------------------------------------------------"+"§a|§r");
            sender.sendMessage("§a|"+"        §c§k!!!!§r§9This Plugin was coded by §cWeihnachtsmannYT§c§k!!!!          "+"§a|§r");
            sender.sendMessage("§a|"+"    §7Verwendung§8: §9/status <operator> <status> <color>          "+"§a|§r");
            sender.sendMessage("§a|"+"    §7Verwendung§8: §9/status <operator> <status> <spieler>        "+"§a|§r");
            sender.sendMessage("§a|"+"       §7<operator>§8: §7< §9\" help \"§7/§9\" set \"§7/§9\" get \"§7/§9\" reset \" §7>         "+"§a|§r");
            sender.sendMessage("§a|§a"+"---------------------------------------------------"+"§a|§r");
        }else{
            sender.sendMessage("§a|§a"+"---------------------------------------------------"+"§a|§r");
            sender.sendMessage("§a|"+"        §c§k!!!!§r§9This Plugin was coded by §cWeihnachtsmannYT§c§k!!!!          "+"§a|§r");
            sender.sendMessage("§a|"+"    §7Verwendung§8: §9/status <operator> <status> <color>          "+"§a|§r");
            sender.sendMessage("§a|"+"       §7<operator>§8: §7< §9\" help \"§7/§9\" set \"§7/§9\" get \"§7/§9\" reset \" §7>         "+"§a|§r");
            sender.sendMessage("§a|§a"+"---------------------------------------------------"+"§a|§r");
        }
    }
}

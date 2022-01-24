package de.weihnachtsmannyt.status.Command;

import de.weihnachtsmannyt.status.Manager.EventManager;
import de.weihnachtsmannyt.status.Manager.FileManager;
import de.weihnachtsmannyt.status.Manager.PrefixManager;
import de.weihnachtsmannyt.status.Status;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
                case "reset":
                    if (args.length == 1) {
                        if (!statusData.getString(p.getName()+".status").equals("Default")) {
                            FileManager.savePlayer(p, "Default", "§f");
                            PrefixManager.createandsetteam(p, "Spieler", "§f");
                            p.sendMessage("§7Dein Status wurde §9zurück §7gesetzt!");
                        }else {
                            p.sendMessage("§7Dein Status ist schon auf §9Default§7!");
                        }
                    }
                    if (args.length == 2) {
                        if (p.hasPermission("status.admin")) {
                            Player target = Bukkit.getPlayerExact(args[3]);
                            FileManager.savePlayer(target, "Default", "§f");
                            PrefixManager.createandsetteam(target, "Spieler", "§f");
                            p.sendMessage("§7Der Status von§9 "
                                    + target.getName()
                                    + "§7 wurde §9zurück §7gesetzt!");
                        }else {
                            p.sendMessage("§9 Du darfst dies nicht!");
                        }
                    }
                    break;
                case "set":
                    if (args.length == 2) {
                        String status = args[1];
                        FileManager.savePlayer(p,status,"§f");
                        PrefixManager.createandsetteam(p, status,"§f");
                        p.sendMessage("§7Dein Status wurde auf §9" + status + "§7 gesetzt!");
                        return true;
                    }
                    if (args.length == 3) {
                        String status = args[1];
                        String color = "§"+args[2];
                        FileManager.savePlayer(p,status,color);
                        PrefixManager.createandsetteam(p, status, color);
                        p.sendMessage("§7Dein Status wurde auf §9" + status + "§7 mit der Farbe " + color + PrefixManager.getcolorfromraw(color) + "§7 gesetzt!");
                        return true;
                    }
                    if (args.length == 4) {
                        if (p.hasPermission("status.admin")) {
                            String status = args[1];
                            String color = "§" + args[2];
                            Player target = Bukkit.getPlayerExact(args[3]);
                            FileManager.savePlayer(target, status, color);
                            PrefixManager.createandsetteam(target, status, color);
                            for (Player all : Bukkit.getOnlinePlayers()){
                                all.setScoreboard(PrefixManager.getScoreboard());
                            }
                            p.sendMessage("§7Der Status von§9 "
                                    + target.getName()
                                    + "§7 wurde auf §9"
                                    + status
                                    + "§7 mit der Farbe "
                                    + color
                                    + PrefixManager.getcolorfromraw(color)
                                    + "§7 gesetzt!"
                            );
                            target.sendMessage("§7 Der Spieler §9"
                                    + p
                                    + "§7 hat den Status von dir auf §9"
                                    + status
                                    + "§7 mit der Farbe "
                                    + color
                                    + PrefixManager.getcolorfromraw(color)
                                    + "§7 gesetzt!"
                            );
                            return true;
                        }else {
                            p.sendMessage("§9 Du darfst dies nicht!");
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
        p.sendMessage("Du musst ein Spieler sein!");
        return true;
    }

    private void sendUsage(CommandSender sender){
        if (sender.hasPermission("status.admin")){
            sender.sendMessage("§7Verwendung§8: §a/status <operator> <status> <color>");
            sender.sendMessage("§7Verwendung§8: §a/status <operator> <status> <spieler>");
        }else{
            sender.sendMessage("§7Verwendung§8: §a/status <operator> <status> <color>");
        }
    }
}

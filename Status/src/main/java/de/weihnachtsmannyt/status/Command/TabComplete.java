package de.weihnachtsmannyt.status.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) return list;
        if (args.length == 1) {
            list.add("set");
            list.add("reset");
        }
        if (args.length == 2) {
            String operator = args[1].toLowerCase();
            switch (operator) {
                case "reset":
                    if (sender.hasPermission("status.admin")) {
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            list.add(online.getName());
                        }
                    }else{
                        list.removeAll(list);
                    }
                    break;
                case "set":
                    list.removeAll(list);
                    break;
                default:
                    return list;
            }
        }
        if (args.length == 3){
            for (int i=1;i<10;i++){
                list.add(String.valueOf(i));
            }
            for (char c = 'a'; c <= 'f'; c++) {
                list.add(String.valueOf(c));
            }
        }
        if (args.length == 4 && sender.hasPermission("status.admin")){
            for (Player online : Bukkit.getOnlinePlayers()){
                list.add(online.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length-1].toLowerCase();
        for (String s : list){
            String s1 = s.toLowerCase();
            if (s1.startsWith(currentarg)){
                completerList.add(s);
            }
        }
        return list;
    }
}

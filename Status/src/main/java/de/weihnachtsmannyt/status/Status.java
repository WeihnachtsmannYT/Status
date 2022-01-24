package de.weihnachtsmannyt.status;

import de.weihnachtsmannyt.status.Command.Command;
import de.weihnachtsmannyt.status.Command.TabComplete;
import de.weihnachtsmannyt.status.Manager.EventManager;
import de.weihnachtsmannyt.status.Manager.FileManager;
import de.weihnachtsmannyt.status.Manager.PrefixManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Status extends JavaPlugin {

    private static Status instance;
    private FileManager fileManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        instance = this;
        this.fileManager = new FileManager();
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();

        Bukkit.getPluginManager().registerEvents(new EventManager(this),this);
        getCommand("status").setExecutor(new Command());
        getCommand("status").setTabCompleter(new TabComplete());
        PrefixManager.setScoreboard();

        for (Player all : Bukkit.getOnlinePlayers()){
            all.setScoreboard(PrefixManager.getScoreboard());
        }

        for (Player all : Bukkit.getOnlinePlayers()){
            if (statusData.getString(all.getName()) == null) {
                FileManager.savePlayer(all, "Default", "§f");
                PrefixManager.getScoreboard().getTeam(PrefixManager.getSpieler()).addPlayer(all);
            }
            if (statusData.getString(all.getName()+".status").equals("Default")){
                PrefixManager.getScoreboard().getTeam(PrefixManager.getSpieler()).addPlayer(all);
            }else {
                PrefixManager.createandsetteam(all,statusData.getString(all.getName()+".status"),statusData.getString(all.getName()+".color"));
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public static Status getInstance() {
        return instance;
    }
}

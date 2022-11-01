package de.weihnachtsmannyt.status;

import de.weihnachtsmannyt.status.Command.Command;
import de.weihnachtsmannyt.status.Command.TabComplete;
import de.weihnachtsmannyt.status.Language.LanguageManager;
import de.weihnachtsmannyt.status.Manager.ChatManager;
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
    private String Status_Prefix = "";

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        instance = this;
        this.fileManager = new FileManager();

        if (this.getConfig().getBoolean("Status-Prefix-on/off")) {
            this.Status_Prefix = this.getConfig().getString("Status-Prefix");
        }

        LanguageManager.setLanguageApiIsEnabled(Bukkit.getPluginManager().isPluginEnabled("LanguageApi"));

        Bukkit.getPluginManager().registerEvents(new EventManager(this),this);
        Bukkit.getPluginManager().registerEvents(new ChatManager(),this);
        getCommand("status").setExecutor(new Command());
        getCommand("status").setTabCompleter(new TabComplete());
        PrefixManager.setScoreboard();

        startSaveAndRegisterPlayer();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Status.getInstance().getFileManager().saveStatusFile();
        Status.getInstance().getFileManager().saveBlockedWordsFile();
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public static Status getInstance() {
        return instance;
    }

    public String getStatus_Prefix() {
        return Status_Prefix + " ";
    }

    public static void startSaveAndRegisterPlayer(){
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();
        for (Player all : Bukkit.getOnlinePlayers()){
            all.setScoreboard(PrefixManager.getScoreboard());
        }
        for (Player all : Bukkit.getOnlinePlayers()){
            if (statusData.getString(all.getName()) == null) {
                FileManager.savePlayerInStatus(all, "Default", "§f");
                PrefixManager.getScoreboard().getTeam(PrefixManager.getSpieler()).addPlayer(all);
            }
            if (statusData.getString(all.getName()+".status").equals("Default")){
                PrefixManager.getScoreboard().getTeam(PrefixManager.getSpieler()).addPlayer(all);
            }else {
                PrefixManager.createandsetteam(all);
            }
        }
    }
}

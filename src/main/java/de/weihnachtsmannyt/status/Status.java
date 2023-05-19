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
    private String Status_Prefix = "";
    private Boolean DeathCounter_on_off = false;

    //Api
    private PrefixManager prefixManager;
    private FileManager fileManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        //APi Stuff
        instance = this;
        this.prefixManager = new PrefixManager();
        this.fileManager = new FileManager();

        if (this.getConfig().getBoolean("Status-Prefix-on/off")) {
            this.Status_Prefix = this.getConfig().getString("Status-Prefix");
        }

        this.DeathCounter_on_off = this.getConfig().getBoolean("DeathCounter-on/off");

        Bukkit.getPluginManager().registerEvents(new EventManager(this),this);
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
        saveDefaultConfig();
    }

    //Api
    public FileManager getFileManager() {
        return fileManager;
    }
    public PrefixManager getPrefixManager() {
        return prefixManager;
    }

    public static Status getInstance() {
        return instance;
    }

    public String getStatus_Prefix() {
        return Status_Prefix + " ";
    }

    public Boolean getDeathCounter_on_off() {
        return DeathCounter_on_off;
    }

    public static void startSaveAndRegisterPlayer(){
        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();
        for (Player all : Bukkit.getOnlinePlayers()){
            all.setScoreboard(PrefixManager.getScoreboard());
        }
        for (Player all : Bukkit.getOnlinePlayers()){
            if (statusData.getString(all.getUniqueId().toString()) == null) {
                FileManager.savePlayerInStatus(all, "Default", "§f");
                PrefixManager.getScoreboard().getTeam(PrefixManager.getTeam()).addEntry(all.getDisplayName());
            }
            if (statusData.getString(all.getUniqueId() + ".status").equals("Default")){
                PrefixManager.getScoreboard().getTeam(PrefixManager.getTeam()).addEntry(all.getDisplayName());
            }else {
                PrefixManager.updatePrefix(all);
            }
    }
    }
}

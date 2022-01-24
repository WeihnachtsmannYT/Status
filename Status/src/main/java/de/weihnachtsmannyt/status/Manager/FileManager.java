package de.weihnachtsmannyt.status.Manager;

import de.weihnachtsmannyt.status.Status;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private File Folder, statusDataFile;

    private YamlConfiguration statusData;

    public FileManager(){
        this.Folder = new File("./plugins/Status/");
        this.statusDataFile = new File(Folder, "status.yml");

        try{
            if (!Folder.exists()) Folder.mkdirs();
            if (!statusDataFile.exists()) statusDataFile.createNewFile();

            statusData = YamlConfiguration.loadConfiguration(statusDataFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            statusData.save(statusDataFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public YamlConfiguration getStatusData() {
        return statusData;
    }

    public static void savePlayer(Player player, String status, String color){
        Player p = player;

        YamlConfiguration statusData = Status.getInstance().getFileManager().getStatusData();
        int value = 1;

        if (statusData.contains(p.getName())){
            value = statusData.getConfigurationSection(p.getName()).getKeys(false).size() + 1;
        }

        statusData.set(p.getName() + ".player", p.getName());
        statusData.set(p.getName() + ".status", status.length() < 10 ? status : "Default");
        statusData.set(p.getName() + ".color", color);

        Status.getInstance().getFileManager().save();

    }
}

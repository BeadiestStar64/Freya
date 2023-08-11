package com.github.beadieststar64.freya.freya;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;

public final class Freya extends JavaPlugin {

    private FileConfiguration yml = null;
    private final File ymlFile;
    private final String file;
    private final Freya plugin;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "FREYA Plugin has been enable!!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "FREYA Plugin has been disable!!");
    }

    public Freya(Freya plugin, String currentPath) {
        this(plugin, currentPath, "config.yml");
    }

    public Freya(Freya plugin, String currentPath, String fileName) {
        this.plugin = plugin;
        this.file = fileName;
        if(currentPath.isEmpty()) {
            ymlFile = new File(plugin.getDataFolder(), File.separator + file);
        }else{
            ymlFile = new File(plugin.getDataFolder(), currentPath + File.separator + file);
        }
        saveDefault();
    }

    private void saveDefault() {
        if(!ymlFile.exists()) {
            plugin.saveResource(file, false);
        }
    }

    private void reloadYml() {
        yml = YamlConfiguration.loadConfiguration(ymlFile);
        final InputStream defYmlStream = plugin.getResource(file);
        if(defYmlStream == null) {
            return;
        }
        yml.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defYmlStream, StandardCharsets.UTF_8)));
    }

    public FileConfiguration getYml() {
        if(yml == null) {
            reloadYml();
        }
        return yml;
    }

    public void saveYml() {
        if(yml == null) {
            return;
        }
        try {
            getYml().save(ymlFile);
        }catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save yml to " + ymlFile, e);
        }
    }

    public boolean getBoolYml(String path) {
        yml = getYml();
        return yml.getBoolean(path);
    }

    public int getIntYml(String path) {
        yml = getYml();
        return yml.getInt(path);
    }

    public String getStringYml(String path) {
        yml = getYml();
        return yml.getString(path);
    }

    public List<String> getListStringYml(String path) {
        yml = getYml();
        return yml.getStringList(path);
    }

    public List<Integer> getListIntegerYml(String path) {
        yml = getYml();
        return yml.getIntegerList(path);
    }

    public List<Boolean> getListBoolYml(String path) {
        yml = getYml();
        return yml.getBooleanList(path);
    }
}

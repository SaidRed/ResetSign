package com.github.saidred.resetsign;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ResetSign extends JavaPlugin {

    private FileConfiguration co;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        co = getConfig();

        this.getServer().getPluginManager().registerEvents(new ResetSignEvent(this),this);

        getCommand("resetSign").setExecutor(new ResetSignCommand(this));
        getCommand("configReload").setExecutor(new ConfigReload(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfiguration getResetSignConfig(){
        return co;
    }
    public void reloadResetSignConfig(){
        co = getConfig();
    }
}

package com.github.saidred.resetsign;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ResetSign extends JavaPlugin {
    @Override
    public void onEnable() {
        // config.ymlが存在しない場合はファイルに出力します。
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        this.getServer().getPluginManager().registerEvents(new ResetSignEvent(this,config),this);

        getCommand("resetSign").setExecutor(new ResetSignCommand(this,config));
        getCommand("configReload").setExecutor(new ConfigReload());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

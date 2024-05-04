package com.github.saidred.resetsign;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ConfigReload implements CommandExecutor {
    ResetSign rs;
    public ConfigReload(ResetSign resetSign, FileConfiguration fileConfiguration){
        rs=resetSign;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender commandSender,@NonNull Command command,@NonNull String s, String[] strings) {
        rs.reloadConfig();
        return false;
    }
}

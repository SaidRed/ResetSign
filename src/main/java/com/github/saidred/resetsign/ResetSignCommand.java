package com.github.saidred.resetsign;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ResetSignCommand implements CommandExecutor {
    private final ResetSign rs;
    private final FileConfiguration fc;
    public ResetSignCommand(ResetSign resetSign, FileConfiguration config) {
        rs=resetSign;
        fc=config;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender,@NonNull Command command,@NonNull String s,String[] strings) {
        if(!(sender instanceof Player))return false;
        Player player=(Player) sender;
        if(fc.getBoolean("playerLogMode")) player.sendMessage("~~~ Reset Sign Command Start ~~~");
        Block block=player.getTargetBlockExact(5);
        if(fc.getBoolean("playerLogMode")) player.sendMessage("> Get Target Block Data");
        if(block == null)return false;
        if(fc.getBoolean("playerLogMode")) player.sendMessage("> Block Not Null");
        if(!(block.getState() instanceof Sign)) return false;
        if(fc.getBoolean("playerLogMode")) player.sendMessage("> Block State Not Sign");
        Sign sign=(Sign) block.getState();
        SignSide back=sign.getSide(Side.BACK);

        if(fc.getBoolean("playerLogMode")) player.sendMessage(">> Start Set Text in Sign Back");
        back.setLine(0, rs.getName() );
        back.setLine(1, player.getInventory().getItemInMainHand().getType().name() );
        if(fc.getBoolean("playerLogMode")) player.sendMessage(">> End Set Text in Sign Back");
        sign.update();
        if(fc.getBoolean("playerLogMode")) player.sendMessage(">> Block State Update");
        return true;
    }
}
package com.github.saidred.resetsign;

import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSignOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ResetSignEvent implements Listener {

    private final Plugin rs;
    public ResetSignEvent(ResetSign resetSign) {
        rs=resetSign;
    }

    @EventHandler
    public void onSignChange(PlayerSignOpenEvent event){
        Player player=event.getPlayer();
        FileConfiguration config=rs.getConfig();
        if( config.getBoolean("playerLogMode") ) player.sendMessage("=== Reset Sign Event Start ===");
        if( !config.getBoolean("gameMode." + player.getGameMode().name()) ){
            if( config.getBoolean("playerLogMode") ) player.sendMessage("* GameMode false");
            return;
        }

        Sign sign=event.getSign();
        SignSide back=sign.getSide(Side.BACK);
        String line0=back.getLine(0);
        if( config.getBoolean("playerLogMode") ) player.sendMessage(">> Get Back Text Line First");
        if( !line0.equals(rs.getName()) ){
            if( config.getBoolean("playerLogMode") ) player.sendMessage("** Not Reset Sign" + line0 );
            return;
        }
        String line1=back.getLine(1);
        if( config.getBoolean("playerLogMode") ) player.sendMessage(">> Get Back Text Line Second");
        ItemStack mainHand=event.getPlayer().getInventory().getItemInMainHand();
        if( !(mainHand.getType().name().equals(line1)) ){
            if( config.getBoolean("playerLogMode") ) player.sendMessage("** Not Equals MainHand Item");
            return;
        }
        if( config.getBoolean("playerLogMode") ) player.sendMessage(">> Item Check true");

        event.setCancelled(true);
        if( config.getBoolean("playerLogMode") ) player.sendMessage(">>> Event Cancel");
        ItemStack newItemStack=rs.getServer().getItemFactory().createItemStack("minecraft:"+line1.toLowerCase());
        if( config.getBoolean("playerLogMode") ) player.sendMessage(">>> New Item Name : " + newItemStack.getType().name());
        newItemStack.setAmount(mainHand.getAmount());
        if( config.getBoolean("playerLogMode") ) player.sendMessage(">>> New Item Amount : " + newItemStack.getAmount());
        event.getPlayer().getInventory().setItemInMainHand(newItemStack);
        if( config.getBoolean("playerLogMode") ) player.sendMessage(">>> MainHand Item Amount : " + mainHand.getAmount());
        if( config.getBoolean("playerLogMode") ) player.sendMessage("=== Reset Sign Event End ===");
    }
}
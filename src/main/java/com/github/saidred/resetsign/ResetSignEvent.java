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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ResetSignEvent implements Listener {
    private final Plugin rs;
    private final FileConfiguration fc;
    public ResetSignEvent(ResetSign resetSign, FileConfiguration config) {
        rs=resetSign;
        fc=config;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction()!= Action.RIGHT_CLICK_BLOCK||event.getClickedBlock()==null)return;
        Player player=event.getPlayer();
        if( fc.getBoolean("playerLogMode") ) player.sendMessage("=== Reset Sign Event Start ===");
        if( !fc.getBoolean("gameMode." + player.getGameMode().name()) ){
            if( fc.getBoolean("playerLogMode") ) player.sendMessage("* GameMode false");
            return;
        }
        BlockState bs=event.getClickedBlock().getState();
        if( fc.getBoolean("playerLogMode") ) player.sendMessage("> Get ClickedBlock State");
        if( !(bs instanceof Sign) ){
            if( fc.getBoolean("playerLogMode") ) player.sendMessage("> Target Not Sign");
            return;
        }

        Sign sign=(Sign) bs;
        SignSide back=sign.getSide(Side.BACK);
        String line0=back.getLine(0);
        if( fc.getBoolean("playerLogMode") ) player.sendMessage(">> Get Back Text Line First");
        if( !line0.equals(rs.getName()) ){
            if( fc.getBoolean("playerLogMode") ) player.sendMessage("** Not Reset Sign" + line0 );
            return;
        }
        String line1=back.getLine(1);
        if( fc.getBoolean("playerLogMode") ) player.sendMessage(">> Get Back Text Line Second");
        ItemStack mainHand=event.getPlayer().getInventory().getItemInMainHand();
        if( !(mainHand.getType().name().equals(line1)) ){
            if( fc.getBoolean("playerLogMode") ) player.sendMessage("** Not Equals MainHand Item");
            return;
        }
        if( fc.getBoolean("playerLogMode") ) player.sendMessage(">> Item Check true");

        event.setCancelled(true);
        if( fc.getBoolean("playerLogMode") ) player.sendMessage(">>> Event Cancel");
        ItemStack newItemStack=rs.getServer().getItemFactory().createItemStack("minecraft:"+line1.toLowerCase());
        if( fc.getBoolean("playerLogMode") ) player.sendMessage(">>> New Item Name : " + newItemStack.getType().name());
        newItemStack.setAmount(mainHand.getAmount());
        if( fc.getBoolean("playerLogMode") ) player.sendMessage(">>> New Item Amount : " + newItemStack.getAmount());
        event.getPlayer().getInventory().setItemInMainHand(newItemStack);
        if( fc.getBoolean("playerLogMode") ) player.sendMessage(">>> MainHand Item Amount : " + mainHand.getAmount());
        if( fc.getBoolean("playerLogMode") ) player.sendMessage("=== Reset Sign Event End ===");
    }
}
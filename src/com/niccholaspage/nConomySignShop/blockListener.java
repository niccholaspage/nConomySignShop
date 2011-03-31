package com.niccholaspage.nConomySignShop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

public class blockListener extends BlockListener {
	public static nConomySignShop plugin;
	public blockListener(nConomySignShop instance){
		plugin = instance;
	}
	@SuppressWarnings("deprecation")
	public void onSignChange(SignChangeEvent event){
		if ((event.getLine(0).equalsIgnoreCase("[nConomy]")) && (!(plugin.has(event.getPlayer(), "nConomySignShop.create")))){
			event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to create nConomySignShop signs!");
			event.getBlock().setTypeId(0);
			//Give back the sign because we're nice (Maybe)
			event.getPlayer().getInventory().addItem(new ItemStack(Material.SIGN, 1));
			event.getPlayer().updateInventory();
		}
	}
}
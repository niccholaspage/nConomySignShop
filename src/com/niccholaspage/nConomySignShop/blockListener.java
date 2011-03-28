package com.niccholaspage.nConomySignShop;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.inventory.ItemStack;

public class blockListener extends BlockListener {
	public static nConomySignShop plugin;
	public blockListener(nConomySignShop instance){
		plugin = instance;
	}
	@SuppressWarnings("deprecation")
	public void onBlockRightClick(BlockRightClickEvent event){
		if ((event.getBlock().getType().equals(Material.SIGN_POST)) || (event.getBlock().getType().equals(Material.WALL_SIGN))){
			Sign sign = (Sign) event.getBlock().getState();
			if (!(sign.getLine(0).equalsIgnoreCase("[nConomy]"))) return;
			if ((!(plugin.isInt(sign.getLine(1)))) || (!(plugin.isInt(sign.getLine(2))) || (!(plugin.isInt(sign.getLine(3)))))) return;
			Integer ID = Integer.parseInt(sign.getLine(1));
			Integer amount = Integer.parseInt(sign.getLine(2));
			Integer pay = Integer.parseInt(sign.getLine(3));
			if (plugin.econHandler.canAddorDelete(pay) == false) return;
			if (Material.getMaterial(ID) == null) return;
			if (amount == 0) return;
			if (plugin.econHandler.getMoney(event.getPlayer()) < pay) return;
			ItemStack item = new ItemStack(0);
			item.setTypeId(ID);
			item.setAmount(amount);
			event.getPlayer().getInventory().addItem(item);
			event.getPlayer().updateInventory();
			plugin.econHandler.removeMoney(event.getPlayer(), pay);
		}
	}
}
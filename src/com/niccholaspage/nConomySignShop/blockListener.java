package com.niccholaspage.nConomySignShop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;
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
	@SuppressWarnings("deprecation")
	public void onBlockRightClick(BlockRightClickEvent event){
		if ((event.getBlock().getType().equals(Material.SIGN_POST)) || (event.getBlock().getType().equals(Material.WALL_SIGN))){
			Sign sign = (Sign) event.getBlock().getState();
			Player player = event.getPlayer();
			if (!(sign.getLine(0).equalsIgnoreCase("[nConomy]"))) return;
			if ((!(plugin.isInt(sign.getLine(2))) || (!(plugin.isInt(sign.getLine(3)))))) return;
			if (!(plugin.hasNotOP(player, "nConomySignShop.use"))) return;
			Material type;
			if (plugin.isInt(sign.getLine(1))){
				type = Material.getMaterial(Integer.parseInt(sign.getLine(1)));
			}else {
				type = Material.getMaterial(sign.getLine(1).toUpperCase());
			}
			//Integer ID = Integer.parseInt(sign.getLine(1));
			Integer amount = Integer.parseInt(sign.getLine(2));
			Integer pay = Integer.parseInt(sign.getLine(3));
			if (plugin.econHandler.canAddorDelete(pay) == false) return;
			if (type == null) return;
			if (amount == 0) return;
			if (plugin.econHandler.getMoney(player) < pay) return;
			ItemStack item = new ItemStack(0);
			item.setType(type);
			item.setAmount(amount);
			player.getInventory().addItem(item);
			player.updateInventory();
			plugin.econHandler.removeMoney(player, pay);
			player.sendMessage(ChatColor.BLUE + "You just bought " + amount + " " + type.name().toLowerCase().replace("_", " ") + " for " + pay + " " + plugin.econHandler.currencyName + ".");
		}
	}
}
package com.niccholaspage.nConomySignShop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class playerListener extends PlayerListener {
	public static nConomySignShop plugin;
	public playerListener(nConomySignShop instance){
		plugin = instance;
	}
	@SuppressWarnings("deprecation")
	public void onPlayerInteract(PlayerInteractEvent event){
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		Block block = event.getClickedBlock();
		if ((block.getType().equals(Material.SIGN_POST)) || (block.getType().equals(Material.WALL_SIGN))){
			Sign sign = (Sign) block.getState();
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
			Integer amount = Integer.parseInt(sign.getLine(2));
			Integer pay = Integer.parseInt(sign.getLine(3));
			if (plugin.econHandler.canAddorDelete(pay) == false) return;
			if (type == null) return;
			if (amount == 0) return;
			String typeName = type.name().toLowerCase().replace("_", " ");
			if (plugin.econHandler.getMoney(player) < pay){
				player.sendMessage(ChatColor.RED + "You don't have enough " + plugin.econHandler.currencyName + " to buy the " + typeName + " from this sign.");
				return;
			}
			ItemStack item = new ItemStack(0);
			item.setType(type);
			item.setAmount(amount);
			player.getInventory().addItem(item);
			player.updateInventory();
			plugin.econHandler.removeMoney(player, pay);
			player.sendMessage(ChatColor.BLUE + "You just bought " + amount + " " + typeName + " for " + pay + " " + plugin.econHandler.currencyName + ".");
		}
	}
}

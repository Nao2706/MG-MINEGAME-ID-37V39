package me.nao.items.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.nao.shop.Items;



public class FlareActions {
	
	public void SendAirDrop(Player player,Location l ) {
		
	
	
		Block b = l.getWorld().getHighestBlockAt(l);
		if(b.getType() == Material.BARRIER) {
			player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"AC 130: "+ChatColor.GREEN+"Paquete de ayuda en Camino suerte.");

			StorageMinecart ent = (StorageMinecart) b.getWorld().spawnEntity(b.getLocation().add(0.5, -3, 0.5), EntityType.MINECART_CHEST);
			ent.setCustomName(""+ChatColor.GREEN+ChatColor.BOLD+"PAQUETE DE AYUDA");
			ent.setCustomNameVisible(true);		
			ent.getLocation().setYaw(player.getLocation().getYaw());
			
			Vector v = player.getLocation().toVector().subtract(ent.getLocation().toVector());
			Location en = ent.getLocation();
			en.setDirection(v);
			ent.teleport(en);
			
			for(int i = 0 ; i <27;i++) {
				ent.getInventory().addItem(CustomItems());
			}
			
		}else {
			player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"AC 130: "+ChatColor.YELLOW+"No podemos ver tu bengala lanza otra donde podamos verla.");
			//System.out.println("No hay Barriers");
		}
		
		return;
		
	}
	
	public void SendAirStrike(Player player,Location l) {
		Block b = l.getWorld().getHighestBlockAt(l);
		if(b.getType() == Material.BARRIER) {
			player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"A10 WARTHOG: "+ChatColor.GREEN+"Ataque en Camino");

			Entity fb = b.getWorld().spawnEntity(b.getLocation().add(0.5, -1, 0.5), EntityType.FIREBALL);
			//fb.setCustomName("Mortero");
			
			Fireball f = (Fireball) fb;
			f.setYield(10);
			f.setShooter(null);
			f.setCustomName(""+ChatColor.RED+ChatColor.BOLD+"Ataque Aereo");
			f.setDirection(new Vector (0,-3,0));
			f.setVelocity(f.getDirection().multiply(3));
			
			//((Fireball) fb).setShooter(player);
		}else {
			player.sendMessage(""+ChatColor.RED+ChatColor.BOLD+"A10 WARTHOG: "+ChatColor.YELLOW+"No podemos ver tu bengala lanza otra donde podamos verla.");
			//System.out.println("No hay Barriers2");
		}
		return;
	}
	
	private ItemStack CustomItems() {
		Random r = new Random();
		List<ItemStack> l = new ArrayList<>();
		l.add(MaterialToItemStack(Material.AIR,1));
		l.add(MaterialToItemStack(Material.GOLDEN_APPLE,25));
		l.add(MaterialToItemStack(Material.ARROW,64));
		l.add(MaterialToItemStack(Material.ENCHANTED_GOLDEN_APPLE,13));
		l.add(MaterialToItemStack(Material.DIAMOND,45));
		l.add(MaterialToItemStack(Material.AIR,1));
		l.add(MaterialToItemStack(Material.NETHERITE_INGOT,60));
		l.add(MaterialToItemStack(Material.EMERALD,35));
		l.add(MaterialToItemStack(Material.AIR,1));
		l.add(MaterialToItemStack(Material.GOLD_INGOT,25));
		l.add(MaterialToItemStack(Material.SPECTRAL_ARROW,55));
		l.add(MaterialToItemStack(Material.BOW,1));
		l.add(MaterialToItemStack(Material.DIAMOND_CHESTPLATE,1));
		l.add(MaterialToItemStack(Material.NETHERITE_CHESTPLATE,1));
		l.add(MaterialToItemStack(Material.AIR,1));
		l.add(Items.ESCU1P.getValue());
		l.add(Items.ESCU2P.getValue());
		l.add(Items.KATANAP.getValue());
		l.add(Items.PALOP.getValue());
		return l.get(r.nextInt(l.size()));
		
	}
	
	private ItemStack MaterialToItemStack(Material m,int amount) {
		return new ItemStack(m,amount);
	}

}

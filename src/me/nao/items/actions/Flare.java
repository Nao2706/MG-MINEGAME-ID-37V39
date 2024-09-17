package me.nao.items.actions;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.nao.main.game.Minegame;
import me.nao.shop.Items;

public class Flare extends BukkitRunnable{

	
	
	private Item item;
	
	
	public Flare(Player player,ItemStack it, Location l) {
		item = l.getWorld().dropItem(l,it);
		item.setVelocity(player.getEyeLocation().getDirection().multiply(3));
		runTaskTimer(JavaPlugin.getPlugin(Minegame.class),0,3);

		
	}
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		item.setPickupDelay(9999);
	
		if(item.getItemStack().isSimilar(Items.BENGALAROJA.getValue()) ) {
			new InstantFirework(FireworkEffect.builder().with(Type.BURST).withColor(Color.RED).build(),item.getLocation());
		}else if(item.getItemStack().isSimilar(Items.BENGALAVERDE.getValue()) ) {
			new InstantFirework(FireworkEffect.builder().with(Type.BURST).withColor(Color.GREEN).build(),item.getLocation());
		}
		
	 
		if(item == null || item.isOnGround() || item.isDead() || item.isInWater()) {
			if(item.getItemStack().isSimilar(Items.BENGALAROJA.getValue()) ) {
				FlareActions fl = new FlareActions();
				fl.SendAirStrike(item.getLocation());
			
			}else if(item.getItemStack().isSimilar(Items.BENGALAVERDE.getValue()) ) {
				FlareActions fl = new FlareActions();
				fl.SendAirDrop(item.getLocation());
			
			}
			
			cancel();
			item.remove();
			
		
		}
		
	}
	
	
	
	
	
	
	
	

}

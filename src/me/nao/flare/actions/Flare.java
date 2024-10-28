package me.nao.flare.actions;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.nao.enums.Items;
import me.nao.main.game.Minegame;

public class Flare extends BukkitRunnable{

	
	
	private Item item;
	private Player player;
	private Minegame plugin;
	
	public Flare(Player player,ItemStack it, Location l,Minegame plugin) {
		this.player = player;
		this.plugin = plugin;
		item = l.getWorld().dropItem(l,it);
		item.setVelocity(player.getEyeLocation().getDirection().multiply(3));
		runTaskTimer(JavaPlugin.getPlugin(Minegame.class),0,3);

		
	}
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		item.setPickupDelay(9999);
	
		if(item.getItemStack().isSimilar(Items.BENGALAROJA.getValue()) || item.getItemStack().isSimilar(Items.BENGALAROJAP.getValue())) {
			new InstantFirework(FireworkEffect.builder().with(Type.BURST).withColor(Color.RED).build(),item.getLocation());
		}else if(item.getItemStack().isSimilar(Items.BENGALAVERDE.getValue())  || item.getItemStack().isSimilar(Items.BENGALAVERDEP.getValue()) ) {
			new InstantFirework(FireworkEffect.builder().with(Type.BURST).withColor(Color.GREEN).build(),item.getLocation());
		}
		
	 
		if(item == null || item.isOnGround() || item.isDead() || item.isInWater()) {
			FlareActions fl = new FlareActions(plugin);
			if(item.getItemStack().isSimilar(Items.BENGALAROJA.getValue()) || item.getItemStack().isSimilar(Items.BENGALAROJAP.getValue()) ) {
				
				fl.SendAirStrike(player,item.getLocation());
			
			}else if(item.getItemStack().isSimilar(Items.BENGALAVERDE.getValue()) || item.getItemStack().isSimilar(Items.BENGALAVERDEP.getValue()) ) {
				
				fl.SendAirDrop(player,item.getLocation());
			
			}
			
			cancel();
			item.remove();
			
		
		}
		
	}
	
	
	
	
	
	
	
	

}

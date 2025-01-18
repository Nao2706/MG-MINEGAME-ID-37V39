package me.nao.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.nao.main.game.Minegame;

public class Minigunarrow extends BukkitRunnable{

	
	private Monster entity;
	private int time;
	
	public Minigunarrow(Monster entity, int time) {
		this.entity = entity; 
		this.time = time;
		runTaskTimer(JavaPlugin.getPlugin(Minegame.class),0,3);
	}
	
	
	public Monster getMonster() {
		return entity;
	}
	
	@Override
	public void run() {
		
		if(time == 0 || getMonster() == null || getMonster().isDead()) {
			this.cancel();
			if(getMonster() != null) {
				getMonster().getEquipment().setItemInMainHand(new ItemStack(Material.BLAZE_ROD));
			}
		}
		
		
		Location loc = getMonster().getLocation();
		Location loc2 = getMonster().getLocation();
			
	//	Location sl = new Location(Bukkit.getWorld(loc.getWorld().getName()), loc.getX(), loc.getY()+1, loc.getZ());
	//	Location sl2 = new Location(Bukkit.getWorld(loc.getWorld().getName()), loc.getX(), loc.getY()+1, loc.getZ());
		
		Arrow aw = (Arrow) getMonster().getWorld().spawnEntity(loc.add(0, 1.6, 0), EntityType.ARROW);
		Arrow aw2 = (Arrow) getMonster().getWorld().spawnEntity(loc2.add(0, 1.6, 0), EntityType.ARROW);
		aw.setCritical(true);
		//aw.setKnockbackStrength(2);
		aw.setFireTicks(1200);
		aw.setShooter(getMonster());
		aw.setVelocity(loc.getDirection().multiply(6).rotateAroundY(Math.toRadians(1)));
		aw2.setCritical(true);
		//aw2.setKnockbackStrength(2);
		aw2.setFireTicks(1200);
		aw2.setShooter(getMonster());
		aw2.setVelocity(loc2.getDirection().multiply(6).rotateAroundY(Math.toRadians(-1)));
		
		time--;
		
	}
	
	
	
	
	
	
}

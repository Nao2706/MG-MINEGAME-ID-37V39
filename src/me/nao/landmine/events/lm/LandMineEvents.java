package me.nao.landmine.events.lm;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.nao.landmine.enums.lm.DetectionType;
import me.nao.landmine.enums.lm.ItemsIs;
import me.nao.landmine.enums.lm.Position;
import me.nao.landmine.general.lm.ExplosiveMine;
import me.nao.landmine.general.lm.LandMineManager;
import me.nao.landmine.main.lm.LandMine;

public class LandMineEvents implements Listener{

	private LandMine plugin;
	private LandMineManager lm;
	
	public LandMineEvents(LandMine plugin) {
		this.plugin = plugin;
		this.lm = new LandMineManager(plugin);
	}
	
	@EventHandler
	public void onLmMove(PlayerMoveEvent e){
		
		Player player = e.getPlayer();
		
		if(!lm.isWorldAllowedForLandMines(player.getWorld().getName()))return;
		
		Location loc = player.getLocation();
		Location above = loc.getBlock().getRelative(0, 2, 0).getLocation();
		Location space1 = loc.getBlock().getRelative(0, 1, 0).getLocation();
		Location space2 = loc.getBlock().getRelative(0, 0, 0).getLocation();
		Location under = loc.getBlock().getRelative(0, -1, 0).getLocation();
	
		if(!player.isFlying()) {
		
			if(player.getGameMode() != GameMode.SPECTATOR) {
			
				if(lm.isaLandMine(under)) {
					ExplosiveMine em = lm.getLandMineInfo(under);
					
					if(em.getDetectionType() == DetectionType.MOVE || em.getDetectionType() == DetectionType.GENERAL) {
						if(em.getDetectionType() == DetectionType.GENERAL) {
							em.setDetectionType(DetectionType.MOVE );
						}
						em.setLandMinePosition(Position.DOWN);
						lm.executeLandMine(player,em.getLocationLandMine(), em.getLandMineType());
						
					}
					
				}else if(lm.isaLandMine(space1)) {
					ExplosiveMine em = lm.getLandMineInfo(space1);
					
					if(em.getDetectionType() == DetectionType.MOVE || em.getDetectionType() == DetectionType.GENERAL) {
						if(em.getDetectionType() == DetectionType.GENERAL) {
							em.setDetectionType(DetectionType.MOVE );
						}
						em.setLandMinePosition(Position.MIDDLE);
						lm.executeLandMine(player, em.getLocationLandMine(), em.getLandMineType());
			
					}
					
				}else if(lm.isaLandMine(space2)) {
					ExplosiveMine em = lm.getLandMineInfo(space2);
					
					if(em.getDetectionType() == DetectionType.MOVE || em.getDetectionType() == DetectionType.GENERAL) {
						if(em.getDetectionType() == DetectionType.GENERAL) {
							em.setDetectionType(DetectionType.MOVE);
						}
						em.setLandMinePosition(Position.MIDDLE);
						lm.executeLandMine(player, em.getLocationLandMine(), em.getLandMineType());
			
					}
					
				}else if(lm.isaLandMine(above)) {
					ExplosiveMine em = lm.getLandMineInfo(above);
					
					if(em.getDetectionType() == DetectionType.MOVE || em.getDetectionType() == DetectionType.GENERAL) {
						if(em.getDetectionType() == DetectionType.GENERAL) {
							em.setDetectionType(DetectionType.MOVE );
						}
						em.setLandMinePosition(Position.UP);

						lm.executeLandMine(player, em.getLocationLandMine(), em.getLandMineType());
					
					}
					
				}
			}
			
		}

		
		return;
		
	}
	
	
	@EventHandler
	public void onLmInteract(PlayerInteractEvent e){
		Player player = e.getPlayer();
		
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(!lm.isWorldAllowedForLandMines(player.getWorld().getName()))return;
		if(player.getGameMode() != GameMode.SPECTATOR) {
		
			if(e.getAction() == Action.LEFT_CLICK_BLOCK ||e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				
				
				Location loc = e.getClickedBlock().getLocation();
				if(e.getItem() != null) {
					if(e.getItem().isSimilar(ItemsIs.REMOVER.getValue())) {
						lm.deleteLandMineData(player,loc);
						
						return;
					}else if(e.getItem().isSimilar(ItemsIs.DETECTOR.getValue())) {
						lm.checkLandMine(player, loc);
						
						return;
					}
				}else if(lm.isaLandMine(loc)) {
					ExplosiveMine em = lm.getLandMineInfo(loc);
					
					if(em.getDetectionType() == DetectionType.INTERACT || em.getDetectionType() == DetectionType.GENERAL) {
						if(em.getDetectionType() == DetectionType.GENERAL) {
							em.setDetectionType(DetectionType.INTERACT );
						}
						lm.executeLandMine(player, em.getLocationLandMine(), em.getLandMineType());
					
					}
					
			    }
			}
		}

		

		return;
	
	}
	
	
	@EventHandler
	public void onLmBreak(BlockBreakEvent e){
		
		Player player = e.getPlayer();
		if(!lm.isWorldAllowedForLandMines(player.getWorld().getName()))return;
		
		Location loc = e.getBlock().getLocation();
		
		
		if(lm.isaLandMine(loc)) {
			ExplosiveMine em = lm.getLandMineInfo(loc);
			if(em.getDetectionType() == DetectionType.BLOCKBREAK || em.getDetectionType() == DetectionType.GENERAL) {
				if(em.getDetectionType() == DetectionType.GENERAL) {
					em.setDetectionType(DetectionType.BLOCKBREAK );
				}
				
				lm.executeLandMine(player, em.getLocationLandMine(), em.getLandMineType());
			}else {
				lm.removeLandMine(loc);
			}
			
		}
		return;
	}
	
	
	@EventHandler
	public void onLmPlace(BlockPlaceEvent e){
		
		Player player = e.getPlayer();
	
		if(lm.isLandMineItem(e.getItemInHand())){
			if(!lm.isWorldAllowedForLandMines(player.getWorld().getName())) {
				FileConfiguration datam = plugin.getLandMineGeneralDataYml().getMessages();
				lm.sendMessage(player, datam.getString("DeniedLandMineWorldMessage"));
				e.setCancelled(true);
				return;
			} 
				
			if(lm.hasReachLimitOfLandMines(player)) {
				lm.showLandMinesLimit(player);
				e.setCancelled(true);
				return;
			}
			
			Location loc = e.getBlockPlaced().getLocation();
	
			lm.registerLandMineData(player, e.getItemInHand(), loc);
			lm.showLandMinesLimit(player);
		}
		
		return;
	}
	
	@EventHandler
	public void onLmExplode(EntityExplodeEvent e){
		
		if(!lm.isWorldAllowedForLandMines(e.getLocation().getWorld().getName()))return;
		List<Block> l = e.blockList();
		
		for(Block b : l) {
			if(lm.isaLandMine(b.getLocation())) {
				lm.removeLandMine(b.getLocation());
			}
		}
	}
	
	
	@EventHandler
	public void onLmBlockExplode(BlockExplodeEvent e){
		
		if(!lm.isWorldAllowedForLandMines(e.getBlock().getWorld().getName()))return;
		List<Block> l = e.blockList();
		
		for(Block b : l) {
			if(lm.isaLandMine(b.getLocation())) {
				lm.removeLandMine(b.getLocation());
			}
		}
		
		
		
	}
	
//	@EventHandler
//	public void onLmUpdate(BlockPistonEvent e){
//		
//		Block b = e.getBlock();
//		
//		if(!lm.isWorldAllowedForLandMines(b.getWorld().getName()))return;
//		
//		
//		Location loc = b.getLocation();
//		
//		
//		if(lm.isaLandMine(loc)) {
//			ExplosiveMine em = lm.getLandMineInfo(loc);
//			if(em.getDetectionType() == DetectionType.REDSTONE || em.getDetectionType() == DetectionType.GENERAL) {
//				if(em.getDetectionType() == DetectionType.GENERAL) {
//					em.setDetectionType(DetectionType.REDSTONE);
//				}
//				
//				lm.executeLandMine(em.getLocationLandMine(), em.getLandMineType());
//			 }
//			 
//	 	}
//		
//	}
//	
	
}

package me.nao.landmine.commands.lm;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nao.landmine.enums.lm.DetectionType;
import me.nao.landmine.enums.lm.ItemsIs;
import me.nao.landmine.enums.lm.LandMineType;
import me.nao.landmine.general.lm.LandMineManager;
import me.nao.landmine.main.lm.LandMine;
import me.nao.landmine.utils.lm.Utils;

public class LandMineCommands  implements CommandExecutor{

	private LandMine plugin;
	private LandMineManager lm;
	
	
	public LandMineCommands(LandMine plugin) {
		this.plugin = plugin;
		this.lm = new LandMineManager(plugin);
	}
	
	
	
	public boolean onCommand(CommandSender sender,  Command comando,  String label, String[] args) {
		
		
		if(!(sender instanceof Player)){
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("reload")){
				   reloadAll();
				switchMessage(null,"&aSe han Recargado los Datos.");
				 return true;
				}else if (args[0].equalsIgnoreCase("playerslandmines")) {
					if(args.length == 1) {
						
						lm.showPlayersLandMines(null, 1);
					
					}else if(args.length == 2){
						
						int pag = Integer.valueOf(args[1]);
						lm.showPlayersLandMines(null, pag);
					}else {
						
					}

					return true;
					
				}else if (args[0].equalsIgnoreCase("playerlandmines")) {
					if(args.length == 2) {
						String name = args[1];
						lm.showPlayerLandMines(null,name, 1);
					
					}else if(args.length == 3){
						String name = args[1];
						int pag = Integer.valueOf(args[2]);
						lm.showPlayerLandMines(null,name, pag);
					}else {
						switchMessage(null, "&aUse /lm playerlandmines <name> <pag>");
					}

					return true;
					
				}else if (args[0].equalsIgnoreCase("worldslandmines")) {
					if(args.length == 1) {
						
						lm.showWorldsLandMines(null, 1);
					
					}else if(args.length == 2){
						
						int pag = Integer.valueOf(args[1]);
						lm.showWorldsLandMines(null,pag);
					}

					return true;
					
				}
				
				
				
				
				
			}
			
			return true;
		}else {
			Player player = (Player) sender;
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("reload")){
					 switchMessage(null,"&aSe han Recargado los Datos.");
					 reloadAll();
					player.sendMessage("Datos Recargados");
					return true;
				}else if(args[0].equalsIgnoreCase("createLandMine")) {
					
					//lm createlandmine MATERIAL MOVE EXPLOSION
					
					if(args.length == 3) {
						
						DetectionType dt = DetectionType.matchType(args[1].toUpperCase());
						LandMineType lt = LandMineType.matchLandMine(args[2].toUpperCase());
						
						lm.addConvertItemToLandMine(player, player.getInventory().getItemInMainHand(), dt, lt);
						
					}else if(args.length == 4) {
						Material m = Material.matchMaterial(args[1].toUpperCase());
						DetectionType dt = DetectionType.matchType(args[2].toUpperCase());
						LandMineType lt = LandMineType.matchLandMine(args[3].toUpperCase());
						lm.addConvertItemToLandMine(player, m, dt, lt);

						
					}else {
						
					}
					
					return true;
				}else if(args[0].equalsIgnoreCase("item")) {
					player.getInventory().addItem(ItemsIs.REMOVER.getValue());
					player.getInventory().addItem(ItemsIs.DETECTOR.getValue());
					
					return true;
				}else if(args[0].equalsIgnoreCase("save")) {
					
					lm.saveLandMineData();
			 		player.sendMessage("Datos Guardados");
					return true;
				}else if(args[0].equalsIgnoreCase("delete")) {
					
					player.getInventory().addItem(ItemsIs.DETECTOR.getValue());
					
					return true;
				}else if(args[0].equalsIgnoreCase("loc")) {
					
					System.out.println(player.getLocation().toString());
					
					
					return true;
				}else if(args[0].equalsIgnoreCase("print")) {
					
					lm.printResult(player);
					
					
					return true;
				}else if (args[0].equalsIgnoreCase("playerslandmines")) {
					if(args.length == 1) {
						
						lm.showPlayersLandMines(player, 1);
					
					}else if(args.length == 2){
						
						int pag = Integer.valueOf(args[1]);
						lm.showPlayersLandMines(player, pag);
					}else {
						
					}

					return true;
					
				}else if (args[0].equalsIgnoreCase("playerlandmines")) {
					if(args.length == 2) {
						String name = args[1];
						lm.showPlayerLandMines(player,name, 1);
					
					}else if(args.length == 3){
						String name = args[1];
						int pag = Integer.valueOf(args[2]);
						lm.showPlayerLandMines(player,name, pag);
					}else {
						switchMessage(player, "&aUse /lm playerlandmines <name> <pag>");
					}

					return true;
					
				}else if (args[0].equalsIgnoreCase("worldslandmines")) {
					if(args.length == 1) {
						
						lm.showWorldsLandMines(player, 1);
					
					}else if(args.length == 2){
						
						int pag = Integer.valueOf(args[1]);
						lm.showWorldsLandMines(player,pag);
					}

					return true;
					
				}
				
				//PENDIENTE MOSTRAR TOTAL DE MINAS Y POR JUGADOR
				//MOSTRAR MINAS POR JUGADOR MAS LOCATIONS Y TYPE
				//SALVAR Y CARGAR DATOS
				//MENSAJES
				//AÑADIR MINAS
			
			
			}
		}
		
		
		
		
		return false;
	}
	
	
	
	
	public void switchMessage(Player player ,String text) {
		if(player != null) {
			player.sendMessage(Utils.formatChatColor(text));
			return;
		}
		Bukkit.getConsoleSender().sendMessage(Utils.formatChatColor(text));
	}
	
	
	public void reloadAll() {
		plugin.getConfig().reload();
		plugin.getMessages().reload();
		plugin.getLandMineData().reload();
		
		
		plugin.getLandMineGeneralDataYml().setConfig(plugin.getConfig());
		plugin.getLandMineGeneralDataYml().setMessages(plugin.getMessages());
		
		lm.loadGroups();
	}
	
	
	
	
	
	
	
	
	
}

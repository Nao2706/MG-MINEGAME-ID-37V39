
package me.nao.shop.mg;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.common.base.Strings;

import me.nao.enums.mg.Items;
import me.nao.enums.mg.ObjetiveStatusType;
import me.nao.enums.mg.Posion;
import me.nao.generalinfo.mg.GameAdventure;
import me.nao.generalinfo.mg.GameConditions;
import me.nao.generalinfo.mg.GameInfo;
import me.nao.generalinfo.mg.GameObjetivesMG;
import me.nao.generalinfo.mg.ItemMenu;
import me.nao.generalinfo.mg.ObjetivesMG;
import me.nao.generalinfo.mg.PlayerInfo;
import me.nao.main.mg.Minegame;
import me.nao.manager.mg.GameIntoMap;
import me.nao.yamlfile.mg.YamlFilePlus;




@SuppressWarnings("deprecation")
public class MinigameShop1 implements Listener{
	

	private Minegame plugin;
	private int TaskID;
	private int TaskID2;
	

	public MinigameShop1(Minegame plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void clickEventItem(InventoryDragEvent e) {
	    //Player player = (Player) e.getWhoClicked();
		
			List<String> l = new ArrayList<String>();
			
			l.add("TIENDA"); l.add("ESPADAS");l.add("ARCOS Y BALLESTAS");l.add("DEFENSA");l.add("COMIDA Y POSIONES");l.add("ESPECIALES");l.add("REVIVIR");
			l.add("MENU DE MAPAS");l.add("OBJETIVOS");l.add("OBJETIVOS PRIMARIOS");l.add("OBJETIVOS SECUNDARIOS");
			String namet = ChatColor.stripColor(e.getView().getTitle());
			if(l.contains(namet)) {
				
					e.setCancelled(true);
		        	//Bukkit.getConsoleSender().sendMessage("ME");

				
				
			}
		
		
	}
	
	
	@SuppressWarnings("removal")
	@EventHandler
	public void clickEvent(InventoryClickEvent e) {
		
		Player player = (Player) e.getWhoClicked();
		
		
		
		
		//String tittle = ChatColor.translateAlternateColorCodes("&", usa el fileconfiguartion);
		// String tittlec = ChatColor.stripColor(titulo);
		if(e.getClickedInventory() == null) return;
		
	
			List<String> l = new ArrayList<String>();
			l.add("TIENDA"); l.add("ESPADAS");l.add("ARCOS Y BALLESTAS");l.add("DEFENSA");l.add("COMIDA Y POSIONES");l.add("ESPECIALES");l.add("REVIVIR");l.add("MENU DE MAPAS");
			l.add("OBJETIVOS");l.add("OBJETIVOS PRIMARIOS");l.add("OBJETIVOS SECUNDARIOS");l.add("OBJETIVOS HOSTILES");
			String namet = ChatColor.stripColor(e.getView().getTitle());
			
			
			if(!l.contains(namet)) {
				return;
			}
			
			//checa que los inventarios creados sean custom 
			if(!isCustomInventory(player))return;
			
			
			
			
				if(e.getCurrentItem() == null) {
						if(e.getClickedInventory().getType() == e.getView().getType()) {
							//System.out.println("GG");
							e.setCancelled(true);
						}
						return;
				}
					
				
				if(e.getCurrentItem() != null) {
					
					if(e.getClickedInventory().getType() == e.getView().getType()) {
						//System.out.println("GG");
						e.setCancelled(true);
					}
					
					
					if(e.getClickedInventory().getType() == InventoryType.CHEST) {
						
						ClickType ct = e.getClick();
						 switch(ct) {
						 case CONTROL_DROP: 
						//	 Bukkit.getConsoleSender().sendMessage("No drop3");
							 e.setCancelled(true);
							 break;
						 case DROP: 
							// Bukkit.getConsoleSender().sendMessage("No drop2");
							 e.setCancelled(true);
							 break;
							 
						default:
							break;
						 }
					
					}
					
					InventoryAction action = e.getAction();
				//	if(!(e.getClickedInventory().getType() == InventoryType.PLAYER)) {
					//	e.setCancelled(true);
						
				//  	}
					
					if(e.getClickedInventory().getType() == InventoryType.PLAYER) {
						
						
						  switch(action) {
					      case MOVE_TO_OTHER_INVENTORY:
 
					          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
					            e.setCancelled(true);
					            return;
					        case HOTBAR_MOVE_AND_READD:
 
					          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
					            e.setCancelled(true);
					            return;
					        case HOTBAR_SWAP:
 
					          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
					            e.setCancelled(true);
					            return;
					            
					        case SWAP_WITH_CURSOR:
 
						          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
						            e.setCancelled(true);
						            return;
						default:
							break;
				
						            
					
						  }
					}
					
					StoreChest(player, e.getCurrentItem());
					
					
					 	
						if(player.getOpenInventory().getBottomInventory().getType() == InventoryType.PLAYER && e.getClickedInventory().getType() == InventoryType.CHEST) {

							  switch(action) {
						      case MOVE_TO_OTHER_INVENTORY:
 						          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
						            e.setCancelled(true);
						            return;
						        case HOTBAR_MOVE_AND_READD:
 						          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
						            e.setCancelled(true);
						            return;
						        case HOTBAR_SWAP:
 						          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
						            e.setCancelled(true);
						            return;
						            
						        case SWAP_WITH_CURSOR:
 							          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
							            e.setCancelled(true);
							            return;
						        case COLLECT_TO_CURSOR:
 							          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
							            e.setCancelled(true);
							            return;
						        case DROP_ALL_CURSOR:
 							          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
							            e.setCancelled(true);
							            return; 
						        case PLACE_SOME:
 
							          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
							            e.setCancelled(true);
							            return;
						        case DROP_ONE_SLOT:
 							          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
							            e.setCancelled(true);
							            return; 
						        case DROP_ALL_SLOT:
 
							          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
							            e.setCancelled(true);
							            return;
						        case PLACE_ALL:
 
							          //  player.sendMessage("§cPlease do not shift-click items into this inventory.");
							            e.setCancelled(true);
							            return;
						        default:
						            break;
							  }
					}
				   
				}
			

			return;
			
		  
		
	   }
	
	
	
	
	
	
	//==========================================================
	
	public void MenuDecoration(Inventory inv , Material m) {
		List<Integer> l1 = new ArrayList<Integer>();
		l1.add(0);l1.add(1);l1.add(2);l1.add(3);l1.add(4);l1.add(5);l1.add(6);l1.add(7);l1.add(8);l1.add(9);l1.add(17);l1.add(18);l1.add(26);
		l1.add(27);l1.add(35);l1.add(36);l1.add(44);l1.add(45);l1.add(46);l1.add(47);l1.add(48);l1.add(49);l1.add(50);l1.add(51);l1.add(52);l1.add(53);
		
		for(int i = 0 ; i < 54; i++) {
			if(l1.contains(i)) {
				inv.setItem(i, new ItemStack(m));
			
			}
		}
		return;
	}
	
	public void PagsCreation(Player player,Inventory inv,List<ItemStack> itemlist) {
			
			if(itemlist.isEmpty()) {
				return;
			}
		 
			
			int maxpag = 22;
		    int pags = itemlist.size(); //para tienda con pags
		    double resultado = pags / maxpag;//SE SETEA LA NUEVA GENERACION DE PAGS, A LLENAR 21 NUEVA PAGINA 22
		    long pr = 0;
		    pr = Math.round(resultado);
		    pr = pr + 1;
		
		  	int pagactual = plugin.getPags().get(player);
		  	int max = ((maxpag * (pagactual-1)) + (maxpag-1));
		  	
		  	
			ItemStack it = new ItemStack(Material.BOOK);
			ItemMeta meta = it.getItemMeta();
			meta.setDisplayName(""+ChatColor.GREEN+ChatColor.BOLD+"PAGINA:" +pagactual+"/"+(pr+1));
			it.setItemMeta(meta);
			
			// se coloca +1 por que pr antes de llegar al borde da 0 + 1 da como resultado pag 1
			
			
			
			//significa que tiene mas paginas si es mayor 
			if((pr) > pagactual){
				inv.setItem(53, Items.ADELANTE.getValue());
			}
			
			//2
			// tienes una pag que regresar 
			if(pagactual >= 2) {
				inv.setItem(45, Items.ATRAS.getValue());
			}
			//dentro de esta lista puede haber muchos items del 1 ................ etc 
			// el for en base al calculo previo de paginas leera la lista hasta el tope
			List<ItemStack> itemsgotomenu = new ArrayList<ItemStack>();
			
			for(int i = (maxpag * (pagactual-1)) ; i < itemlist.size();i++) {
				ItemStack itemoflist = itemlist.get(i);
				itemsgotomenu.add(itemoflist);
				
				if(i == max) {
					break;
				}
			}
			
			//inicio en slot (esto tomando en cuenta la decoracion)
			int slot = 10;
			//se recorre para poder llenar solo los espacios mencionados
			for(int r = 0 ; r<itemsgotomenu.size();r++) {
				
				//testa si termino en el ultimo slot por llenar y pasa a la siguiente fila
				if(slot == 17 || slot == 26) {
					slot = slot + 2;
				}
				
				//romp
				if(slot == 35) {
					break;
				}
				inv.setItem(slot, itemsgotomenu.get(r));
				slot++;
			}
			
		 
	}
	
	public void ShowObjetives(Player player) {
		
		plugin.getPags().put(player, 1);
		PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
		GameObjetivesMG gomg = gi.getGameObjetivesMg();
		if(!gomg.hasMapObjetives()) {
			player.sendMessage(ChatColor.RED+"El Mapa no tiene Habilitado los Objetivos.");
			return;
		}
		
		
		List<ObjetivesMG> l1 = gi.getGameObjetivesMg().getObjetives();
		
		if(l1.isEmpty()) {
			player.sendMessage(ChatColor.RED+"Este Mapa no incluye Objetivos.");
			return;
		}
		
		
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.RED+ChatColor.BOLD+"OBJETIVOS");
		MenuDecoration(inv,Material.BLACK_STAINED_GLASS_PANE);
		
		List<ObjetivesMG> pr = new ArrayList<>();
		List<ObjetivesMG> se = new ArrayList<>();
		List<ObjetivesMG> host = new ArrayList<>();
		
		ItemStack it = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
		ItemStack it2 = new ItemStack(Material.GOLDEN_APPLE);
		ItemStack obh = new ItemStack(Material.TNT);
		ItemStack info = new ItemStack(Material.BEACON);
		
		ItemMeta meta = info.getItemMeta();
		meta.setDisplayName(""+ChatColor.GREEN+ChatColor.BOLD+"INFORMACION TIPOS DE OBJETIVOS");
		List<String> list = new ArrayList<>();
		list.add("");
		list.add(""+ChatColor.GREEN+ChatColor.UNDERLINE+"Completo: "+ChatColor.GRAY+"El completar un Objetivo trae Recompensas.");
	
		list.add(""+ChatColor.RED+ChatColor.UNDERLINE+"Incompleto: "+ChatColor.GRAY+"Si el Objetivo no se cumple puede traer castigos. ");
		list.add(ChatColor.YELLOW+"(Opcional el Castigo)");
		list.add(""+ChatColor.WHITE+ChatColor.UNDERLINE+"En Espera: "+ChatColor.GRAY+"A la espera de cambios durante el Juego.");
		list.add(""+ChatColor.AQUA+ChatColor.UNDERLINE+"Desconocido: "+ChatColor.GRAY+"Este Objetivo no se sabra hasta mas adelante.");
		list.add(""+ChatColor.YELLOW+ChatColor.UNDERLINE+"Advertencia: "+ChatColor.GRAY+"Este Objetivo va en contra de todos");
		list.add(ChatColor.GRAY+"si se Completa puede haber un "+ChatColor.YELLOW+"Castigo leve.");
		list.add(""+ChatColor.DARK_RED+ChatColor.UNDERLINE+"Peligro: "+ChatColor.GRAY+"Este Objetivo va en contra de todos");
		list.add(ChatColor.GRAY+"si se Completa puede haber un"+ChatColor.RED+" Castigo Grave.");
		list.add(""+ChatColor.DARK_GRAY+ChatColor.UNDERLINE+"Concluido: "+ChatColor.GRAY+"El Objetivo cambia a "+ChatColor.DARK_GRAY+"Concluido"+ChatColor.GRAY+" cuando los Objetivos ");
		list.add(ChatColor.YELLOW+"Advertencia "+ChatColor.WHITE+"o "+ChatColor.RED+"Peligro "+ChatColor.GRAY+"se completan.");
		list.add(""+ChatColor.RED+ChatColor.UNDERLINE+"Cancelado: "+ChatColor.GRAY+"Los Objetivos pueden llegar a ser "+ChatColor.GOLD+"Cancelados");
		list.add(ChatColor.GRAY+"para evitar su Progreso.");
		list.add("");
		list.add(ChatColor.BLUE+"Algunos Objetivos se Completaran con ciertas Condiciones");
		list.add(ChatColor.BLUE+"asi que presta atencion a lo que te rodea, Suerte.");
		
		
		meta.setLore(list);
		
		info.setItemMeta(meta);
		
		
		for(ObjetivesMG ob : l1) {	
			if(ob.getPriority() <= 0) {
				host.add(ob);
			}else if(ob.getPriority() == 1){
				pr.add(ob);
			}else if(ob.getPriority() >= 2){
				se.add(ob);
			}
		}
		//31 para amenazas
		
	
		
		if(pr.isEmpty()) {
			inv.setItem(21,CreateTempItemWithList(it,""+ChatColor.RED+ChatColor.BOLD+"Sin Objetivos Primarios", pr));
		}else {
			inv.setItem(21,CreateTempItemWithList(it,""+ChatColor.GREEN+ChatColor.BOLD+"Objetivos Primarios", pr));
		}
		
		
		if(se.isEmpty()) {
			inv.setItem(23,CreateTempItemWithList(it2,""+ChatColor.RED+ChatColor.BOLD+"Sin Objetivos Secundarios", se));
		}else {
			inv.setItem(23, CreateTempItemWithList(it2,""+ChatColor.GREEN+ChatColor.BOLD+"Objetivos Secundarios", se));
		}
		
		
		if(host.isEmpty()) {
			inv.setItem(31,CreateTempItemWithList(obh,""+ChatColor.RED+ChatColor.BOLD+"Sin Objetivos Hostiles", host));
		}else {
			inv.setItem(31,CreateTempItemWithList(obh,""+ChatColor.GOLD+ChatColor.BOLD+"Objetivos Hostiles", host));
		}
		
		inv.setItem(13, info);
		inv.setItem(40, Items.CERRAR2.getValue());
		player.openInventory(inv);
		UpdateInventory2(player);
		
		 
	
	}
	
	public void ObjetivesPrimary(Player player) {
		PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetivesPrimary();
		List<ItemStack> li = new ArrayList<>();
		
		
		
		if(l.isEmpty()) {
			player.sendMessage(ChatColor.RED+"Este Mapa no incluye Objetivos Primarios.");
			return;
		}
		
		
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.GOLD+ChatColor.BOLD+"OBJETIVOS PRIMARIOS");
		MenuDecoration(inv,Material.BLACK_STAINED_GLASS_PANE);
	
		
		
		
		for(ObjetivesMG ob : l) {
			
			if(ob.getObjetiveStatusType() == ObjetiveStatusType.COMPLETE) {
				ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.GREEN+ChatColor.BOLD+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				for(int i = 0; i < descripobj.size();i++) {
					descrip.add(ChatColor.YELLOW+descripobj.get(i));
				}
				descrip.add(ChatColor.GREEN+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GREEN+ChatColor.BOLD+"COMPLETADO");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));

				List<Map.Entry<Player, Integer>> lis = getPlayerInteractions(ob.getPlayerInteractions());
				if(!lis.isEmpty()) {
					descrip.add(""+ChatColor.GREEN+ChatColor.BOLD+"Ayudaron");
					for(Map.Entry<Player, Integer> e : lis) {
						descrip.add(ChatColor.GRAY+"- "+ChatColor.GREEN+e.getKey().getName()+ChatColor.GOLD+": "+ChatColor.RED+e.getValue());
					}
				}
				
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.INCOMPLETE) {
				ItemStack item = new ItemStack(Material.RED_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
						descrip.add(ChatColor.YELLOW+descripobj.get(i));
					}
				}
				
				descrip.add(ChatColor.GREEN+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.RED+ChatColor.BOLD+"INCOMPLETO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.WAITING) {
				ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(ChatColor.RED+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(ChatColor.YELLOW+descripobj.get(i));
					}
				}
				descrip.add(ChatColor.AQUA+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"EN PROGRESO");
				List<Map.Entry<Player, Integer>> lis = getPlayerInteractions(ob.getPlayerInteractions());
				if(!lis.isEmpty()) {
					descrip.add(""+ChatColor.GREEN+ChatColor.BOLD+"Ayudaron");
					for(Map.Entry<Player, Integer> e : lis) {
						descrip.add(ChatColor.GREEN+"-"+ChatColor.AQUA+e.getKey().getName()+ChatColor.GOLD+" = "+ChatColor.RED+e.getValue());
					}
				}
				
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.UNKNOW) {
				ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.WHITE+ChatColor.BOLD+ChatColor.MAGIC+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(""+ChatColor.RED+ChatColor.MAGIC+descripobj.get(i));
					}
				}
				descrip.add(""+ChatColor.AQUA+ChatColor.MAGIC+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"EN PROGRESO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.CANCELLED) {
				ItemStack item = new ItemStack(Material.RED_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(""+ChatColor.GRAY+ChatColor.BOLD+descripobj.get(i));
					}
				}	
				descrip.add(""+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.RED+ChatColor.BOLD+"OBJETIVO CANCELADO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}

			
			
		}
		inv.setItem(41, Items.VOLVER2.getValue());
		inv.setItem(40, Items.CERRAR2.getValue());
		PagsCreation(player, inv, li);
		
		player.openInventory(inv);
		UpdateInventory2(player);
		return;
		
	}
	public void ObjetivesSecondary(Player player) {
		PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetivesSecondary();
		
		List<ItemStack> li = new ArrayList<>();
		
		if(l.isEmpty()) {
			player.sendMessage(ChatColor.RED+"Este Mapa no incluye Objetivos Secundarios.");
			return;
		}
		
		
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.GREEN+ChatColor.BOLD+"OBJETIVOS SECUNDARIOS");
		MenuDecoration(inv,Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		
		
		
		
		for(ObjetivesMG ob : l) {
			
			if(ob.getObjetiveStatusType() == ObjetiveStatusType.COMPLETE) {
				ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.GREEN+ChatColor.BOLD+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(ChatColor.YELLOW+descripobj.get(i));
					}
				}
				descrip.add(ChatColor.GREEN+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.GREEN+ChatColor.BOLD+"COMPLETADO");
				List<Map.Entry<Player, Integer>> lis = getPlayerInteractions(ob.getPlayerInteractions());
				if(!lis.isEmpty()) {
					descrip.add(""+ChatColor.GREEN+ChatColor.BOLD+"Ayudaron");
					for(Map.Entry<Player, Integer> e : lis) {
						descrip.add(ChatColor.GREEN+"-"+ChatColor.AQUA+e.getKey().getName()+ChatColor.GOLD+" = "+ChatColor.RED+e.getValue());
					}
				}
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.INCOMPLETE) {
				ItemStack item = new ItemStack(Material.RED_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(ChatColor.YELLOW+descripobj.get(i));
					}
				}
				descrip.add(ChatColor.GREEN+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.RED+ChatColor.BOLD+"INCOMPLETO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.WAITING) {
				ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.WHITE+ChatColor.BOLD+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(ChatColor.YELLOW+descripobj.get(i));
					}
				}
				descrip.add(ChatColor.AQUA+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"EN PROGRESO");
				List<Map.Entry<Player, Integer>> lis = getPlayerInteractions(ob.getPlayerInteractions());
				if(!lis.isEmpty()) {
					descrip.add(""+ChatColor.GREEN+ChatColor.BOLD+"Ayudaron");
					for(Map.Entry<Player, Integer> e : lis) {
						descrip.add(ChatColor.GREEN+"-"+ChatColor.AQUA+e.getKey().getName()+ChatColor.GOLD+" = "+ChatColor.RED+e.getValue());
					}
				}
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.UNKNOW) {
				ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.WHITE+ChatColor.BOLD+ChatColor.MAGIC+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(""+ChatColor.RED+ChatColor.MAGIC+descripobj.get(i));
					}
				}	
				descrip.add(""+ChatColor.AQUA+ChatColor.MAGIC+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"EN PROGRESO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.CANCELLED) {
				ItemStack item = new ItemStack(Material.RED_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(""+ChatColor.GRAY+ChatColor.BOLD+descripobj.get(i));
					}
				}
				descrip.add(""+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.RED+ChatColor.BOLD+ChatColor.MAGIC+"OBJETIVO  CANCELADO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}

			
			
		}
		inv.setItem(41, Items.VOLVER2.getValue());
		inv.setItem(40, Items.CERRAR2.getValue());
		PagsCreation(player, inv, li);
		
		player.openInventory(inv);
		UpdateInventory2(player);
		return;
		
	}
	
	
	public void ObjetivesHostile(Player player) {
		PlayerInfo p = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(p.getMapName());
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetivesHostile();
		List<ItemStack> li = new ArrayList<>();
		
		
		if(l.isEmpty()) {
			player.sendMessage(ChatColor.RED+"Este Mapa no incluye Objetivos Hostiles.");
			return;
		}
		
		 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.RED+ChatColor.BOLD+"OBJETIVOS HOSTILES");
		MenuDecoration(inv,Material.RED_STAINED_GLASS_PANE);
		
		
		
		
		for(ObjetivesMG ob : l) {
			
			if(ob.getObjetiveStatusType() == ObjetiveStatusType.UNKNOW) {
				ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.WHITE+ChatColor.BOLD+ChatColor.MAGIC+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				descrip.add(""+ChatColor.RED+ChatColor.MAGIC+ob.getDescription());
				descrip.add(""+ChatColor.AQUA+ChatColor.MAGIC+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+ChatColor.MAGIC+"EN PROGRESO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}
			if(ob.getObjetiveStatusType() == ObjetiveStatusType.WARNING) {
				ItemStack item = new ItemStack(Material.YELLOW_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.WHITE+ChatColor.BOLD+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
					descrip.add(ChatColor.RED+descripobj.get(i));
					}
				}
				descrip.add(ChatColor.AQUA+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.YELLOW+ChatColor.BOLD+"EN PROGRESO ADVERTENCIA OBJETIVO EN CONTRA ");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.DANGER) {
				ItemStack item = new ItemStack(Material.RED_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.WHITE+ChatColor.BOLD+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
						descrip.add(""+ChatColor.DARK_RED+ChatColor.BOLD+descripobj.get(i));
					}
				}
			
				descrip.add(""+ChatColor.AQUA+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.DARK_RED+ChatColor.BOLD+"EN PROGRESO UN OBJETIVO PELIGROSO EN CONTRA");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.CONCLUDED) {
				ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.WHITE+ChatColor.BOLD+ChatColor.MAGIC+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
						descrip.add(""+ChatColor.GRAY+ChatColor.BOLD+descripobj.get(i));
					}
				}
				descrip.add(""+ChatColor.AQUA+ChatColor.BOLD+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.GRAY+ChatColor.BOLD+ChatColor.MAGIC+"OBJETIVO EN CONTRA CONCLUIDO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}if(ob.getObjetiveStatusType() == ObjetiveStatusType.CANCELLED) {
				ItemStack item = new ItemStack(Material.RED_STAINED_GLASS);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+ob.getObjetiveName());
				List<String> descrip = new ArrayList<>();
				List<String> descripobj = ob.getDescription();
				if(!descripobj.isEmpty()) {
					for(int i = 0; i < descripobj.size();i++) {
						descrip.add(""+ChatColor.GRAY+ChatColor.BOLD+descripobj.get(i));
					}
				}
				descrip.add(""+ChatColor.RED+ChatColor.BOLD+"("+ob.getCurrentValue()+"/"+ob.getCompleteValue()+")");
				descrip.add(""+ChatColor.GOLD+ChatColor.BOLD+"["+getProgressBar(ob.getCurrentValue(), ob.getCompleteValue(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.GOLD+ChatColor.BOLD+"]");
				descrip.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(ob.getCurrentValue(),ob.getCompleteValue()));
				descrip.add(""+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+"OBJETIVO EN CONTRA CANCELADO");
				meta.setLore(descrip);
				item.setItemMeta(meta);
				li.add(item);
				
			}
			
			
		}
		inv.setItem(41, Items.VOLVER2.getValue());
		inv.setItem(40, Items.CERRAR2.getValue());
		PagsCreation(player, inv, li);
		
		player.openInventory(inv);
		UpdateInventory2(player);
		return;
		
	}
	
	public void GameReviveInv(Player player) {
		
		
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"REVIVIR");
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		String mapa = pl.getMapName();
		GameInfo gm = plugin.getGameInfoPoo().get(mapa);
		if(gm instanceof GameAdventure) {
			GameAdventure ga = (GameAdventure) gm;
			List<String> dead = ga.getDeadPlayers();
			if(dead.isEmpty()) {
				player.sendMessage(ChatColor.RED+"No hay ningun jugador que revivir.");
				return;
			}
			
			inv.setItem(49, Items.CERRAR2.getValue());
			List<ItemStack> items = new ArrayList<>();
			//int i = 0;
			for(String players : dead) {
				Player target = Bukkit.getServer().getPlayerExact(players);
				ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
				SkullMeta meta = (SkullMeta) head.getItemMeta();
				meta.setOwningPlayer(target);
				List<String> lore2 = new ArrayList<>();
			    lore2.add(ChatColor.GREEN+"Necesitas 35 diamantes para revivir a este jugador");
				meta.setLore(lore2);
				head.setItemMeta(meta);
				items.add(head);
//				if(i == 49)continue;
//				
//				inv.setItem(i,head);
//				i++;
//				if(i == 45) {
//					break;
//				}
			}
			PagsCreation(player,inv,items);
			MenuDecoration(inv,Material.BLACK_STAINED_GLASS_PANE);
			player.openInventory(inv);
		}
	}

	
	
	
	public void updateInventory(Player player) {
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
	 
		GameConditions gc = new GameConditions(plugin);
		TaskID = sh.scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
			
				if(player.getOpenInventory() == null || !isUpdateable(player,gc.isPlayerinGame(player))) {
					Bukkit.getScheduler().cancelTask(TaskID);
					return;
				}
			}
		}, 0L, 20L);
		
		
	}
	
	public boolean isUpdateable(Player player, boolean status) {
		
		if(status){
			return false;
		}
		
		if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("MENU DE MAPAS")) {
			

			
			 Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"MENU DE MAPAS");
			 MenuDecoration(inv,Material.BLACK_STAINED_GLASS_PANE);
			 
			   int puntaje = plugin.getItemMenuMg().size(); //para tienda con pags
			   double resultado = puntaje / 22;
			   long pr = 0;
			   pr = Math.round(resultado);
			 
			   int pagactual = plugin.getPags().get(player);
			  

				ItemStack it = new ItemStack(Material.BOOK);
				ItemMeta meta = it.getItemMeta();
				meta.setDisplayName(""+ChatColor.GREEN+ChatColor.BOLD+"PAGINA:" +pagactual+"/"+(pr+1));
				it.setItemMeta(meta);
				
				// se coloca +1 por que pr antes de llegar al borde da 0 + 1 da como resultado pag 1
				
				pr = pr + 1;
				
				//significa que tiene mas paginas si es mayor 
				if((pr) > pagactual){
					inv.setItem(53, Items.ADELANTE.getValue());
				}
				
				//2
				// tienes una pag que regresar 
				if(pagactual >= 2) {
					inv.setItem(45, Items.ATRAS.getValue());
				}
				
				List<ItemStack> itemlist = new ArrayList<ItemStack>();
			
				List<Map.Entry<String, ItemMenu>> list = new ArrayList<>(plugin.getItemMenuMg().entrySet());
				
				Collections.sort(list, new Comparator<Map.Entry<String, ItemMenu>>() {
					public int compare(Map.Entry<String, ItemMenu> e1, Map.Entry<String, ItemMenu> e2) {
						// e2 a e1 = de mayor a menor e1 a e2 = de menor a mayor
						return e1.getValue().getPosition() - e2.getValue().getPosition();
					}
				});
				
				for(Map.Entry<String, ItemMenu> e : list) {
					if(!e.getValue().isWorking()) continue; 
					itemlist.add(e.getValue().getUpdateLore(player));
				}
				
				 
				int slot = 10;
				
				//se recorre para poder llenar solo los espacios mencionados
				for(int r = 0 ; r<itemlist.size();r++) {
					
					//testa si termino en el ultimo slot por llenar y pasa a la siguiente fila
					if(slot == 17 || slot == 26) {
						slot = slot + 2;
					}
					
					//romp
					if(slot == 35) {
						break;
					}
					inv.setItem(slot, itemlist.get(r));
					slot++;
				}
				
				player.openInventory(inv);
			
			return true;
		}
		return false;
	}
	
	public void missionsMenu(Player player) {
		
		if(!plugin.getItemMenuMg().isEmpty()){
			 Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"MENU DE MAPAS");
			 MenuDecoration(inv,Material.BLACK_STAINED_GLASS_PANE);
			 
			   int puntaje = plugin.getItemMenuMg().size(); //para tienda con pags
			   double resultado = puntaje / 22;
			   long pr = 0;
			   pr = Math.round(resultado);
			 
			   int pagactual = plugin.getPags().get(player);
			  

				ItemStack it = new ItemStack(Material.BOOK);
				ItemMeta meta = it.getItemMeta();
				meta.setDisplayName(""+ChatColor.GREEN+ChatColor.BOLD+"PAGINA:" +pagactual+"/"+(pr+1));
				it.setItemMeta(meta);
				
				// se coloca +1 por que pr antes de llegar al borde da 0 + 1 da como resultado pag 1
				
				pr = pr + 1;
				
				//significa que tiene mas paginas si es mayor 
				if((pr) > pagactual){
					inv.setItem(53, Items.ADELANTE.getValue());
				}
				
				//2
				// tienes una pag que regresar 
				if(pagactual >= 2) {
					inv.setItem(45, Items.ATRAS.getValue());
				}
				
				List<ItemStack> itemlist = new ArrayList<ItemStack>();
			
				List<Map.Entry<String, ItemMenu>> list = new ArrayList<>(plugin.getItemMenuMg().entrySet());
				
				Collections.sort(list, new Comparator<Map.Entry<String, ItemMenu>>() {
					public int compare(Map.Entry<String, ItemMenu> e1, Map.Entry<String, ItemMenu> e2) {
						return e2.getValue().getPosition() - e1.getValue().getPosition();
					}
				});
				
				
				for(Map.Entry<String, ItemMenu> e : list) {
					if(!e.getValue().isWorking()) continue; 
					itemlist.add(e.getValue().getUpdateLore(player));
				}
				
				
				int slot = 10;
				
				//se recorre para poder llenar solo los espacios mencionados
				for(int r = 0 ; r<itemlist.size();r++) {
					
					//testa si termino en el ultimo slot por llenar y pasa a la siguiente fila
					if(slot == 17 || slot == 26) {
						slot = slot + 2;
					}
					
					//romp
					if(slot == 35) {
						break;
					}
					inv.setItem(slot, itemlist.get(r));
					slot++;
				}
				
				player.openInventory(inv);
				updateInventory(player);
		}else{
		  player.sendMessage(ChatColor.RED+"No hay Mapas Disponibles.");
			 
		}
		return;
	}
	
	
	public void UpdateInventory2(Player player) {
		

		
		  if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS")
			|| player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS PRIMARIOS")
			|| player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS SECUNDARIOS")
			|| player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS HOSTILES")
			) {
			  return;
		  }
		BukkitScheduler sh = Bukkit.getServer().getScheduler();
	
		TaskID2 = sh.scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if(!UpdateObjetivesMenu(player)) {
					Bukkit.getScheduler().cancelTask(TaskID2);
					//plugin.getPlayerReading().remove(player.getName());
					return;
				}
			
			
			}
			
			
		}, 0L, 20L);
		
		
	}
	
	
	public boolean UpdateObjetivesMenu(Player player) {
	
	  if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS")) {
			ShowObjetives(player);
			return true;
		}if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS PRIMARIOS")) {
			ObjetivesPrimary(player);
			return true;
		}if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS SECUNDARIOS")) {
			ObjetivesSecondary(player);
			return true;
		}if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS HOSTILES")) {
			ObjetivesHostile(player);
			return true;
		}
		return false;
	}
	
	
	public boolean isCustomInventory(Player player) {
		
		if(player.getOpenInventory() != null && player.getOpenInventory().getTopInventory().getHolder() == null) {
			return true;
		}
		return false;
	}
	
	
	
	//TODO STORE CHEST
	public void StoreChest(Player player,ItemStack item) {
		
		
		
		//CONDICIONAL PARA CHEQUEAR QUE COLOQUEN 
		// une al jugador al clickear en el menu de misiones
		
		GameConditions gc = new GameConditions(plugin);
			if(!gc.isPlayerinGame(player) && player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("MENU DE MAPAS")) {
				
				if(item.isSimilar(Items.ADELANTE.getValue())) {
					plugin.getPags().replace(player, plugin.getPags().get(player)+1);
					NewPag(player);
					return;
				}if(item.isSimilar(Items.ATRAS.getValue())) {
					plugin.getPags().replace(player, plugin.getPags().get(player)-1);
					NewPag(player);
					return;
				}
					
				if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
					
					
					List<String> list = item.getItemMeta().getLore();
					String name = ChatColor.stripColor(list.get(list.size()-1).replace("Codigo: ",""));
					System.out.println("("+name+")");
					if(gc.existMap(name)) {
						player.closeInventory();
						gc.mgJoinToTheGames(player, name);
						
					}else {
						player.sendMessage(ChatColor.RED+"Ese Mapa no existe.");
					}
				}
				
//				if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
//					
//					
//					String name = ChatColor.stripColor(item.getItemMeta().getDisplayName().replace(" ","_"));
//					
//					
//					
//					if(gc.ExistMap(name)) {
//						player.closeInventory();
//						gc.mgJoinToTheGames(player, name);
//						
//					}else {
//						player.sendMessage(ChatColor.RED+"Ese Mapa no existe.");
//					}
//					
//				}
				return;
			}
		
			
		
			if(item.getType() == Material.PLAYER_HEAD) {
				SkullMeta meta = (SkullMeta) item.getItemMeta();
				// @SuppressWarnings("deprecation")
				 String name = meta.getOwningPlayer().getName();
				
				GameIntoMap c = new GameIntoMap(plugin);
				c.GameRevivePlayer(player, name);
			}
		
			
			 if(item.isSimilar(Items.OBJETIVOS.getValue())) {
				 player.getInventory().addItem(Items.OBJETIVOSP.getValue());
				 return;
			 }
			 
			 
			if(item.isSimilar(Items.ESPADAENCAN1.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 5)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.ESPADAENCAN1P.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 5 Netherite.");
				}
	
			}	
			
			if(item.isSimilar(Items.STOREXPRESS.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 192)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.STOREXPRESSP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,192));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 192 de Lingotes de Hierro (3 Stacks de hierro)");
				}
			}
				
			if(item.isSimilar(Items.REFUERZOS.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 30)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.REFUERZOSP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 de Lingotes de Netherite ");
				}
			}
			if(item.isSimilar(Items.REFUERZOS2.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 20)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.REFUERZOS2P.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,20));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 64 de Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.JEDI.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 40)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.JEDIP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 de Lingotes de Netherite");
				}
			}
			if(item.isSimilar(Items.PALO.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 40)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.PALOP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Hierro");
				}
			}
			
			if(item.isSimilar(Items.ESPADADIAMANTE.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 9)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD,1));
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,9));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 9 Diamantes.");
				}
		   }
			
			if(item.isSimilar(Items.ESPADAHIERRO.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 5)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(new ItemStack(Material.IRON_SWORD,1));
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 5 Diamantes.");
				}
		    }
			
			if(item.isSimilar(Items.ESPADAORO.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 7)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(new ItemStack(Material.GOLDEN_SWORD,1));
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,7));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 7 Lingotes de Hierro");
				}
			}
			
			if(item.isSimilar(Items.ESPADAMADERA.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 1)) {
					player.getInventory().addItem(new ItemStack(Material.WOODEN_SWORD,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,1));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 1 Diamantes");
				}
			}
			if(item.isSimilar(Items.ESPADAPIEDRA.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 3)) {
					player.getInventory().addItem(new ItemStack(Material.STONE_SWORD,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,3));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 3 Diamantes");
				}
			}
			if(item.isSimilar(Items.ESPADANETHERITA.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 12)) {
					player.getInventory().addItem(new ItemStack(Material.NETHERITE_SWORD,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,12));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 12 Diamantes");
				}
			}
		
			if(item.isSimilar(Items.CHECKPOINT.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 20)) {
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().addItem(Items.CHECKPOINTP.getValue());
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,20));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 20 Lingotes de Netherite");
				}
			}
			if(item.isSimilar(Items.KATANA.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 62)) {
					player.getInventory().addItem(Items.KATANAP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,62));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 62 Lingotes de Neherite");
				}
			}
			if(item.isSimilar(Items.KATANA2.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 100)) {
					player.getInventory().addItem(Items.KATANA2P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,100));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 100 Lingotes de Neherite");
				}
			}
			
			if(item.isSimilar(Items.TRIDENTE.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD), 5)) {
					player.getInventory().addItem(new ItemStack(Material.TRIDENT,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.EMERALD,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 5 Esmeraldas");
				}
			}
			if(item.isSimilar(Items.TOTEM.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD), 30)) {
					player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.EMERALD,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Esmeraldas");
				}
			}
			if(item.isSimilar(Items.ESCUDO.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 20)) {
					player.getInventory().addItem(new ItemStack(Material.SHIELD,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,20));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 20 Lingotes de Hierro");
				}
			}
			
			if(item.isSimilar(Items.ESCUDO1.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 64)) {
					player.getInventory().addItem(Items.ESCUDO1P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,64));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 64 Lingotes de Hierro");
				}
			}
			
			if(item.isSimilar(Items.ESCUDO2.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 150)) {
					player.getInventory().addItem(Items.ESCUDO2P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,150));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 150 Lingotes de Hierro");
				}
			}
			
			if(item.isSimilar(Items.TRIDENTEE.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 64)) {
					player.getInventory().addItem(Items.TRIDENTEEP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,64));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 64 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.TRIDENTEE2.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 100)) {
					player.getInventory().addItem(Items.TRIDENTEE2P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,100));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 100 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.ARCOENCAN1.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 30)) {
					player.getInventory().addItem(Items.ARCOENCAN1P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.SOUL.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				 
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND_BLOCK), 10)) {
					player.getInventory().addItem(Items.SOULP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND_BLOCK,10));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 10 Bloques de Diamante");
				}
			}
			
			if(item.isSimilar(Items.DASH.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				 
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 25)) {
					player.getInventory().addItem(Items.DASHP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND_BLOCK,10));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 25 Lingotes de Netherite");
				}
			}
			
			
			
			if(item.isSimilar(Items.DEADBOW.getValue())) {
				
				if(hasSpaceinInventory(player)) return;
				
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 40)) {
					player.getInventory().addItem(Items.DEADBOWP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.DEADBOW2.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 110)) {
					player.getInventory().addItem(Items.DEADBOW2P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,110));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 110 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.ARCO.getValue())) {
				if(hasSpaceinInventory(player)) return;
					if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 20)) {
						player.getInventory().addItem(new ItemStack(Material.BOW,1));
						player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
						player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,20));
					}else {
						player.sendMessage(ChatColor.RED+"Necesitas 20 Lingotes de Hierro");
					}
				}
		
			if(item.isSimilar(Items.MEDICO.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 30)) {
					player.getInventory().addItem(Items.MEDICOP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.BALLESTA1.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 40)) {
					player.getInventory().addItem(Items.BALLESTA1P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Netherite");
				}
			}
			
			
			if(item.isSimilar(Items.BALLESTA2.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT), 64)) {
					player.getInventory().addItem(Items.BALLESTA2P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,64));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 64 Lingotes de Netherite");
				}
			}
			
			
			if(item.isSimilar(Items.BALLESTA.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 12)) {
					player.getInventory().addItem(new ItemStack(Material.CROSSBOW,1));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT,12));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 12 Lingotes de Oro");
				}
			}
			if(item.isSimilar(Items.FLECHA.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 3)) {
					player.getInventory().addItem(new ItemStack(Material.ARROW,5));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT,3));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 3 Lingotes de oro");
				}
			}
			if(item.isSimilar(Items.FLECHAESPECTRAL.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 4)) {
					player.getInventory().addItem(new ItemStack(Material.SPECTRAL_ARROW,5));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 5 Lingotes de oro");
				}
			}
			if(item.isSimilar(Items.MANZANA.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT),1)) {
					player.getInventory().addItem(new ItemStack(Material.APPLE,5));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,1));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 1 Lingotes de Hierro");
				}
			}if(item.isSimilar(Items.MANZANAOROENCANTADA.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD),35)) {
					player.getInventory().addItem(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE,3));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.EMERALD,35));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 35 Esmeraldas");
				}
			}
			
			if(item.isSimilar(Items.MANZANAORO.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD),10)) {
					player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,10));
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.EMERALD,10));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 10 Esmeraldas");
				}
			}
			
			if(item.isSimilar(Posion.HEALTH.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND),3)) {
					player.getInventory().addItem(Posion.HEALTHP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,5));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 3 Diamantes");
				}
			}
		
			if(item.isSimilar(Posion.SPEED.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND),20)) {
					player.getInventory().addItem(Posion.SPEEDP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,20));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 20 Diamantes");
				}
			}
			if(item.isSimilar(Posion.REGENER.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND),2)) {
					player.getInventory().addItem(Posion.REGENERP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,2));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 2 Diamantes");
				}
			}
			
			if(item.isSimilar(Posion.ABSOR.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND),25)) {
					player.getInventory().addItem(Posion.ABSORP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,25));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 25 Diamantes");
				}
			}
			
			if(item.isSimilar(Posion.RESIS.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),2)) {
					player.getInventory().addItem(Posion.RESISP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,2));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 2 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.GANCHO.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),30)) {
					player.getInventory().addItem(Items.GANCHOP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Lingotes de Netherite");
				}
			}
			if(item.isSimilar(Items.GANCHO2.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),50)) {
					player.getInventory().addItem(Items.GANCHO2P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,50));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 50 Lingotes de Netherite");
				}
			}
			if(item.isSimilar(Items.ESCU1.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),40)) {
					player.getInventory().addItem(Items.ESCU1P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.ESCU2.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),40)) {
					player.getInventory().addItem(Items.ESCU2P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.ESCU3.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),45)) {
					player.getInventory().addItem(Items.ESCU3P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,45));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 45 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.ESCU4.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),35)) {
					player.getInventory().addItem(Items.ESCU4P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,35));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 35 Lingotes de Netherite");
				}
			}
			if(item.isSimilar(Items.ESCU5.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),200)) {
					player.getInventory().addItem(Items.ESCU5P.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,200));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 200 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.ARROWL.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),64)) {
					player.getInventory().addItem(Items.ARROWLP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,64));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 64 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.BAZUKA.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),128)) {
					player.getInventory().addItem(Items.BAZUKAP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,64));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 128 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.COHETE.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),40)) {
					player.getInventory().addItem(Items.COHETEP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,40));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 40 Lingotes de Netherite");
				}
			}
			
			
			if(item.isSimilar(Items.RAINARROW.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),10)) {
					player.getInventory().addItem(Items.RAINARROWP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,10));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 10 Lingotes de Netherite");
				}
			}
			
			if(item.isSimilar(Items.BENGALAROJA.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),30)) {
					player.getInventory().addItem(Items.BENGALAROJAP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,30));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 30 Lingotes de Netherite");
				}
			}
			
			
			if(item.isSimilar(Items.BENGALAVERDE.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),25)) {
					player.getInventory().addItem(Items.BENGALAVERDEP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,25));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 25 Lingotes de Netherite");
				}
			}
			 
			if(item.isSimilar(Items.REVIVE.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND),25)) {
					player.getInventory().addItem(Items.REVIVEP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.DIAMOND,25));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 25 Diamantes");
				}
			}
			
			if(item.isSimilar(Items.BENGALAMARCADORA.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),10)) {
					player.getInventory().addItem(Items.BENGALAMARCADORAP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,10));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 10 Lingotes de Netherite");
				}
			}if(item.isSimilar(Items.TNT.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.NETHERITE_INGOT),3)) {
					player.getInventory().addItem(Items.TNTP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.NETHERITE_INGOT,3));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 3 Lingotes de Netherite");
				}
			}if(item.isSimilar(Items.MASCARAANTIGAS.getValue())) {
				if(hasSpaceinInventory(player)) return;
				if(player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT),64)) {
					player.getInventory().addItem(Items.MASCARAANTIGASP.getValue());
					player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 20.0F, 1F);
					player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT,64));
				}else {
					player.sendMessage(ChatColor.RED+"Necesitas 64 de Hierro");
				}
			}
			
			
			
			if(item.isSimilar(Items.ARMAS.getValue())) {
				StoreCreateWeaponsMelee(player);
			}
			
			if(item.isSimilar(Items.ARMAS2.getValue())) {
				StoreCreateWeaponsShooters(player);
			}
			
			if(item.isSimilar(Items.DEFENSA.getValue())) {
				StoreCreateShields(player);
			}
			
			if(item.isSimilar(Items.COMIDA.getValue())) {
				StoreCreateFood(player);
			}
			
			if(item.isSimilar(Items.ESPECIALES.getValue())) {
				StoreCreateSpecialWeapons(player);
			}
			
			if(item.isSimilar(Items.VOLVER.getValue()) || item.isSimilar(Items.VOLVER2.getValue())) {
				NewPagBack(player);
			}
			 
			if(item.isSimilar(Items.CERRAR.getValue()) || item.isSimilar(Items.CERRAR2.getValue())) {
				player.closeInventory();
			}
	
			
			if(item.isSimilar(Items.ADELANTE.getValue())) {
				plugin.getPags().replace(player, plugin.getPags().get(player)+1);
				NewPag(player);
			}if(item.isSimilar(Items.ATRAS.getValue())) {
				plugin.getPags().replace(player, plugin.getPags().get(player)-1);
				NewPag(player);
			}if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Objetivos Primarios")) {
				ObjetivesPrimary(player);
			}if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Objetivos Secundarios")) {
				ObjetivesSecondary(player);
			}if(item.hasItemMeta() && item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Objetivos Hostiles")) {
				ObjetivesHostile(player);
			}
				
			
			
			
	}
	
	public boolean hasSpaceinInventory(Player player) {
		 if(player.getInventory().firstEmpty() == -1) {
			 player.sendMessage(ChatColor.RED+"Tu Inventario esta lleno has Espacio.");
			  return true;
		 }
		 return false;
		 
	}
	
	//CHEQUEAR LUEGO
	public void CreateInvChest(Player player,Chest chest) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		chest.setCustomName(""+ChatColor.DARK_GREEN+ChatColor.BOLD+"TIENDA TEST");
		Inventory inv = chest.getInventory();
		
		
		//ItemStack item9 = new ItemStack(Material.LIME_STAINED_GLASS,1);
	
		inv.setItem(0, Items.ESPADAMADERA.getValue());
		inv.setItem(1,  Items.ESPADAPIEDRA.getValue());
		inv.setItem(2,  Items.ESPADAHIERRO.getValue());
		inv.setItem(3,  Items.ESPADAORO.getValue());
		inv.setItem(4,  Items.ESPADADIAMANTE.getValue());
		inv.setItem(7, Items.ESPADANETHERITA.getValue());
		inv.setItem(8, Items.ARCO.getValue());
		inv.setItem(9, Items.BALLESTA.getValue());
		inv.setItem(10, Items.FLECHA.getValue());
		
	}
	
	public void StoreCreate(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_GREEN+ChatColor.BOLD+"TIENDA");
		MenuDecoration(inv,Material.BLACK_STAINED_GLASS_PANE);
		PlayerInfo pl = plugin.getPlayerInfoPoo().get(player);
		GameInfo gi = plugin.getGameInfoPoo().get(pl.getMapName());
		
		inv.setItem(11, Items.ARMAS.getValue());
		inv.setItem(13, Items.ARMAS2.getValue());
		inv.setItem(15, Items.DEFENSA.getValue());
		inv.setItem(21, Items.COMIDA.getValue());
		inv.setItem(23, Items.ESPECIALES.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		
		List<ObjetivesMG> l = gi.getGameObjetivesMg().getObjetives();
		if(!l.stream().filter(o -> o.getObjetiveName().equals("Mapa Con Objetivos Borrados")).findFirst().isPresent()) {
			inv.setItem(31, Items.OBJETIVOS.getValue());
		}
		//31
		// slot 49 es el centro verticalmente 
		//inv.setItem(49, Items.CERRAR.getValue());
		//tope es 53 no hay 54
		//inv.setItem(50, item9);
	
		
		player.openInventory(inv);
	}
	 
	
	public void StoreCreateWeaponsMelee(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.RED+ChatColor.BOLD+"ESPADAS");
		
		MenuDecoration(inv,Material.RED_STAINED_GLASS_PANE);
	
		
		inv.setItem(10, Items.ESPADAMADERA.getValue());
		inv.setItem(11,  Items.ESPADAPIEDRA.getValue());
		inv.setItem(12,  Items.ESPADAHIERRO.getValue());
		inv.setItem(13,  Items.ESPADAORO.getValue());
		inv.setItem(14,  Items.ESPADADIAMANTE.getValue());
		inv.setItem(15, Items.ESPADANETHERITA.getValue());
		inv.setItem(16, Items.ESPADAENCAN1.getValue());
		inv.setItem(19, Items.KATANA.getValue());
		inv.setItem(20, Items.TRIDENTE.getValue());
		inv.setItem(21, Items.KATANA2.getValue());
		inv.setItem(22, Items.TRIDENTEE.getValue());
		inv.setItem(23, Items.TRIDENTEE2.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		player.openInventory(inv);
		
	}
  
	public void StoreCreateWeaponsShooters(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.GREEN+ChatColor.BOLD+"ARCOS Y BALLESTAS");
		MenuDecoration(inv,Material.LIME_STAINED_GLASS_PANE);
		
		
		
		inv.setItem(10, Items.ARCO.getValue());
		inv.setItem(11, Items.BALLESTA.getValue());
		inv.setItem(12, Items.ARCOENCAN1.getValue());
		inv.setItem(13, Items.BALLESTA1.getValue());
		inv.setItem(14, Items.BALLESTA2.getValue());
		inv.setItem(15, Items.FLECHA.getValue());
		inv.setItem(16, Items.FLECHAESPECTRAL.getValue());
		inv.setItem(19, Items.DEADBOW.getValue());
		inv.setItem(20, Items.DEADBOW2.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		player.openInventory(inv);
		
	}
	
	public void StoreCreateShields(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.BLUE+ChatColor.BOLD+"DEFENSA");
		MenuDecoration(inv,Material.BLUE_STAINED_GLASS_PANE);
		
		
		inv.setItem(10, Items.ESCU1.getValue());
		inv.setItem(11, Items.ESCU2.getValue());
		inv.setItem(12, Items.ESCU3.getValue());
		inv.setItem(13, Items.ESCU4.getValue());
		inv.setItem(14, Items.ESCU5.getValue());
		inv.setItem(15, Items.ESCUDO.getValue());
		inv.setItem(16, Items.TOTEM.getValue());
		inv.setItem(19, Items.ESCUDO1.getValue());
		inv.setItem(20, Items.ESCUDO2.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		
		player.openInventory(inv);
	}
	
	public void StoreCreateFood(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.AQUA+ChatColor.BOLD+"COMIDA Y POSIONES");
		
		MenuDecoration(inv,Material.LIGHT_BLUE_STAINED_GLASS_PANE);
		
		
		inv.setItem(10, Items.MANZANAORO.getValue());
		inv.setItem(11, Items.MANZANA.getValue());
		inv.setItem(12, Posion.REGENER.getValue());
		inv.setItem(13, Posion.HEALTH.getValue());
		inv.setItem(14, Posion.SPEED.getValue());
		inv.setItem(15, Posion.ABSOR.getValue());
		inv.setItem(16, Posion.RESIS.getValue());
		inv.setItem(19, Items.MANZANAOROENCANTADA.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		
		player.openInventory(inv);
	}
	
	public void StoreCreateSpecialWeapons(Player player) {
		//creas el inventario l
		//los itemstacks estan como ejemplo puesto que sale mejor hacer o dedicar una clase para items 
		Inventory inv = Bukkit.createInventory(null,54,""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"ESPECIALES");
		
		MenuDecoration(inv,Material.PURPLE_STAINED_GLASS_PANE);
		PlayerInfo pi = plugin.getPlayerInfoPoo().get(player);
		GameConditions gc = new GameConditions(plugin);
		
		
		inv.setItem(10, Items.PALO.getValue());
		
		inv.setItem(11, Items.REFUERZOS.getValue());
		inv.setItem(12, Items.REFUERZOS2.getValue());
		inv.setItem(13, Items.STOREXPRESS.getValue());
		inv.setItem(14, Items.JEDI.getValue());
		//inv.setItem(30, levitar);
		inv.setItem(15, Items.MEDICO.getValue());
		inv.setItem(16, Items.CHECKPOINT.getValue());
		inv.setItem(19, Items.GANCHO.getValue());
		inv.setItem(20, Items.ARROWL.getValue());
		inv.setItem(21, Items.BAZUKA.getValue());
		inv.setItem(22, Items.COHETE.getValue());
		inv.setItem(23, Items.GANCHO2.getValue());
		inv.setItem(24, Items.BENGALAROJA.getValue());
		inv.setItem(25, Items.BENGALAVERDE.getValue());
		inv.setItem(28, Items.RAINARROW.getValue());
		if(gc.isEnabledReviveSystem(pi.getMapName())) {
			inv.setItem(29, Items.REVIVE.getValue());
		}
		inv.setItem(30, Items.BENGALAMARCADORA.getValue());
		inv.setItem(31, Items.TNT.getValue());
		inv.setItem(32, Items.MASCARAANTIGAS.getValue());
		inv.setItem(33, Items.SOUL.getValue());
		inv.setItem(34, Items.DASH.getValue());
		inv.setItem(40, Items.CERRAR.getValue());
		inv.setItem(41, Items.VOLVER.getValue());
		
		player.openInventory(inv);
	}
	
	

	
	//NUEVA PAGINA EN UNA PAGINA SECUNDARIA   
	public void NewPag(Player player) {
		if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("MENU DE MAPAS")) {
			missionsMenu(player);
		}else if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS PRIMARIOS")) {
			ObjetivesPrimary(player);
		}else if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS SECUNDARIOS")) {
			ObjetivesSecondary(player);
		}else if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("REVIVIR")) {
			GameReviveInv(player);
		}else if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS HOSTILES")) {
			ObjetivesHostile(player);
		}
	}
	
	//NUEVA PAGINA EN PAG PRINCIPAL
	public void NewPagBack(Player player) {
		List<String> l = new ArrayList<String>();
		 
		l.add("ESPADAS");l.add("ARCOS Y BALLESTAS");l.add("DEFENSA");l.add("COMIDA Y POSIONES");l.add("ESPECIALES");
		if(player.getOpenInventory() != null && l.contains(ChatColor.stripColor(player.getOpenInventory().getTitle()))) {
			StoreCreate(player);
		}if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS PRIMARIOS")) {
			ShowObjetives(player);
		}if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS SECUNDARIOS")) {
			ShowObjetives(player);
		}if(player.getOpenInventory() != null && ChatColor.stripColor(player.getOpenInventory().getTitle()).equals("OBJETIVOS HOSTILES")) {
			ShowObjetives(player);
		}
	}
	
//	⚠
	public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,ChatColor notCompletedColor) {
        float percent = (float) current/max;
        int progressBars = (int) (totalBars * percent);
 
        return Strings.repeat(""+ completedColor +ChatColor.BOLD + symbol, progressBars) + Strings.repeat("" + notCompletedColor +ChatColor.BOLD+ symbol, totalBars - progressBars);
   }
	
	public ItemStack CreateTempItemWithList(ItemStack item , String Nombre , List<ObjetivesMG> l) {
		List<String> l2 = new ArrayList<>();
		ItemMeta meta = item.getItemMeta();
		
		 
		if(!l.isEmpty()) {
			meta.setDisplayName(Nombre);
			for(int i=0;i<l.size();i++) {
				if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.COMPLETE) {
					l2.add(""+ChatColor.GREEN+ChatColor.BOLD+l.get(i).getObjetiveName()+ChatColor.DARK_GRAY+" Completo "+ChatColor.GREEN+ChatColor.STRIKETHROUGH+"("+l.get(i).getCurrentValue()+"/"+l.get(i).getCompleteValue()+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.INCOMPLETE) {
					l2.add(""+ChatColor.RED+ChatColor.BOLD+l.get(i).getObjetiveName()+ChatColor.DARK_GRAY+" Incompleto "+ChatColor.RED+ChatColor.STRIKETHROUGH+"("+l.get(i).getCurrentValue()+"/"+l.get(i).getCompleteValue()+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.WAITING) {
					l2.add(""+ChatColor.WHITE+ChatColor.BOLD+l.get(i).getObjetiveName()+ChatColor.DARK_GRAY+" En Espera "+ChatColor.GOLD+"("+l.get(i).getCurrentValue()+"/"+l.get(i).getCompleteValue()+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.UNKNOW) {
					l2.add(""+ChatColor.WHITE+ChatColor.MAGIC+l.get(i).getObjetiveName()+ChatColor.DARK_GRAY+" Desconocido "+ChatColor.GOLD+ChatColor.MAGIC+"("+l.get(i).getCurrentValue()+"/"+l.get(i).getCompleteValue()+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.WARNING) {
					l2.add(""+ChatColor.YELLOW+ChatColor.BOLD+l.get(i).getObjetiveName()+ChatColor.DARK_GRAY+" Advertencia "+ChatColor.GOLD+"("+l.get(i).getCurrentValue()+"/"+l.get(i).getCompleteValue()+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.DANGER) {
					l2.add(""+ChatColor.DARK_RED+ChatColor.BOLD+l.get(i).getObjetiveName()+ChatColor.DARK_GRAY+" Peligro "+ChatColor.GOLD+"("+l.get(i).getCurrentValue()+"/"+l.get(i).getCompleteValue()+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.CONCLUDED) {
					l2.add(""+ChatColor.DARK_GRAY+ChatColor.BOLD+l.get(i).getObjetiveName()+ChatColor.DARK_GRAY+" Concluido "+ChatColor.GOLD+"("+l.get(i).getCurrentValue()+"/"+l.get(i).getCompleteValue()+")");
				}else if(l.get(i).getObjetiveStatusType() == ObjetiveStatusType.CANCELLED) {
					l2.add(""+ChatColor.RED+ChatColor.BOLD+ChatColor.STRIKETHROUGH+l.get(i).getObjetiveName()+ChatColor.DARK_GRAY+" Cancelado "+ChatColor.GOLD+ChatColor.STRIKETHROUGH+"("+l.get(i).getCurrentValue()+"/"+l.get(i).getCompleteValue()+")");
				}
				
			}
			
			if(l2.isEmpty()) {
				l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"Cargando...");
 
			 }
			
				GameConditions gc = new GameConditions(plugin);
				l2.add("");
			    l2.add(""+ChatColor.GREEN+ChatColor.BOLD+"Clickeame Para ver mas Detalles.");
			    l2.add(""+ChatColor.WHITE+ChatColor.BOLD+"["+getProgressBar(gc.getAmountOfObjetivesComplete(l), l.size(), 20, '|', ChatColor.GREEN, ChatColor.RED)+ChatColor.WHITE+ChatColor.BOLD+"]");
			    l2.add(ChatColor.GOLD+"Hay un Progreso del "+ChatColor.GREEN+Porcentage(gc.getAmountOfObjetivesComplete(l), l.size()));
			  
			
			
		    meta.setLore(l2);
			item.setItemMeta(meta);
		}else {
			 
			  item.setType(Material.BARRIER);
			  meta.setDisplayName(Nombre);
			  l2.add(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"Sin Datos");
			  l2.add(""+ChatColor.YELLOW+ChatColor.BOLD+"No hay Sistema...");
			  meta.setLore(l2);
			  item.setItemMeta(meta);
		}
	
		
		
		return item;
	}
	
	public List<Map.Entry<Player, Integer>> getPlayerInteractions(HashMap<Player,Integer> vals){
   		
   		
   		List<Map.Entry<Player, Integer>> list = new ArrayList<>(vals.entrySet());

		
		Collections.sort(list, new Comparator<Map.Entry<Player, Integer>>() {
			public int compare(Map.Entry<Player, Integer> e1, Map.Entry<Player, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});
   		
   		return list;
   	}
	
	public String Porcentage(int current , int max ) {
		float percent = (float) current/max*100;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		return nf.format(percent)+"%";
	}

	public FileConfiguration getGameConfig(String name) {
		YamlFilePlus file = new YamlFilePlus(plugin);
		FileConfiguration config = file.getSpecificYamlFile("Maps",name);
		//u.saveSpecificl(name);
	    return config;
	}
	
}

package me.nao.generalinfo.mg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nao.main.mg.Minegame;
import me.nao.utils.mg.Utils;


public class ItemMenu {

	private String code , displayname , datetime , permissionforplay;
	private List<String> lore , permissionmessage;
	private boolean working , enchanted, locked, maintenance, time , ranked , permission;
	private ItemStack item;
	private Minegame plugin;
	private int position;
	
	
	public ItemMenu(Minegame plugin) {
		this.plugin = plugin;
	}

	public String getCode() {
		return code;
	}
	
	public String getDisplayname() {
		return displayname;
	}
	
	public String getDatetime() {
		return datetime;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public boolean isWorking() {
		return working;
	}
	
	public boolean isEnchanted() {
		return enchanted;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	public boolean hasMaintenance() {
		return maintenance;
	}
	
	public boolean hasTime() {
		return time;
	}
	
	public boolean isRanked() {
		return ranked;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setDisplayname(String displayname) {
		this.displayname = Utils.colorTextChatColor(displayname);
	}
	
	
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
	public void setLore(List<String> lore) {
		this.lore = lore;
	}
	
	
	public ItemStack getUpdateLore(Player player) {
		ItemStack itemg = getItem();
		ItemMeta meta = itemg.getItemMeta();
		
		
		
		meta.setDisplayName(getDisplayname());
		
		if(isEnchanted()) {
			meta.addEnchant(Enchantment.LOOTING, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		
		List<String> itemlore = new ArrayList<>();
		itemlore.add("");
		if(!getLore().isEmpty()) {
			for(int i = 0 ; i < getLore().size();i++) {
				itemlore.add(ChatColor.translateAlternateColorCodes('&',getLore().get(i)));
			}
		}
		itemlore.add("");
		
		if(hasMaintenance()) {
			itemlore.add(""+ChatColor.YELLOW+ChatColor.BOLD+ChatColor.ITALIC+"X "+ChatColor.RED+ChatColor.BOLD+ChatColor.UNDERLINE+"EN MANTENIMIENTO"+ChatColor.YELLOW+ChatColor.BOLD+ChatColor.ITALIC+" X");
			//itemlore.add(ChatColor.GOLD+" Todos los Mapas estan Bloqueados. ");
		}
		if(isLocked()) {
			itemlore.add(" "+ChatColor.GOLD+ChatColor.BOLD+"MAPA: "+ChatColor.RED+ChatColor.BOLD+"DESHABILITADO.");
		}
		
		if(hasPermission()) {
			if(!player.hasPermission(getPermissionforplay())) {
				itemlore.add(""+ChatColor.RED+ChatColor.BOLD+"BLOQUEADO");
				
					List<String> perml = getPermissionMessage();
					if(!perml.isEmpty()) {
						for(int i =0;i< perml.size();i++) {
							itemlore.add(ChatColor.translateAlternateColorCodes('&', perml.get(i)).replace("%player%",player.getName()));
						}
					}else {
						itemlore.add(ChatColor.RED+"Mapa Bloqueado: "+ChatColor.GOLD+"Necesitas un Permiso para Acceder.");
					}
			}else {
				itemlore.add(""+ChatColor.GREEN+ChatColor.BOLD+"DESBLOQUEADO");
			}
		}
		
		if(isRanked()) {
			itemlore.add(""+ChatColor.GREEN+ChatColor.MAGIC+"[[["+ChatColor.DARK_PURPLE+ChatColor.BOLD+" RANKED MAP "+ChatColor.GREEN+ChatColor.MAGIC+"]]]");
		}
		itemlore.add("");
		
		if(plugin.getGameInfoPoo().containsKey(getCode())) {
			itemlore.add(""+ChatColor.GREEN+ChatColor.BOLD+"JUGADORES: "+ChatColor.RED+ChatColor.BOLD+plugin.getGameInfoPoo().get(getCode()).getParticipants().size());
			itemlore.add(""+ChatColor.WHITE+ChatColor.BOLD+"ESPECTADORES: "+ChatColor.RED+ChatColor.BOLD+plugin.getGameInfoPoo().get(getCode()).getSpectators().size());
			itemlore.add(""+ChatColor.WHITE+ChatColor.BOLD+"ESTADO: "+plugin.getGameInfoPoo().get(getCode()).getGameStatus().getValue());


		}else {
			itemlore.add(""+ChatColor.GREEN+ChatColor.BOLD+"JUGADORES: "+ChatColor.RED+ChatColor.BOLD+"0");
			itemlore.add(""+ChatColor.WHITE+ChatColor.BOLD+"ESPECTADORES: "+ChatColor.RED+ChatColor.BOLD+"0");
			itemlore.add(""+ChatColor.GOLD+ChatColor.BOLD+"ESTADO: "+ChatColor.WHITE+ChatColor.BOLD+"ESPERANDO");
		}
		
		itemlore.add(ChatColor.WHITE+"Codigo: "+ChatColor.GREEN+getCode());
		meta.setLore(itemlore);
		itemg.setItemMeta(meta);
		return itemg;
	}
	
	public void setWorking(boolean working) {
		this.working = working;
	}
	
	public void setEnchanted(boolean enchanted) {
		this.enchanted = enchanted;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public void setMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}
	
	public void setTime(boolean time) {
		this.time = time;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public void setRanked(boolean ranked) {
		this.ranked = ranked;
	}

	public List<String> getPermissionMessage() {
		return permissionmessage;
	}

	public boolean hasPermission() {
		return permission;
	}

	public void setPermissionmessage(List<String> permissionmessage) {
		this.permissionmessage = permissionmessage;
	}

	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	public String getPermissionforplay() {
		return permissionforplay;
	}

	public void setPermissionforplay(String permissionforplay) {
		this.permissionforplay = permissionforplay;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}


	
	
	
	
	
	
	
}

package me.nao.landmine.enums.lm;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ItemsIs {

	
    REMOVER(""+ChatColor.GOLD+ChatColor.BOLD+"DESACTIVE LANDMINE", Material.SHEARS,null,1, ChatColor.GREEN+"Nota:"+ChatColor.RED+" Desactive LandMines"),

    DETECTOR(""+ChatColor.GOLD+ChatColor.BOLD+"LANDMINE DETECTOR", Material.COMPASS,null,1, ChatColor.GREEN+"Nota:"+ChatColor.RED+" Detect if a Block is a LandMine.");

	
	
    public ItemStack item;
    
    
	@SuppressWarnings("deprecation")
	private ItemsIs(String nombre, Material material,String encant , int amount, String ...lore){
        ItemStack item = new ItemStack(material,amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(nombre);
        List<String> lore2 = new ArrayList<>();
        
      // luck,1;fire,5;
        
     

       
      
     // Material.END_CRYSTAL;
        
        if(lore != null) {
        	 for(String linea : lore){
                 lore2.add(linea);
             }
             //lore.add(""+ChatColor.GOLD+ChatColor.BOLD+"Valor:"+ChatColor.GREEN+ChatColor.BOLD+"+ 3");|
             meta.setLore(lore2);
        }
       
        if(encant != null) {
        	if(encant.contains("/")) {
        		 String[] seccion = encant.split("/");
               	for(int i =0;i<seccion.length;i++) {
                 		String seccion2 = seccion[i];
                 		
                 		String[] f = seccion2.split(",");
                 		String encan = f[0];
      			    	int encanlvl = Integer.valueOf(f[1]);
                 	
      				meta.addEnchant(Enchantment.getByName(encan.toUpperCase()), encanlvl, true);
                }
        	}else {
        		
        		String[] f = encant.split(",");
         		String encan = f[0];
			     	int encanlvl = Integer.valueOf(f[1]);
        		meta.addEnchant(Enchantment.getByName(encan.toUpperCase()), encanlvl, true);
        	}
        	
        }
       
  // meta.addEnchant(Enchantment.DAMAGE_ALL,1, true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        this.item = item;
    }
 
    public ItemStack getValue(){
        return this.item;
    }
	
}

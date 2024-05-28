package me.nao.events;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

//import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;




public class ItemNBT {
	
	
	public static ItemStack getItemNBT(Material item , String status , String data ) {
		
		ItemStack item2 = new ItemStack(item);
		
		NBTItem nb = new NBTItem(item2);
		List <String> l = nb.getStringList(status);
		
		
		
		if(data.contains(",")) {
			 String[] datos = data.split(",");
			  for(int i = 0 ; i< datos.length ;i ++) {
				  if(Material.matchMaterial(datos[i]) == null)continue;
				  l.add("minecraft:"+datos[i]);
			  }
			  nb.applyNBT(item2);
		}else {
			if(Material.matchMaterial(data) != null || !l.contains(data)) {
				l.add("minecraft:"+data);
				  nb.applyNBT(item2);
			}
		}
		  
		  return item2;
	}
	
	public static ItemStack getItemNBT2(ItemStack it, String status , String data ) {
		
		ItemStack item2 = it;
		
		NBTItem nb = new NBTItem(item2);
		List <String> l = nb.getStringList(status);
		
		if(data.contains(",")) {
			 String[] datos = data.split(",");
			  for(int i = 0 ; i< datos.length ;i ++) {
				  if(Material.matchMaterial(datos[i]) == null)continue;
				  l.add("minecraft:"+datos[i]);
			  }
			  nb.applyNBT(item2);
		}else {
			if(Material.matchMaterial(data) != null || !l.contains(data)) {
				l.add("minecraft:"+data);
				  nb.applyNBT(item2);
			}
		}
		
		 
		  
		  return item2;
	}
	
    public static ItemStack getBreakWoolTag(Material item , String status , String data ) {
     // net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(new ItemStack(Material.valueOf(material.toUpperCase()), 1));
     // net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(new ItemStack(item, 1));
   
      //NBTTagList idsTag = new NBTTagList();
      
        List<String> blocks = new ArrayList<String>();
        
        
		  String[] datos = data.split(",");
		  for(int i = 0 ; i< datos.length ;i ++) {
			  
			  blocks.add(datos[i]);
			 
		  }
        
        //blocks.add("stone");
       // blocks.add("dirt");

        for(int i = 0;i<blocks.size();i++) {
        // idsTag.add(NBTTagString.a("minecraft:"+blocks.get(i)));
        }
        
       
   
       //NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
   
       // tag.set("CanDestroy", idsTag);
      //  tag.set(status, idsTag);
   
      //  stack.setTag(tag);
    ItemStack it = new ItemStack(Material.DIAMOND);
        return it;
       // return CraftItemStack.asBukkitCopy(stack);
    }
    
    
    

}

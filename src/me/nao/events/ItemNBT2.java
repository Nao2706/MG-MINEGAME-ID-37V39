package me.nao.events;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

//import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;







public class ItemNBT2 {
	
	
	
	
    public static ItemStack getBreakWoolTag(ItemStack it , String status , String data ) {
       //net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(new ItemStack(Material.valueOf(material.toUpperCase()), 1));
      // net.minecraft.world.item.ItemStack stack = CraftItemStack.asNMSCopy(it);
   
       // NBTTagList idsTag = new NBTTagList();
      
        List<String> blocks = new ArrayList<String>();
        
        
		  String[] datos = data.split(",");
		  for(int i = 0 ; i< datos.length ;i ++) {
			  
			  blocks.add(datos[i]);
			 
		  }
        
        //blocks.add("stone");
       // blocks.add("dirt");

        for(int i = 0;i<blocks.size();i++) {
       //  idsTag.add(NBTTagString.a("minecraft:"+blocks.get(i)));
        }
        
       
   
       // NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
   
       // tag.set("CanDestroy", idsTag);
       // tag.set(status, idsTag);
   
       // stack.setTag(tag);
    
        
        //return CraftItemStack.asBukkitCopy(stack);
        ItemStack it2 = new ItemStack(Material.DIAMOND);
        return it2;
    }
    
    
    

}

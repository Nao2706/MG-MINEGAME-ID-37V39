package me.nao.events.mg;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Chest;
//import org.bukkit.block.data.BlockData;
//import org.bukkit.block.data.type.Chest;
//import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
//import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;




public class ItemNBT {
	
	//APARTIR DE MINECRAFT 1.20.5 CAMBIO EL TEMA DE LAS NBT EN LOS ITEMS
	@SuppressWarnings("deprecation")
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
	
	//APARTIR DE MINECRAFT 1.20.5 CAMBIO EL TEMA DE LAS NBT EN LOS ITEMS MAS INFO EN DISCORD DE NBTAPI
	@SuppressWarnings("deprecation")
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
	
	
	//APARTIR DE MINECRAFT 1.20.5 CAMBIO EL TEMA DE LAS NBT EN LOS ITEMS
	public static ItemStack getItemNBTnew(Material item , String status , String data ) {
		
		ItemStack item2 = new ItemStack(item);
		
		List <String> blocks = new ArrayList<>();
		
		if(data.contains(",")) {
			 String[] datos = data.split(",");
			  for(int i = 0 ; i< datos.length ;i ++) {
				  if(Material.matchMaterial(datos[i]) == null)continue;
				  blocks.add(datos[i]);
			  }
			  
		}else {
			if(Material.matchMaterial(data) != null || !blocks.contains(data)) {
				blocks.add(data);
			}
		}
		
		
		NBT.modifyComponents(item2,nbt ->{ 
			
			// nbt.setString("minecraft:can_break","{blocks:[stone,dirt]}");
			// nbt.setString("minecraft:can_break","{\"blocks\":[\"stone\",\"dirt\"]}");
		
			
			// nbt.setString("minecraft:can_break", "{\"blocks\":[\"stone\"]}");
			if(status.toLowerCase().equals("canbreak")){
				ReadWriteNBT cb = nbt.getOrCreateCompound("minecraft:can_break");	
				cb.getCompoundList("predicates").addCompound().getStringList("blocks").addAll(blocks);
				cb.setBoolean("show_in_tooltip", true);
				
				
			}else if(status.toLowerCase().equals("canplaceon")){
				ReadWriteNBT cb = nbt.getOrCreateCompound("minecraft:can_place_on");	
				cb.getCompoundList("predicates").addCompound().getStringList("blocks").addAll(blocks);
				cb.setBoolean("show_in_tooltip", true);
			}
			
		});
		  return item2;
	}
	
	//APARTIR DE MINECRAFT 1.20.5 CAMBIO EL TEMA DE LAS NBT EN LOS ITEMS
	
	public static ItemStack getItemNBTnew(ItemStack it, String status , String data ) {
		
		ItemStack item2 = it;
		
		
		List <String> blocks = new ArrayList<>();
		
		if(data.contains(",")) {
			 String[] datos = data.split(",");
			  for(int i = 0 ; i< datos.length ;i ++) {
				  if(Material.matchMaterial(datos[i]) == null)continue;
				  blocks.add(datos[i]);
			  }
			  
		}else {
			if(Material.matchMaterial(data) != null || !blocks.contains(data)) {
				blocks.add(data);
			}
		}
		
		
		NBT.modifyComponents(item2,nbt ->{ 
			
			// nbt.setString("minecraft:can_break","{blocks:[stone,dirt]}");
			// nbt.setString("minecraft:can_break","{\"blocks\":[\"stone\",\"dirt\"]}");
		
			
			// nbt.setString("minecraft:can_break", "{\"blocks\":[\"stone\"]}");
			if(status.toLowerCase().equals("canbreak")){
				ReadWriteNBT cb = nbt.getOrCreateCompound("minecraft:can_break");	
				cb.getCompoundList("predicates").addCompound().getStringList("blocks").addAll(blocks);
				cb.setBoolean("show_in_tooltip", true);
				
				
			}else if(status.toLowerCase().equals("canplaceon")){
				ReadWriteNBT cb = nbt.getOrCreateCompound("minecraft:can_place_on");	
				cb.getCompoundList("predicates").addCompound().getStringList("blocks").addAll(blocks);
				cb.setBoolean("show_in_tooltip", true);
			}
			
		});
		 
		  
		  return item2;
	}
	
	
	
	public static ItemStack getItemNBT223(ItemStack it) {
		
		ItemStack item2 = it;
		
		
		
		NBT.modifyComponents(item2,nbt ->{ 
		
			// nbt.setString("minecraft:can_break","{blocks:[stone,dirt]}");
			// nbt.setString("minecraft:can_break","{\"blocks\":[\"stone\",\"dirt\"]}");
		
			List <String> lw = new ArrayList<>();
			lw.add("stone");
			lw.add("beacon");
			// nbt.setString("minecraft:can_break", "{\"blocks\":[\"stone\"]}");
			ReadWriteNBT cb = nbt.getOrCreateCompound("minecraft:can_break");	
			cb.getCompoundList("predicates").addCompound().getStringList("blocks").addAll(lw);
			cb.setBoolean("show_in_tooltip", true);

			 
//			if(status.toLowerCase().equals("candestroy")){
//				nbt.getOrCreateCompound("minecraft:can_break");
//			}else if(status.toLowerCase().equals("canplaceon")){
//				nbt.getOrCreateCompound("minecraft:can_place_on");
//			}
//			@SuppressWarnings("unchecked")
//			List <String> l = (List<String>) nbt.getStringList("blocks");
//		
//			if(data.contains(",")) {
//				 String[] datos = data.split(",");
//				  for(int i = 0 ; i< datos.length ;i ++) {
//					  if(Material.matchMaterial(datos[i]) == null)continue;
//					  l.add(datos[i]);
//				  }
//				
//			}else {
//				if(Material.matchMaterial(data) != null || !l.contains(data)) {
//					l.add(data);
//					 
//				}
//			}
		
		});
		
		
		
	
		//nb.getOrCreateCompound("minecraft:can_place_on");
	
		
		 
		  
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
    
    //READ CHESTS WITH +NBT ITEMS 
    public static void getItemsChest(ItemStack item) {
    		// SI FUNCIONA
    	if(item.getItemMeta() instanceof BlockStateMeta) {
    		BlockStateMeta bm = (BlockStateMeta) item.getItemMeta();
    		System.out.println("WII 2 blockstate");
    		if(bm.getBlockState() instanceof Chest) {
    			
    			Chest c = (Chest) bm.getBlockState();
    		
    		
    			Inventory inv = c.getInventory();
    			for(ItemStack i : inv.getContents()){
    				if(i == null)continue;
    				System.out.println("WII2 "+i.getType());
    			}
    		
    			
    		}
    		
    		
    	}
    
    	
    }
    
    
    

}

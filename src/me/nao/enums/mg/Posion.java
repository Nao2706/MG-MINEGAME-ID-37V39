package me.nao.enums.mg;
 
import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
 
@SuppressWarnings("deprecation")
        public enum Posion{
            HEALTH(""+ChatColor.RED+ChatColor.BOLD+"INSTA HEALTH", Material.SPLASH_POTION,Color.RED,new PotionEffect(PotionEffectType.INSTANT_HEALTH,5 * 20,2, true ,true,true ), 1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 3 Diamantes",ChatColor.GREEN+"Te Curara Instantaneamente al usarlo."),
            HEALTHP(""+ChatColor.RED+ChatColor.BOLD+"INSTA HEALTH", Material.SPLASH_POTION,Color.RED,new PotionEffect(PotionEffectType.INSTANT_HEALTH,5 * 20,2, true ,true,true ), 1,ChatColor.GREEN+"Te Curara al usarlo."),
            
            SPEED(""+ChatColor.AQUA+ChatColor.BOLD+"SPEED", Material.SPLASH_POTION,Color.AQUA,new PotionEffect(PotionEffectType.SPEED,60 * 20,2, true ,true,true ), 1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 20 Diamantes",ChatColor.GREEN+"Te dara Velocidad Temporal.",ChatColor.GREEN+"60 Segs."),
            SPEEDP(""+ChatColor.AQUA+ChatColor.BOLD+"SPEED", Material.SPLASH_POTION,Color.AQUA,new PotionEffect(PotionEffectType.SPEED,60 * 20,2, true ,true,true ), 1,ChatColor.GREEN+"Te dara Velocidad Temporal.",ChatColor.GREEN+"60 Segs."),

            ABSOR(""+ChatColor.AQUA+ChatColor.BOLD+"ABSORCION", Material.SPLASH_POTION,Color.YELLOW,new PotionEffect(PotionEffectType.ABSORPTION,20 * 20,4, true ,true,true ), 1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 25 Diamantes",ChatColor.GREEN+"Te dara Absorcion Temporal."),
            ABSORP(""+ChatColor.AQUA+ChatColor.BOLD+"ABSORCION", Material.SPLASH_POTION,Color.YELLOW,new PotionEffect(PotionEffectType.ABSORPTION,20 * 20,4, true ,true,true ), 1,ChatColor.GREEN+"Te dara Absorcion Temporal"),

            RESIS(""+ChatColor.WHITE+ChatColor.BOLD+"RESISTENCIA", Material.SPLASH_POTION,Color.WHITE,new PotionEffect(PotionEffectType.RESISTANCE,35 * 20,4, true ,true,true ), 1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 2 Netherite",ChatColor.GREEN+"Te dara Resistencia Temporal.",ChatColor.GREEN+"20 Segs."),
            RESISP(""+ChatColor.WHITE+ChatColor.BOLD+"RESISTENCIA", Material.SPLASH_POTION,Color.WHITE,new PotionEffect(PotionEffectType.RESISTANCE,35 * 20,4, true ,true,true ), 1,ChatColor.GREEN+"Te dara Resistencia Temporal.",ChatColor.GREEN+"35 Segs."),

            
            REGENER(""+ChatColor.RED+ChatColor.BOLD+"REGENERACION", Material.SPLASH_POTION,Color.RED,new PotionEffect(PotionEffectType.REGENERATION,5 * 20,4, true ,true,true ),1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 2 Diamantes",ChatColor.GREEN+ "Te Regenerara la Vida al usarlo.",ChatColor.GREEN+"5 Segs."),
            REGENERP(""+ChatColor.RED+ChatColor.BOLD+"REGENERACION", Material.SPLASH_POTION,Color.RED,new PotionEffect(PotionEffectType.REGENERATION,5 * 20,4, true ,true,true ),1,ChatColor.GREEN+ "Te Regenerara la Vida al usarlo.",ChatColor.GREEN+"5 Segs.");
            
            public ItemStack item;
         
        
			private Posion(String nombre, Material material,Color encant,PotionEffect efc ,int amount, String ...lore){
                ItemStack item = new ItemStack(material,amount);
                PotionMeta meta = (PotionMeta) item.getItemMeta();
                meta.setDisplayName(nombre);
                List<String> lore2 = new ArrayList<>();
                
              // luck,1;fire,5;
               
               // PotionEffectType.DAMAGE_RESISTANCE
                
               if(efc != null) {
            	   meta.addCustomEffect(efc, true);
               }

             // Material.END_CRYSTAL;
                
                if(lore != null) {
                	 for(String linea : lore){
                         lore2.add(linea);
                     }
                     //lore.add(""+ChatColor.GOLD+ChatColor.BOLD+"Valor:"+ChatColor.GREEN+ChatColor.BOLD+"+ 3");|
                     meta.setLore(lore2);
                }
               
                if(encant != null) {
                
                	meta.setColor(encant);
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
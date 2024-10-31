package me.nao.enums;
 
import java.util.ArrayList;
import java.util.List;
 
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
 
 
        public enum Items{
        	
            ESPADAMADERA(""+ChatColor.GOLD+ChatColor.BOLD+"ESPADA DE MADERA", Material.WOODEN_SWORD,null,1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 1 Diamante"),
            ESPADAPIEDRA(""+ChatColor.GOLD+ChatColor.BOLD+"ESPADA DE PIEDRA", Material.STONE_SWORD,null, 1,ChatColor.GREEN+"Precio:"+ChatColor.RED+" 3 Diamante"),
            ESPADAHIERRO(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA DE HIERRO", Material.IRON_SWORD,null, 1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 5 Diamante"),
            ESPADAORO(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA DE ORO", Material.GOLDEN_SWORD,null,1, ChatColor.GREEN+"Precio:"+ChatColor.RED+" 7 Diamante"),
            ESPADADIAMANTE(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA DE DIAMANTE", Material.DIAMOND_SWORD,null,1,ChatColor.GREEN+"Precio:"+ChatColor.RED+" 9 Diamante"),
            ESPADANETHERITA(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA DE NETHERITA", Material.NETHERITE_SWORD,null, 1,ChatColor.GREEN+"Precio:"+ChatColor.RED+" 12 Diamante"),
            
            FLECHA(""+ChatColor.GREEN+ChatColor.BOLD+"FLECHA", Material.ARROW,null, 1,ChatColor.GREEN+"Precio: "+ChatColor.RED+"3 de Oro"),
            FLECHAESPECTRAL(""+ChatColor.GREEN+ChatColor.BOLD+"FLECHA ESPECTRAL", Material.SPECTRAL_ARROW,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"5 de Oro",ChatColor.YELLOW+ "Marca a tus enemigos para que tus aliados lo vean"),
            ARCO(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO", Material.BOW,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"20 de Hierro"),
            BALLESTA(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA", Material.CROSSBOW,null, 1,ChatColor.GREEN+"Precio: "+ChatColor.RED+"12 de Oro",ChatColor.YELLOW+ "Tiene mas alcance que un arco"),
            TRIDENTE(""+ChatColor.GREEN+ChatColor.BOLD+"TRIDENTE", Material.TRIDENT,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"5 Esmeraldas",ChatColor.YELLOW+ "Tiene un poco mas de daño y puedes lanzarlo en el agua"),
            ESCUDO(""+ChatColor.GREEN+ChatColor.BOLD+"ESCUDO", Material.SHIELD,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"20 de Hierro",ChatColor.YELLOW+ "Te servira para cubrirte"),
            TOTEM(""+ChatColor.GREEN+ChatColor.BOLD+"TOTEM DE INMORTALIDAD", Material.TOTEM_OF_UNDYING, null,1, ChatColor.GREEN+"Precio:  "+ChatColor.RED+"30 de Esmeralda",ChatColor.YELLOW+ "Te dara otra oportunidad (pero no si te caes del mapa)"),
            MANZANAORO(""+ChatColor.GREEN+ChatColor.BOLD+"MANZANA DE ORO", Material.GOLDEN_APPLE,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"10 Esmeraldas",ChatColor.YELLOW+ "Te curara al comerla"),
            MANZANA(""+ChatColor.GREEN+ChatColor.BOLD+"MANZANA", Material.APPLE,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"1 Lingote de Hierro",ChatColor.YELLOW+ "Evitara que mueras de Hambre"),
            
            
            BENGALAROJA(""+ChatColor.RED+ChatColor.BOLD+"BENGALA ROJA", Material.REDSTONE_TORCH,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+"Bombardea el Area donde cae. ",ChatColor.RED+"Debes estar en un Sitio despejado.. ",ChatColor.GREEN+"Click Derecho para Lanzar. "),
            BENGALAROJAP(""+ChatColor.RED+ChatColor.BOLD+"BENGALA ROJA", Material.REDSTONE_TORCH,null, 1,ChatColor.YELLOW+"Bombardea el Area donde cae. ",ChatColor.RED+"Debes estar en un Sitio despejado.. ",ChatColor.GREEN+"Click Derecho para Lanzar. "),

            BENGALAVERDE(""+ChatColor.GREEN+ChatColor.BOLD+"BENGALA VERDE", Material.REDSTONE_TORCH,null, 1,ChatColor.GREEN+"Precio: "+ChatColor.RED+"25 Netherite",ChatColor.YELLOW+"Recibe un paquete de Ayuda Random. ",ChatColor.RED+"Debes estar en un Sitio Despejado.. ",ChatColor.GREEN+"Click Derecho para Lanzar. "),
            BENGALAVERDEP(""+ChatColor.GREEN+ChatColor.BOLD+"BENGALA VERDE", Material.REDSTONE_TORCH,null, 1,ChatColor.YELLOW+"Recibe un paquete de Ayuda Random. ",ChatColor.RED+"Debes estar en un Sitio Despejado.. ",ChatColor.GREEN+"Click Derecho para Lanzar. "),

            
            TEST(""+ChatColor.RED+ChatColor.BOLD+"TEST", Material.STICK,null, 1, ChatColor.GREEN+"Falling Block"),
            REVIVE(""+ChatColor.RED+ChatColor.BOLD+"AUTO REVIVIR", Material.BREWING_STAND,null, 1, ChatColor.GREEN+"Llevalo contigo para AutorevivirteMantenlo pulsado.", ChatColor.GREEN+"Usalo cuando te Noqueen. (Click Derecho sobre tu Cadaver)", ChatColor.GREEN+"Precio: "+ChatColor.RED+"25 Diamantes"),
            REVIVEP(""+ChatColor.RED+ChatColor.BOLD+"AUTO REVIVIR", Material.BREWING_STAND,null, 1, ChatColor.GREEN+"Llevalo contigo para Autorevivirte.", ChatColor.GREEN+"Usalo cuando te Noqueen. (Click Derecho sobre tu Cadaver)"),


            
            CERRAR(""+ChatColor.RED+ChatColor.BOLD+"CERRAR", Material.BARRIER,null, 1, ChatColor.GREEN+"Salir de la Tienda"),
            CERRAR2(""+ChatColor.RED+ChatColor.BOLD+"CERRAR", Material.BARRIER,null, 1, ChatColor.GREEN+"Cerrar Menu."),
            VOLVER(""+ChatColor.WHITE+ChatColor.BOLD+"VOLVER", Material.JIGSAW,null, 1, ChatColor.GREEN+"Regresa al Menu Principal de la Tienda."),
            VOLVER2(""+ChatColor.WHITE+ChatColor.BOLD+"VOLVER", Material.JIGSAW,null, 1, ChatColor.GREEN+"Regresa al Menu Principal. "),
            
            ARMAS(""+ChatColor.RED+ChatColor.BOLD+"ESPADAS", Material.IRON_SWORD,null, 1, ChatColor.GREEN+"Compra una para Defenderte."),
            ARMAS2(""+ChatColor.GREEN+ChatColor.BOLD+"ARCOS", Material.BOW,null, 1, ChatColor.GREEN+"Arcos y Ballestas a la Venta."),
            DEFENSA(""+ChatColor.BLUE+ChatColor.BOLD+"DEFENSA", Material.SHIELD,null, 1, ChatColor.GREEN+"Items que podrian salvarte la vida si los usas bien."),
            COMIDA(""+ChatColor.AQUA+ChatColor.BOLD+"COMIDA Y POSIONES", Material.GOLDEN_APPLE,null, 1, ChatColor.GREEN+"Compra comida o Posiones para regenera la vida."),
            ESPECIALES(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"ESPECIALES", Material.BEACON,null, 1, ChatColor.GREEN+"Items con acciones Especiales."),

            
            OBJETIVOS(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"OBJETIVOS DEL MAPA", Material.BOOK,null, 1, ChatColor.GREEN+"Mira los Objetivos que tenga el Mapa. ", ChatColor.RED+"Clickeame para Obtener un Libro. "),
            OBJETIVOSP(""+ChatColor.DARK_PURPLE+ChatColor.BOLD+"OBJETIVOS DEL MAPA", Material.BOOK,null, 1, ChatColor.GREEN+"Mira los Objetivos que tenga el Mapa. ", ChatColor.RED+"Clickeame. "),

            
            RAINARROW(""+ChatColor.GREEN+ChatColor.BOLD+"MULTIPLE ARROW", Material.SPECTRAL_ARROW,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"10 Netherite",ChatColor.GREEN+"Click Derecho para "+ChatColor.RED+"Lanzar Multiples Flechas "),
            RAINARROWP(""+ChatColor.GREEN+ChatColor.BOLD+"MULTIPLE ARROW", Material.SPECTRAL_ARROW,null, 1, ChatColor.GREEN+"Click Derecho para "+ChatColor.RED+"Lanzar Multiples Flechas "),


            ESCU1(""+ChatColor.GRAY+ChatColor.BOLD+"ANTI FLECHAS 1", Material.IRON_CHESTPLATE,"protection_environmental,5/protection_projectile,8", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "Reduce el Daño contra Proyectiles."),
            ESCU1P(""+ChatColor.GRAY+ChatColor.BOLD+"ANTI FLECHAS 1", Material.IRON_CHESTPLATE,"protection_environmental,5/protection_projectile,8", 1, ChatColor.YELLOW+ "Reduce el Daño contra Proyectiles."),

            ESCU2(""+ChatColor.YELLOW+ChatColor.BOLD+"ANTI FUEGO 1", Material.GOLDEN_CHESTPLATE,"protection_environmental,5/protection_fire,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "Reduce el Daño contra el Fuego."),
            ESCU2P(""+ChatColor.YELLOW+ChatColor.BOLD+"ANTI FUEGO 1", Material.GOLDEN_CHESTPLATE,"protection_environmental,5/protection_fire,10", 1, ChatColor.YELLOW+ "Reduce el Daño contra el Fuego."),

            ESCU3(""+ChatColor.AQUA+ChatColor.BOLD+"ANTI EXPLOSION 1", Material.DIAMOND_CHESTPLATE,"protection_environmental,6/protection_explosions,9", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"45 Netherite",ChatColor.YELLOW+ "Reduce el Daño contra Explosiones."),
            ESCU3P(""+ChatColor.AQUA+ChatColor.BOLD+"ANTI EXPLOSION 1", Material.DIAMOND_CHESTPLATE,"protection_environmental,6/protection_explosions,9", 1, ChatColor.YELLOW+ "Reduce el Daño contra Explosiones."),

            
            ESCU4(""+ChatColor.WHITE+ChatColor.BOLD+"ANTI CAIDA 1", Material.LEATHER_CHESTPLATE,"protection_environmental,10/protection_fall,11", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"35 Netherite",ChatColor.YELLOW+ "Reduce el Daño contra Caida."),
            ESCU4P(""+ChatColor.WHITE+ChatColor.BOLD+"ANTI CAIDA 1", Material.LEATHER_CHESTPLATE,"protection_environmental,10/protection_fall,11", 1, ChatColor.YELLOW+ "Reduce el Daño contra Caida."),
         
            ESCU5(""+ChatColor.GOLD+ChatColor.BOLD+"ANTI TODO", Material.NETHERITE_CHESTPLATE,"protection_environmental,10/protection_fall,11/protection_projectile,10/protection_fire,10/protection_explosions,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"200 Netherite",ChatColor.YELLOW+ "Reduce el Daño contra Casi Todo."),
            ESCU5P(""+ChatColor.GOLD+ChatColor.BOLD+"ANTI TODO", Material.NETHERITE_CHESTPLATE,"protection_environmental,10/protection_fall,11/protection_projectile,10/protection_fire,10/protection_explosions,10", 1, ChatColor.YELLOW+ "Reduce el Daño contra Casi Todo."),

            
            BAZUKA(""+ChatColor.GREEN+ChatColor.BOLD+"BAZOOKA", Material.BLAZE_ROD,"knockback,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"128 Netherite",ChatColor.GREEN+"Habilidad:"+ChatColor.RED+" Daña a Cualquier Entidad ",ChatColor.GREEN+ "Lanzalo a distancia. ",ChatColor.GOLD+"Usa Proyectil especial."),
            BAZUKAP(""+ChatColor.GREEN+ChatColor.BOLD+"BAZOOKA", Material.BLAZE_ROD,"knockback,10", 1,ChatColor.GREEN+"Habilidad:"+ChatColor.RED+" Daña a Cualquier Entidad ",ChatColor.GREEN+ "Lanzalo a distancia. ",ChatColor.GOLD+"Usa Proyectil especial."),


            COHETE(""+ChatColor.GOLD+ChatColor.BOLD+"COHETE", Material.FIRE_CHARGE,"fire_aspect,3/knockback,4", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.GREEN+"Proyectil:"+ChatColor.RED+" Usalo con una bazuka"),
            COHETEP(""+ChatColor.GOLD+ChatColor.BOLD+"COHETE", Material.FIRE_CHARGE,"fire_aspect,3/knockback,4", 1,ChatColor.GREEN+"Proyectil:"+ChatColor.RED+" Usalo con una bazuka"),

            
            ZOMBIPALO(""+ChatColor.RED+ChatColor.BOLD+"ANTI-LOOT", Material.STICK,"knockback,7", 1,ChatColor.GREEN+"Aparece por tu ambicion."),
            ARROWDIS(""+ChatColor.RED+ChatColor.BOLD+"ARROW DISPENSER 1", Material.BLAZE_ROD,"knockback,7", 1,ChatColor.GREEN+"Genera flechas por dispensador. "),
            ARROWDIS2(""+ChatColor.RED+ChatColor.BOLD+"ARROW DISPENSER 2", Material.BLAZE_ROD,"knockback,7", 1,ChatColor.GREEN+"Genera flechas por dispensador. "),

            
            
            GANCHO(""+ChatColor.RED+ChatColor.BOLD+"GANCHO", Material.FISHING_ROD,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+ "Desplazate mas rapido."),
            GANCHOP(""+ChatColor.RED+ChatColor.BOLD+"GANCHO", Material.FISHING_ROD,null, 1,ChatColor.YELLOW+ "Desplazate mas rapido."),

            GANCHO2(""+ChatColor.GREEN+ChatColor.BOLD+"GANCHO DE MANIOBRAS", Material.FISHING_ROD,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"50 Netherite",ChatColor.YELLOW+ "Desplazate mas rapido.",ChatColor.YELLOW+ "Sin que el cable toque el piso.",ChatColor.RED+ "Lapislazuli."),
            GANCHO2P(""+ChatColor.GREEN+ChatColor.BOLD+"GANCHO DE MANIOBRAS", Material.FISHING_ROD,null, 1,ChatColor.YELLOW+ "Desplazate mas rapido.",ChatColor.YELLOW+ "Sin que el cable toque el piso.",ChatColor.RED+ "Lapislazuli."),

            
            ARROWL(""+ChatColor.GREEN+ChatColor.BOLD+"ARROW LAUNCHER", Material.BLAZE_ROD,"damage_all,10/knockback,3", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"64 Netherite",ChatColor.YELLOW+ "Dispara Flechas mas rapido."),
            ARROWLP(""+ChatColor.GREEN+ChatColor.BOLD+"ARROW LAUNCHER", Material.BLAZE_ROD,"damage_all,10/knockback,3", 1,ChatColor.YELLOW+ "Dispara Flechas mas rapido.",ChatColor.RED+ "- Consume 2 Flechas."),

            
            MEDICO(""+ChatColor.RED+ChatColor.BOLD+"MEDICO", Material.EMERALD,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+ "Llama a un Aldeano medico"),
            MEDICOP(""+ChatColor.RED+ChatColor.BOLD+"MEDICO", Material.EMERALD,null, 1),
            
            REFUERZOS(""+ChatColor.WHITE+ChatColor.BOLD+"REFUERZOS", Material.NETHER_STAR,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+ "Llama a un Iron Golem"),
            REFUERZOSP(""+ChatColor.WHITE+ChatColor.BOLD+"REFUERZOS", Material.NETHER_STAR,null, 1),
            
            REFUERZOS2(""+ChatColor.WHITE+ChatColor.BOLD+"REFUERZOS 2", Material.END_CRYSTAL,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"64 Netherite",ChatColor.YELLOW+ "Llama a una escuadra de Golems de hierro"),
            REFUERZOS2P(""+ChatColor.WHITE+ChatColor.BOLD+"REFUERZOS 2", Material.END_CRYSTAL,null, 1),
            
            STOREXPRESS(""+ChatColor.BLUE+ChatColor.BOLD+"STORE EXPRESS", Material.ENCHANTED_BOOK,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"3 Stacks Hierro",ChatColor.YELLOW+ "Compra desde donde sea que estes."),
            STOREXPRESSP(""+ChatColor.BLUE+ChatColor.BOLD+"STORE EXPRESS", Material.ENCHANTED_BOOK,null, 1,ChatColor.YELLOW+ "Compra desde donde sea que estes."),
            
            CHECKPOINT(""+ChatColor.GREEN+ChatColor.BOLD+"CHECKPOINT", Material.BEACON,null, 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"20 Netherite",ChatColor.YELLOW+ "En caso de estar cerca de morir ",ChatColor.YELLOW+ "Te tepeara al sitio donde pusiste el marcador."),
            CHECKPOINTP(""+ChatColor.GREEN+ChatColor.BOLD+"CHECKPOINT", Material.BEACON,null, 1,ChatColor.YELLOW+ "En caso de estar cerca de morir ",ChatColor.YELLOW+ "Tepeara al sitio donde pusiste el marcador"),
            
            BALLESTA1(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA RAPIDA", Material.CROSSBOW,"quick_charge,5/piercing,1", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "Esta encantado"),
            BALLESTA1P(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA RAPIDA", Material.CROSSBOW,"quick_charge,5/piercing,1", 1),
            
            
            BALLESTA2(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA RAPIDA 2", Material.CROSSBOW,"quick_charge,5/multishot,1", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"64 Netherite",ChatColor.YELLOW+ "Esta encantado"),
            BALLESTA2P(""+ChatColor.GREEN+ChatColor.BOLD+"BALLESTA RAPIDA 2", Material.CROSSBOW,"quick_charge,5/multishot,1", 1),
            
            
            ARCOENCAN1(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO ENCANTADO", Material.BOW,"arrow_fire,3/arrow_knockback,5", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"30 Netherite",ChatColor.YELLOW+ "Esta encantado"),
            ARCOENCAN1P(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO ENCANTADO", Material.BOW,"arrow_fire,3/arrow_knockback,5", 1),
            
            //
            DEADBOW(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO DE MUERTE", Material.BOW,"arrow_damage,10/arrow_fire,3/arrow_knockback,4", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "Esta encantado"),
            DEADBOWP(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO DE MUERTE", Material.BOW,"arrow_damage,10/arrow_fire,3/arrow_knockback,4", 1),
            
            DEADBOW2(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO DE MUERTE 2", Material.BOW,"arrow_damage,20/arrow_fire,5/arrow_knockback,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"110 Netherite",ChatColor.YELLOW+ "Lanzale una Flecha y Eliminalo."),
            DEADBOW2P(""+ChatColor.GREEN+ChatColor.BOLD+"ARCO DE MUERTE 2", Material.BOW,"arrow_damage,20/arrow_fire,5/arrow_knockback,10", 1,ChatColor.YELLOW+ "Lanzale una Flecha y Eliminalo."),
            
            
            JEDI(""+ChatColor.GREEN+ChatColor.BOLD+"STAR WARS", Material.END_CRYSTAL,"fire_aspect,3/knockback,4", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "Empuja a tus enemigos cuando te rodeen"),
            JEDIP(""+ChatColor.GREEN+ChatColor.BOLD+"STAR WARS", Material.END_CRYSTAL,"fire_aspect,3/knockback,4", 1),
            
            //
            KATANA(""+ChatColor.RED+ChatColor.BOLD+"KATANA", Material.NETHERITE_SWORD,"damage_all,10/knockback,2", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"62 Netherite",ChatColor.YELLOW+ "Esta encantada"),
            KATANAP(""+ChatColor.RED+ChatColor.BOLD+"KATANA", Material.NETHERITE_SWORD,"damage_all,10/knockback,2", 1),
            
            KATANA2(""+ChatColor.RED+ChatColor.BOLD+"KATANA 2", Material.NETHERITE_SWORD,"damage_all,15/knockback,2", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"100 Netherite",ChatColor.YELLOW+ "Cortalos por la mitad."),
            KATANA2P(""+ChatColor.RED+ChatColor.BOLD+"KATANA 2", Material.NETHERITE_SWORD,"damage_all,15/knockback,2", 1,ChatColor.YELLOW+ "Cortalos por la mitad."),
            
            
            PALO(""+ChatColor.RED+ChatColor.BOLD+"KICK ALL", Material.STICK,"fire_aspect,3/knockback,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"40 Netherite",ChatColor.YELLOW+ "No dejes que se te acerquen"),
            PALOP(""+ChatColor.RED+ChatColor.BOLD+"KICK ALL", Material.STICK,"fire_aspect,3/knockback,10", 1),
            
            TRIDENTEE(""+ChatColor.GREEN+ChatColor.BOLD+"TRIDENTE ENCANTADO", Material.TRIDENT,"loyalty,3/impaling,5", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"64 Netherite",ChatColor.YELLOW+ "Tiene un poco mas de daño y puedes lanzarlo en el agua."),
            TRIDENTEEP(""+ChatColor.GREEN+ChatColor.BOLD+"TRIDENTE ENCANTADO", Material.TRIDENT,"loyalty,3/impaling,5", 1,ChatColor.YELLOW+ "Tiene un poco mas de daño y puedes lanzarlo en el agua."),

            TRIDENTEE2(""+ChatColor.GREEN+ChatColor.BOLD+"TRIDENTE DE POSEIDON", Material.TRIDENT,"loyalty,3/impaling,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"100 Netherite",ChatColor.YELLOW+ "Se lo quitaron a Poseidon???"),
            TRIDENTEE2P(""+ChatColor.GREEN+ChatColor.BOLD+"TRIDENTE DE POSEIDON", Material.TRIDENT,"loyalty,3/impaling,10", 1,ChatColor.YELLOW+ "Se lo quitaron a Poseidon???"),

            ESCUDO1(""+ChatColor.GREEN+ChatColor.BOLD+"ESCUDO LVL 1", Material.SHIELD,"durability,5/knockback,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"64 de Hierro",ChatColor.YELLOW+ "Te servira para Cubrirte.",ChatColor.YELLOW+ "Contra Proyectiles y Explosiones"),
            ESCUDO1P(""+ChatColor.GREEN+ChatColor.BOLD+"ESCUDO LVL 1", Material.SHIELD,"durability,5/knockback,10", 1,ChatColor.YELLOW+ "Te servira para Cubrirte.",ChatColor.YELLOW+ "Contra Proyectiles y Explosiones"),
            
            ESCUDO2(""+ChatColor.GREEN+ChatColor.BOLD+"ESCUDO LVL 2", Material.SHIELD,"thorns,10/durability,10/knockback,10", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"150 de Hierro",ChatColor.YELLOW+ "Te servira para Cubrirte.",ChatColor.YELLOW+ "Eres el Capitan America???"),
            ESCUDO2P(""+ChatColor.GREEN+ChatColor.BOLD+"ESCUDO LVL 2", Material.SHIELD,"thorns,10/durability,10/knockback,10", 1,ChatColor.YELLOW+ "Te servira para Cubrirte.",ChatColor.YELLOW+ "Contra Proyectiles y Explosiones",ChatColor.YELLOW+ "Eres el Capitan America???"),
            
            
            ADELANTE(""+ChatColor.GOLD+ChatColor.BOLD+"SIGUIENTE PAGINA", Material.GREEN_STAINED_GLASS_PANE,null,1, ChatColor.GREEN+"Nota:"+ChatColor.RED+" Ve la siguiente pagina."),
            ATRAS(""+ChatColor.GOLD+ChatColor.BOLD+"PAGINA ANTERIOR", Material.RED_STAINED_GLASS_PANE,null,1, ChatColor.GREEN+"Nota:"+ChatColor.RED+" Ve la anterior pagina."),

            
            
            ESPADAENCAN1(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA ENCANTADA", Material.IRON_SWORD,"fire_aspect,3/knockback,4", 1, ChatColor.GREEN+"Precio: "+ChatColor.RED+"5 Netherite",ChatColor.YELLOW+ "Esta encantada"),
        	ESPADAENCAN1P(""+ChatColor.GREEN+ChatColor.BOLD+"ESPADA ENCANTADA", Material.IRON_SWORD,"fire_aspect,3/knockback,4", 1);
        	
        	
        	
        	
            
            public ItemStack item;
         
            @SuppressWarnings("deprecation")
			private Items(String nombre, Material material,String encant , int amount, String ...lore){
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
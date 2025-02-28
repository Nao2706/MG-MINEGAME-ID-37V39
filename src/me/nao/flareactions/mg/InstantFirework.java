package me.nao.flareactions.mg;



import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class InstantFirework {

	
	
	public InstantFirework(FireworkEffect fireworkeffect,Location l)  {
		
		Firework f = l.getWorld().spawn(l,Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(fireworkeffect);
		f.setFireworkMeta(fm);


		
	}
	
	
	
	
	
}

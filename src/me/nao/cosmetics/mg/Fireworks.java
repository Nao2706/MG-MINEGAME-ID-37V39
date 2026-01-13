package me.nao.cosmetics.mg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class Fireworks {

	
	private Player player;
	
	public Fireworks(Player player) {
		this.player = player;
	}
	
	public Fireworks() {
	}
	
	public void spawnFireballGreenLarge() {
		Location l = player.getLocation();
		
		Firework f = (Firework) l.getWorld().spawn(l,Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder()
				.flicker(false)
				.trail(true)
				.with(Type.BALL_LARGE)
				.withColor(Color.GREEN)
				.withFade(Color.ORANGE)
				.build());
		
				fm.setPower(3);
				f.setFireworkMeta(fm);
				
				ParticleOfWin();
	}
	
	public void ParticleOfWin() {
		Location l = player.getLocation();
		
		l.getWorld().spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0.5, 1, 0.5),	/* N DE PARTICULAS */10, 1, 1, 1, /* velocidad */0, null, true);
		
		
	}
	
	
	public void spawnFireballAquaLarge() {
		Location l = player.getLocation();
		
		Firework f = (Firework) l.getWorld().spawn(l,Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder()
				.flicker(false)
				.trail(true)
				.with(Type.BALL_LARGE)
				.withColor(Color.AQUA)
				.withFade(Color.YELLOW)
				.build());
				
				fm.setPower(3);
				f.setFireworkMeta(fm);
	}
	
	
	public void spawnFireballRedLarge() {
		Location l = player.getLocation();
		
		Firework f = (Firework) l.getWorld().spawn(l,Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder()
				.flicker(false)
				.trail(true)
				.with(Type.BALL_LARGE)
				.withColor(Color.RED)
				.withFade(Color.YELLOW)
				.build());
				
				fm.setPower(3);
				f.setFireworkMeta(fm);
	}
	
	
	public void spawnMetodoAyi() {
		Location l = player.getLocation();
		Random r = new Random();
		List<Color> lc = getColor();
		
		
		Firework f = (Firework) l.getWorld().spawn(l,Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder()
				.flicker(false)
				.trail(true)
				.with(Type.BALL_LARGE)
				.withColor(lc.get(r.nextInt(lc.size())))
				.withFade(lc.get(r.nextInt(lc.size())))
				.build());
				
				fm.setPower(3);
				f.setFireworkMeta(fm);
	}
	
	public void spawnMetodoAyi(Player player) {
		Location l = player.getLocation();
		Random r = new Random();
		List<Color> lc = getColor();
		
		
		Firework f = (Firework) l.getWorld().spawn(l,Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder()
				.flicker(false)
				.trail(true)
				.with(Type.BALL_LARGE)
				.withColor(lc.get(r.nextInt(lc.size())))
				.withFade(lc.get(r.nextInt(lc.size())))
				.build());
				
				fm.setPower(3);
				f.setFireworkMeta(fm);
	}
	
	public List<Color> getColor(){
		List <Color> c = new ArrayList<Color>();
		c.add(Color.AQUA);  c.add(Color.NAVY);
		c.add(Color.BLACK); c.add(Color.OLIVE); 
		c.add(Color.BLUE);  c.add(Color.ORANGE);
		c.add(Color.FUCHSIA); c.add(Color.PURPLE);
		c.add(Color.GRAY);  c.add(Color.RED);
		c.add(Color.GREEN); c.add(Color.SILVER); 
		c.add(Color.LIME);  c.add(Color.TEAL); 
		c.add(Color.MAROON);  c.add(Color.WHITE); 
		 c.add(Color.YELLOW); 
		 
		
			
		
		return c;
	}
	
	
}

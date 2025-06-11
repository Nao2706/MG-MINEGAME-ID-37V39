package me.nao.generalinfo.mg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class TeamsMg {
	
	private List<TeamMg> teamsingame;

	public TeamsMg() {
		this.teamsingame = new ArrayList<>();
		
	}


	public List<TeamMg> getTeamsingame() {
		return teamsingame;
	}
	
	public void setTeamsingame(List<TeamMg> teamsingame) {
		this.teamsingame = teamsingame;
	}

	
	public int getTeamsAlive() {
		
		List<TeamMg> teams = getTeamsingame();
		List<TeamMg> alive = new ArrayList<>();
		
		for(TeamMg t : teams) {
			if(t.isObliterated()) continue;
			alive.add(t);
		}
		
		return alive.size();
	}
	
	public int getTeamsObliterated() {
		
		List<TeamMg> teams = getTeamsingame();
		List<TeamMg> eliminated = new ArrayList<>();
		
		for(TeamMg t : teams) {
			if(!t.isObliterated()) continue;
			eliminated.add(t);
		}
		
		return eliminated.size();
	}
	
	 public List<List<String>> getRandomTeams(List<String> l,int amountteams){
		  
		   	List<String> copy = new ArrayList<>();
		   	copy.addAll(l);
			Collections.shuffle(copy);
			List<List<String>> g = IntStream.range(0, copy.size())
					.boxed()
					.collect(Collectors.groupingBy(i -> i % amountteams))
					.values() 
					.stream()
					.map(il -> il.stream().map(copy::get).collect(Collectors.toList()))
					.collect(Collectors.toList());
			
		   return g;
	  }
	 
	 
	 public void showAvablieTeams(Player player) {
		List<TeamMg> teams = getTeamsingame();
		
		int i = 0;
		for(TeamMg t : teams) {
			player.getInventory().setItem(i, itemDesingTeam(t.getColor(),t));
			i++;
		}	
			
			
	 }
	 
	 //CUANDO DECIDES UNIRTE A UN EQUIPO
	 public void joinIntoTeam(Player player) {
		 
	 }

	 
	 @SuppressWarnings("deprecation")
	public ItemStack itemDesingTeam(Color color ,TeamMg t) {
		 ItemStack it = null;
		 ItemMeta meta = null;
		 if(color == Color.YELLOW) {
			 it = new ItemStack(Material.YELLOW_WOOL);
			 meta = it.getItemMeta();
			 meta.setDisplayName(""+ChatColor.YELLOW+ChatColor.BOLD+"EQUIPO "+t.getTeamName());
			 List<String> l = new ArrayList<>();
			 l.add("");
			 l.addAll(t.getMembers());
			 l.add("");
			 l.add(""+t.getMembers().size()+"/"+t.getMaxmembers());
			 meta.setLore(l);
			 it.setItemMeta(meta);
			 
		 }if(color == Color.RED) {
			 it = new ItemStack(Material.RED_WOOL);
			 meta = it.getItemMeta();
			 meta.setDisplayName(""+ChatColor.RED+ChatColor.BOLD+"EQUIPO "+t.getTeamName());
			 List<String> l = new ArrayList<>();
			 l.add("");
			 l.addAll(t.getMembers());
			 l.add("");
			 l.add(""+t.getMembers().size()+"/"+t.getMaxmembers());
			 meta.setLore(l);
			 it.setItemMeta(meta);
			 
		 }if(color == Color.BLUE) {
			 it = new ItemStack(Material.BLUE_WOOL);
			 meta = it.getItemMeta();
			 meta.setDisplayName(""+ChatColor.BLUE+ChatColor.BOLD+"EQUIPO "+t.getTeamName());
			 List<String> l = new ArrayList<>();
			 l.add("");
			 l.addAll(t.getMembers());
			 l.add("");
			 l.add(""+t.getMembers().size()+"/"+t.getMaxmembers());
			 meta.setLore(l);
			 it.setItemMeta(meta);
			 
		 }if(color == Color.GREEN) {
			 it = new ItemStack(Material.LIME_WOOL);
			 meta = it.getItemMeta();
			 meta.setDisplayName(""+ChatColor.GREEN+ChatColor.BOLD+"EQUIPO "+t.getTeamName());
			 List<String> l = new ArrayList<>();
			 l.add("");
			 l.addAll(t.getMembers());
			 l.add("");
			 l.add(""+t.getMembers().size()+"/"+t.getMaxmembers());
			 meta.setLore(l);
			 it.setItemMeta(meta);
			 
		 }
		return it;
	 }
	
}

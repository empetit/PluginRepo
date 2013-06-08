package com.gmail.capthesheep;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
 
public class FlagListenner implements Listener {
	
	public Capthesheep plugin;
	
	public FlagListenner(Capthesheep pl){
		plugin = pl;
	}
	
    @EventHandler
    public void placeFlag(PlayerInteractEvent event) {

    	if(event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().getItemInHand().getType() == Material.WOOL){

        	SheepGame sg = null;
        	try{
            	sg = plugin.LesParties.get(event.getPlayer());        		
        	}catch(Exception e){
        		sg = null;
        	}
        	
    		if(sg != null){
    			
            	World monde = event.getPlayer().getWorld();
            	Block blocCible = event.getClickedBlock();
            	
    			switch(sg.getEtape()){
    			case 1: //premier drapeau
    				sg.setLocFlagUn(blocCible.getLocation());
                	sg.setCoulFlagUn(event.getPlayer().getItemInHand().getData().getData());
                	
                	monde.getBlockAt(new Location(monde,blocCible.getX(),blocCible.getY()+1,blocCible.getZ())).setType(Material.FENCE);
                	monde.getBlockAt(new Location(monde,blocCible.getX(),blocCible.getY()+2,blocCible.getZ())).setType(Material.FENCE);
                	monde.getBlockAt(new Location(monde,blocCible.getX(),blocCible.getY()+3,blocCible.getZ())).setType(Material.FENCE);
                	monde.getBlockAt(new Location(monde,blocCible.getX(),blocCible.getY()+4,blocCible.getZ())).setType(Material.FENCE);
                	monde.getBlockAt(new Location(monde,blocCible.getX()+1,blocCible.getY()+3,blocCible.getZ())).setTypeIdAndData(35,sg.getCoulFlagUn() , true);
                	monde.getBlockAt(new Location(monde,blocCible.getX()+1,blocCible.getY()+4,blocCible.getZ())).setTypeIdAndData(35,sg.getCoulFlagUn() , true);
                	monde.getBlockAt(new Location(monde,blocCible.getX()+2,blocCible.getY()+3,blocCible.getZ())).setTypeIdAndData(35,sg.getCoulFlagUn() , true);
                	monde.getBlockAt(new Location(monde,blocCible.getX()+2,blocCible.getY()+4,blocCible.getZ())).setTypeIdAndData(35,sg.getCoulFlagUn() , true);
                	sg.setEtape(2);
                	sg.getCreateur().sendMessage(ChatColor.GREEN + " + Cliquez avec un bloc laine de couleur pour placer le " +ChatColor.YELLOW+"deuxième "+ChatColor.GREEN +"drapeau");
        			
    				break;
    			case 2: //deuxième drapeau
    				
    				sg.setLocFlagDeux(blocCible.getLocation());
                	sg.setCoulFlagDeux(event.getPlayer().getItemInHand().getData().getData());
                	
                	if(sg.getLocFlagDeux().distance(sg.getLocFlagUn()) > plugin.config.getInt("minDistFlag") && sg.getCoulFlagUn() != sg.getCoulFlagDeux()){
                    	
                    	monde.getBlockAt(new Location(monde,blocCible.getX(),blocCible.getY()+1,blocCible.getZ())).setType(Material.FENCE);
                    	monde.getBlockAt(new Location(monde,blocCible.getX(),blocCible.getY()+2,blocCible.getZ())).setType(Material.FENCE);
                    	monde.getBlockAt(new Location(monde,blocCible.getX(),blocCible.getY()+3,blocCible.getZ())).setType(Material.FENCE);
                    	monde.getBlockAt(new Location(monde,blocCible.getX(),blocCible.getY()+4,blocCible.getZ())).setType(Material.FENCE);
                    	monde.getBlockAt(new Location(monde,blocCible.getX()+1,blocCible.getY()+3,blocCible.getZ())).setTypeIdAndData(35,sg.getCoulFlagDeux() , true);
                    	monde.getBlockAt(new Location(monde,blocCible.getX()+1,blocCible.getY()+4,blocCible.getZ())).setTypeIdAndData(35,sg.getCoulFlagDeux() , true);
                    	monde.getBlockAt(new Location(monde,blocCible.getX()+2,blocCible.getY()+3,blocCible.getZ())).setTypeIdAndData(35,sg.getCoulFlagDeux() , true);
                    	monde.getBlockAt(new Location(monde,blocCible.getX()+2,blocCible.getY()+4,blocCible.getZ())).setTypeIdAndData(35,sg.getCoulFlagDeux() , true);

                    	sg.getCreateur().sendMessage(ChatColor.GREEN + " Placement des drapeaux terminé. Cliquez avec une "+ChatColor.YELLOW+"pancarte"+ChatColor.GREEN+" pour placer l'entrée");
                    	
                    	sg.setEtape(3);
                    	
                    	ItemStack is = new ItemStack(Material.SIGN, 1);
                    	ItemMeta im = is.getItemMeta();
                    	im.setDisplayName("Panneau d'affichage");
                    	is.setItemMeta(im);
                    	sg.getCreateur().getInventory().addItem(is);                    	

                    	is = new ItemStack(Material.PAPER, 1);
                    	im = is.getItemMeta();
                    	im.setDisplayName(sg.getGameName());
                    	ArrayList<String> lore = new ArrayList<String>();
                    	lore.add("Placer cette affichette");
                    	lore.add("pour permettre aux joueurs ");
                    	lore.add("de s'inscrire pour une partie");
                    	im.setLore(lore);
                    	is.setItemMeta(im);
                    	sg.getCreateur().getInventory().addItem(is);
                    	
                	} else {
                		if(sg.getCoulFlagUn() == sg.getCoulFlagDeux()){             			
                			sg.getCreateur().sendMessage(ChatColor.RED +"Les deux drapeaux doivent avoir une couleur différente");
                		}else{
                    		sg.getCreateur().sendMessage(ChatColor.RED +"Les deux drapeaux sont trop proches !");   
                		}
                	}
    				break;
    			case 4:
    				
    				break;
    			}
    		}
    	}else if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getItemInHand().getType() == Material.PAPER && (event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN) ){
    		SheepGame sg = null;

        	try{
            	sg = plugin.LesParties.get(event.getPlayer());        		
        	}catch(Exception e){
        		sg = null;
        	}
        	
        	if(sg != null){
        			Logger.getLogger(event.getClickedBlock().getType().toString());
            	if( event.getPlayer().getItemInHand().getItemMeta().getDisplayName() == sg.getGameName()){            		
            		
            		Sign sign = (Sign) event.getClickedBlock().getState();
            		sign.setLine(0, "<------------>");
            		sign.setLine(1, ""+ChatColor.BLUE+""+sg.getGameName()+"");
            		sign.setLine(2, ""+ChatColor.GREEN+"0/"+sg.getJoueurMax()+"");            		
            		sign.setLine(3, "<------------>");
            		sign.update();
            		event.getPlayer().getItemInHand().setAmount(0);
            		
            		Block panneau = (Block) event.getClickedBlock();

            		panneau.setMetadata("sg", new FixedMetadataValue(plugin, true));
            		panneau.setMetadata("sg.proprio", new FixedMetadataValue(plugin, sg.getCreateur().getName()) );
            		panneau.setMetadata("sg.nom", new FixedMetadataValue(plugin, sg.getGameName()) );
            		
            	}
        	}

    		event.setCancelled(true);
    	}
    }

    @EventHandler
    public void joinGame(PlayerInteractEvent event) {
    	SheepGame sg = null;

    	try{
        	sg = plugin.LesParties.get(event.getPlayer());        		
    	}catch(Exception e){
    		sg = null;
    	}
    	
    	if( sg != null && (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN)){
    		Sign sign = (Sign) event.getClickedBlock().getState();
    		if (sign.hasMetadata("sg")){
    			//recharge le nombre de joueurs
    			sign.setLine(2, ""+ChatColor.GREEN+""+sg.getJoueursAct() +"/"+sg.getJoueurMax()+""); 
    			sign.update();
    			if(sg.getJoueursAct() < sg.getJoueurMax()){
        			sg.AjouterJoueur(event.getPlayer());   				
    			}else{
    				event.getPlayer().sendMessage(ChatColor.RED+"Il n'y a plus de place :(");
    			}
    			

    			sign.setLine(2, ""+ChatColor.GREEN+""+sg.getJoueursAct() +"/"+sg.getJoueurMax()+""); 
    			sign.update();
    			}
    		}
    	}
    }
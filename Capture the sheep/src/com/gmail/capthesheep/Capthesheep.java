package com.gmail.capthesheep;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Capthesheep extends JavaPlugin {
	
	private FlagListenner listenFlag = new FlagListenner(this);
	public HashMap<Player,SheepGame> LesParties = new HashMap<Player,SheepGame>();

    File configFile;
    FileConfiguration config;
	
	@Override
    public void onEnable(){
		//chargement de la configuration 
	    configFile = new File(getDataFolder(), "config.yml");
	    
	    try {
	        firstRun();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    config = new YamlConfiguration();
	    loadYamls();
	    
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.listenFlag,this);
		
		getLogger().info("Capture the sheep enabled ! :)");
    }
    @Override
    public void onDisable() {
    	saveYamls();
    	getLogger().info("Capture the sheep disabled ! :(");
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("cts") && (sender instanceof Player)){
    		Player createur =  getServer().getPlayer(sender.getName());
    		switch(args.length){
    			case 0 :
    				sender.sendMessage(ChatColor.GREEN + "Capture the sheep V"+getDescription().getVersion());
    				break;
    			case 1 :
		        	switch(args[0]){
		        		case "cancel" :
		        			sender.sendMessage(ChatColor.RED + "Création annulée !");
		        			LesParties.remove(createur);
		        			break;
		        		default:
		        			sender.sendMessage(ChatColor.RED + "Commande invalide ! \n - /cts cancel");break;
		        	}
		        	break;
	    		case 2 :
		        	switch(args[0]){
		        		case "create" :
		        			sender.sendMessage(ChatColor.GREEN + "Création d'une partie");
		        			LesParties.put(createur,new SheepGame(args[1],createur,config.getInt("maxJoueur")));
		        			sender.sendMessage(ChatColor.GREEN + "Configuration de "+ChatColor.YELLOW +args[1]+" :");
		        			sender.sendMessage(ChatColor.GREEN + " + Cliquez avec un bloc laine de couleur pour placer le " +ChatColor.YELLOW+"premier "+ChatColor.GREEN +"drapeau");
		        			LesParties.get(createur).setEtape(1);
		        			break;

		        		case "supprimer" :
		        			createur.sendMessage(LesParties.get(createur).getGameName());
		        			if(LesParties.get(createur).getGameName() == args[1] && LesParties.get(createur).getEtape() == 5){
		        				createur.sendMessage(ChatColor.GREEN+"La partie "+LesParties.get(createur).getGameName()+" a bien été supprimée!");
		        			}else{
		        				createur.sendMessage(ChatColor.RED+"Vous n'êtes pas le propriétaire de cette partie !");
		        			}
		        			break;
		        	}
		        	break;
		        default: return false;
    		}
	        return true;
    	} else if (cmd.getName().equalsIgnoreCase("basic2")) {
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    		}
    		return true;
    	}
    	return false;
    }
    

	
	private void firstRun() throws Exception {
	    if(!configFile.exists()){
	        configFile.getParentFile().mkdirs();
	        copy(getResource("config.yml"), configFile);
	    }
	}
	
	private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void saveYamls() {
	    try {
	        config.save(configFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void loadYamls() {
	    try {
	        config.load(configFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}

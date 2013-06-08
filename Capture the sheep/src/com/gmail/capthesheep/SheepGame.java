package com.gmail.capthesheep;


import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SheepGame {

	private String GameName;
	private Player Createur;
	private Location locFlagUn;
	private byte coulFlagUn;
	private Location locFlagDeux;
	private byte coulFlagDeux;
	private int etape,joueursAct,joueurMax;
	private ArrayList<Player> equipeUn;
	private ArrayList<Player> equipeDeux;
	
	public SheepGame(String nom,Player crea,int maxPlay){
		this.setGameName(nom);
		this.setCreateur(crea);
		this.setEtape(0);
		this.setJoueursAct(0);
		this.setJoueurMax(maxPlay);
		equipeUn = new ArrayList<>();
		equipeDeux = new ArrayList<>();
		
		
	}
	
	public void AjouterJoueur(Player nouvJoueur){
		if(equipeUn.size() <= equipeDeux.size()){
			equipeUn.add(nouvJoueur);
			nouvJoueur.sendMessage(ChatColor.DARK_BLUE+"Vous etes dans l'equipe 1");
			nouvJoueur.teleport(new Location(locFlagUn.getWorld(), locFlagUn.getX()+2, locFlagUn.getY()+1, locFlagUn.getZ()));
		}else{
			equipeDeux.add(nouvJoueur);
			nouvJoueur.sendMessage(ChatColor.DARK_BLUE+"Vous etes dans l'equipe 2");
			nouvJoueur.teleport(new Location(locFlagDeux.getWorld(), locFlagDeux.getX(), locFlagDeux.getY()+1, locFlagDeux.getZ()+2));
		}
		joueursAct = joueursAct+1;
	}
	
	public String getGameName() {
		return GameName;
	}

	public void setGameName(String gameName) {
		GameName = gameName;
	}

	public Player getCreateur() {
		return Createur;
	}

	public void setCreateur(Player createur) {
		Createur = createur;
	}

	public Location getLocFlagUn() {
		return locFlagUn;
	}

	public void setLocFlagUn(Location locFlagUn) {
		this.locFlagUn = locFlagUn;
	}

	public byte getCoulFlagUn() {
		return coulFlagUn;
	}

	public void setCoulFlagUn(byte coulFlagUn) {
		this.coulFlagUn = coulFlagUn;
	}

	public Location getLocFlagDeux() {
		return locFlagDeux;
	}

	public void setLocFlagDeux(Location locFlagDeux) {
		this.locFlagDeux = locFlagDeux;
	}

	public byte getCoulFlagDeux() {
		return coulFlagDeux;
	}

	public void setCoulFlagDeux(byte coulFlagDeux) {
		this.coulFlagDeux = coulFlagDeux;
	}

	public int getEtape() {
		return etape;
	}

	public void setEtape(int etape) {
		this.etape = etape;
	}

	public int getJoueursAct() {
		return joueursAct;
	}

	public void setJoueursAct(int joueursAtc) {
		this.joueursAct = joueursAtc;
	}

	public int getJoueurMax() {
		return joueurMax;
	}

	public void setJoueurMax(int joueurMax) {
		this.joueurMax = joueurMax;
	}

}

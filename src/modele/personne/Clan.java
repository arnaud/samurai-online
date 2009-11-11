/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.personne;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import modele.monde.ZoneClan;


/**
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class Clan implements Serializable {

	public String nom;
	private ZoneClan zoneClan;
	private int idClan;
	private Collection personnages;
	private Personnage responsableClan;
	private Collection groupes;

	public Clan(String nom,Personnage responsableClan){
		this.nom=nom;	
		this.responsableClan = responsableClan;
		this.personnages = new Vector();
		personnages.add(responsableClan);
		this.groupes = new Vector();
	}
	
	public Clan(String nom,Personnage responsableClan,ZoneClan zoneClan){
		this.nom=nom;	
		this.zoneClan=zoneClan;
		this.responsableClan = responsableClan;	
		this.personnages = new Vector();
		personnages.add(responsableClan);
		this.groupes = new Vector();
	}
	
	public String getNomClan(){
		return nom;
	}
	
	public void setNomClan(String nom){
		this.nom=nom;
	}
	
	public void setPersonnages(Collection value) {
		personnages = value;
	}

	public Iterator personnageIterator() {
		return personnages.iterator();
	}

	public boolean addPersonnage(Personnage element) {
		return personnages.add(element);
	}

	public boolean removePersonnage(Personnage element) {
		return personnages.remove(element);
	}

	
	public void setResponsable(Personnage personnage){
		this.responsableClan=personnage;
	}
	
	public Personnage getResponsable(){
		return responsableClan;
	}
	
	public boolean isPersonnagesEmpty() {
		return personnages.isEmpty();
	}

	public void clearPersonnages() {
		personnages.clear();
	}

	public boolean containsPersonnage(Personnage element) {
		return personnages.contains(element);
	}

	public boolean containsAllPersonnage(Collection elements) {
		return personnages.containsAll(elements);
	}

	public int personnagseSize() {
		return personnages.size();
	}

	public void setGroupes(Collection value) {
		groupes = value;
	}

	public Iterator groupesIterator() {
		return groupes.iterator();
	}

	public boolean addGroupe(Groupe element) {
		return groupes.add(element);
	}

	public boolean removeGroupe(Groupe element) {
		return groupes.remove(element);
	}

	public boolean isGroupesEmpty() {
		return groupes.isEmpty();
	}

	public void clearGroupes() {
		groupes.clear();
	}

	public boolean containsGroupe(Groupe element) {
		return groupes.contains(element);
	}

	public boolean containsAllGroupe(Collection elements) {
		return groupes.containsAll(elements);
	}

	public int groupesSize() {
		return groupes.size();
	}
}

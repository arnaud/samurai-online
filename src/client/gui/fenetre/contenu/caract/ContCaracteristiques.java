package client.gui.fenetre.contenu.caract;

import java.util.Vector;

import javax.swing.JFrame;

import modele.caracteristique.Caracteristique;
import client.gui.fenetre.contenu.AbsContent;
import client.modele.Personnage;

import javax.swing.BoxLayout;

public class ContCaracteristiques extends AbsContent {
	
	private static final long serialVersionUID = 1L;

	private Personnage personnage;
	private Vector caracteristiques;
	private Vector elements_carac;

	public ContCaracteristiques(Vector caracteristiques){
		super("Caractéristiques");
		this.caracteristiques = caracteristiques;
		initialize();
	}
	
	public ContCaracteristiques(Personnage personnage){
		super("Caractéristiques");
		this.personnage = personnage;
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		elements_carac = new Vector();
		if(caracteristiques==null)
			caracteristiques = new Vector(personnage.getCaracteristiques());
		for(int i=0; i<caracteristiques.size(); i++){
			Caracteristique en_cours = (Caracteristique) caracteristiques.get(i);
			String nom = en_cours.getNom();
			int valeur = en_cours.getValeur();
			int valeur_min = en_cours.getValeurMin();
			int valeur_max = en_cours.getValeurMax();
			boolean is_bar = en_cours.getNom().equals("Vie");
			elements_carac.add(new PanCaract(nom, valeur_min, valeur, valeur_max, is_bar));
		}
		refreshGraphics();
	}
	
	private void refreshGraphics(){
		for(int i=0; i<elements_carac.size(); i++)
			add((PanCaract)elements_carac.get(i));
	}

	public void refresh() {
		caracteristiques = new Vector(personnage.getCaracteristiques());
		for(int i=0; i<elements_carac.size(); i++)
			((PanCaract)elements_carac.get(i)).setValeur(((Caracteristique)caracteristiques.get(i)).getValeurMin(), ((Caracteristique)caracteristiques.get(i)).getValeur(), ((Caracteristique)caracteristiques.get(i)).getValeurMax());
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Test ContCaracteristiques");
		Vector v = new Vector();
		v.add(new Caracteristique("ouais", 1, 2, 3, null));
		v.add(new Caracteristique("génial", 1, 2, 3, null));
		frame.add(new ContCaracteristiques(v));
		frame.pack();
		frame.setVisible(true);
	}
	
}

package client.gui.fenetre.contenu.quete;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.gui.fenetre.contenu.AbsContent;

import modele.objet.ObjetConcret;
import modele.quete.ObjectifQuete;

public class ChapitreQuete extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel pan_1 = null;
	private JPanel pan_2 = null;
	private JLabel var_chapitre = null;
	private JLabel lab_objectif = null;
	private JPanel pan_objetsassociés = null;
	private JLabel lab_objetsassocies = null;
	private JLabel var_chapitrerealise = null;
	private JLabel var_objectif = null;
	private JLabel var_objetsassocies = null;
	private JPanel pan_tempsrestant = null;
	private JLabel lab_tempsrestant = null;
	private JLabel var_tempsrestant = null;

	private long tempsRestant;

	/**
	 * This is the default constructor
	 */
	public ChapitreQuete() {
		super();
		initialize();
	}
	
	/**
	 * Construit un chapitre de quête
	 * @param numChapitre Numéro du chapitre
	 * @param objectif Texte descriptif de l'objectif à atteindre pour valider le chapitre
	 * @param objets Objets relatifs à ce chapitre
	 * @param tempsRestant Temps restant pour finir le chapitre
	 */
	public ChapitreQuete(int numChapitre, String objectif, ObjetConcret[] objets, long tempsRestant, boolean realise){
		super();
		initialize();
		setNumeroChapitre(numChapitre);
		setObjectif(objectif);
		setObjetsAssocies(objets);
		setTempsRestantSecondes(tempsRestant);
		setChapitreRealise(realise);
	}

	public ChapitreQuete(ObjectifQuete objectifQuete){
		super();
		initialize();
		setNumeroChapitre(objectifQuete.getNumero());
		setObjectif(objectifQuete.getDescription());
		setObjetsAssocies(null);
		setTempsRestantSecondes(-1);
		setChapitreRealise(objectifQuete.isRealise());
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray,1));
		this.add(getPan_1(), null);
		this.add(getPan_2(), null);
		this.add(getPan_objetsassociés(), null);
		this.add(getPan_tempsrestant(), null);
	}

	/**
	 * This method initializes pan_1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan_1() {
		if (pan_1 == null) {
			var_chapitrerealise = new JLabel();
			var_chapitrerealise.setText("null");
			var_chapitre = new JLabel();
			var_chapitre.setText("Chapitre ? : ");
			pan_1 = new JPanel();
			pan_1.setLayout(new BorderLayout());
			pan_1.setName("pan_1");
			pan_1.add(var_chapitre, java.awt.BorderLayout.WEST);
			pan_1.add(var_chapitrerealise, java.awt.BorderLayout.CENTER);
			pan_1.setBackground(TRANSPARENT_COLOR);
		}
		return pan_1;
	}

	/**
	 * This method initializes pan_2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan_2() {
		if (pan_2 == null) {
			var_objectif = new JLabel();
			var_objectif.setText("null");
			lab_objectif = new JLabel();
			lab_objectif.setText("Objectif : ");
			pan_2 = new JPanel();
			pan_2.setLayout(new BorderLayout());
			pan_2.setName("pan_2");
			pan_2.add(lab_objectif, java.awt.BorderLayout.WEST);
			pan_2.add(var_objectif, java.awt.BorderLayout.CENTER);
			pan_2.setBackground(TRANSPARENT_COLOR);
		}
		return pan_2;
	}

	/**
	 * This method initializes pan_objetsassociés	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan_objetsassociés() {
		if (pan_objetsassociés == null) {
			var_objetsassocies = new JLabel();
			var_objetsassocies.setText("null");
			lab_objetsassocies = new JLabel();
			lab_objetsassocies.setText("Objets associés : ");
			pan_objetsassociés = new JPanel();
			pan_objetsassociés.setLayout(new BorderLayout());
			pan_objetsassociés.add(lab_objetsassocies, java.awt.BorderLayout.WEST);
			pan_objetsassociés.add(var_objetsassocies, java.awt.BorderLayout.CENTER);
			pan_objetsassociés.setBackground(TRANSPARENT_COLOR);
		}
		return pan_objetsassociés;
	}

	/**
	 * This method initializes pan_tempsrestant	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan_tempsrestant() {
		if (pan_tempsrestant == null) {
			var_tempsrestant = new JLabel();
			var_tempsrestant.setText("null");
			lab_tempsrestant = new JLabel();
			lab_tempsrestant.setText("Temps restant : ");
			pan_tempsrestant = new JPanel();
			pan_tempsrestant.setLayout(new BorderLayout());
			pan_tempsrestant.add(lab_tempsrestant, java.awt.BorderLayout.WEST);
			pan_tempsrestant.add(var_tempsrestant, java.awt.BorderLayout.CENTER);
			pan_tempsrestant.setBackground(TRANSPARENT_COLOR);
		}
		return pan_tempsrestant;
	}
	
	public void setNumeroChapitre(int numero){
		var_chapitre.setText("Chapitre "+numero+" : ");
	}
	
	public int getNumeroChapitre(){
		return Integer.parseInt(var_chapitre.getText().split(" ")[1]);
	}
	
	public void setChapitreRealise(boolean realise){
		var_chapitrerealise.setText(realise ? "réalisé" : "à faire");
	}
	
	public boolean isChapitreRealise(){
		return var_chapitrerealise.getText().equals("réalisé");
	}
	
	public void setObjectif(String objectif){
		var_objectif.setText(objectif);
	}
	
	public String getObjectif(){
		return var_objectif.getText();
	}
	
	public void setObjetsAssocies(ObjetConcret[] objets){
		if(objets==null)
			pan_objetsassociés.setVisible(false);
		else{
			String result = "";
			for(int i=0; i<objets.length; i++){
				if(i>0) result += ", ";
				result += objets[i].getNom();
			}
			var_objetsassocies.setText(result);
		}
	}
	
	public String getObjetsAssocies(){
		return var_objetsassocies.getText();
	}
	
	/**
	 * Définit le temps restant (en secondes)
	 * @param secondes Si il n'y a pas de temps limite pour le chapitre, alors entrer la valeur -1
	 */
	public void setTempsRestantSecondes(long secondes){
		this.tempsRestant = secondes;
		if(secondes==-1){
			pan_tempsrestant.setVisible(false);
			return;
		}
		int j = (int) secondes/60/60/24;
		int h = (int) (secondes - j*24*60*60)/60/60;
		int s = (int) secondes % 60;
		int m = (int) (secondes - s)/60 % 60;
		setTempsRestant(j,h,m,s);
	}
	
	public long getTempsRestantSecondes(){
		return tempsRestant;
	}
	
	public void setTempsRestant(int jours, int heures, int minutes, int secondes){
		tempsRestant = secondes + 60*(minutes+ 60*(heures + 24*(jours)));
		String result = "";
		boolean modifie = false;
		if(jours>0){
			result += jours+" j ";
			modifie = true;
		}
		if(heures>0 || modifie){
			result += heures+" h ";
			modifie = true;
		}
		if(minutes>0 || modifie){
			result += minutes+" min ";
			modifie = true;
		}
		if(secondes>0 || modifie){
			result += secondes+" s";
			modifie = true;
		}
		if(result.equals(""))
			result = "temps écoulé !";
		var_tempsrestant.setText(result);
	}
	
	public String getTempsRestant(){
		return var_tempsrestant.getText();
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

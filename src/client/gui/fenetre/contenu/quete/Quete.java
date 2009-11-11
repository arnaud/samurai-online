package client.gui.fenetre.contenu.quete;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import modele.objet.ObjetConcret;
import modele.quete.ObjectifQuete;

/**
 * Fenêtre affichant les informations d'une quête.
 * Deux modes d'affichage sont possible : le mode complet et le mode réduit.
 * La différence se situe au niveau du choix de l'affichage des chapitres.
 * @author -kouyio-
 *
 */
public class Quete extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel pan_description = null;
	private JPanel pan_responsable = null;
	private JPanel panChapitres = null;
	private JScrollPane pan_scroll = null;

	private JLabel lab_description = null;
	private JLabel lab_reponsable = null;

	private String nomQuete;
	private JLabel var_description = null;
	private JLabel var_responsable = null;
	
	private boolean mode;
	private Vector chapitres;
	private modele.quete.Quete modeleQuete;
	
	/**
	 * Constructeur par défaut.
	 * Construit une fenêtre de Quête en mode complet.
	 *
	 */
	public Quete(){
		this(true);
	}
	
	/**
	 * Construit une fenêtre de quête avec le choix du mode d'affichage
	 * @param fullMode Si vrai, Mode complet (affiche les chapitres), sinon mode réduit (sans les chapitres)
	 */
	public Quete(boolean fullMode) {
		super();
		this.mode = fullMode;
		initialize();
	}

	
	public Quete(boolean fullMode, modele.quete.Quete modeleQuete) {
		super();
		this.mode = fullMode;
		initialize();
		this.modeleQuete = modeleQuete;
/*		try{
		modeleQuete.getInfos();
		}catch(Exception e){
			System.out.println("Exception dans quete :"+e);
		}*/
		this.setNomQuete(modeleQuete.getnomQuete());
		this.setDescription(modeleQuete.getDescriptionQuete());
		this.setResponsable("Ici nom du responsable");
		Collection objectifs = new Vector();
		objectifs =	modeleQuete.getObjectifs();
		for(Iterator it = objectifs.iterator(); it.hasNext();) {
			ObjectifQuete objectifQuete = (ObjectifQuete) it.next();
			addChapitre(objectifQuete);
		}
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setNomQuete("Quête inconnue");
		this.add(getPan_description(), null);
		this.add(getPan_responsable(), null);
		this.add(initChapitres(), null);
		if(!mode)
			pan_scroll.setVisible(false);
	}

	/**
	 * This method initializes pan_description	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan_description() {
		if (pan_description == null) {
			var_description = new JLabel();
			var_description.setText("null");
			lab_description = new JLabel();
			lab_description.setText("Description : ");
			pan_description = new JPanel();
			pan_description.setLayout(new BorderLayout());
			pan_description.add(lab_description, java.awt.BorderLayout.WEST);
			pan_description.add(var_description, java.awt.BorderLayout.CENTER);
		}
		return pan_description;
	}

	/**
	 * This method initializes pan_responsable	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan_responsable() {
		if (pan_responsable == null) {
			var_responsable = new JLabel();
			var_responsable.setText("null");
			lab_reponsable = new JLabel();
			lab_reponsable.setText("Responsable : ");
			pan_responsable = new JPanel();
			pan_responsable.setLayout(new BorderLayout());
			pan_responsable.add(lab_reponsable, java.awt.BorderLayout.WEST);
			pan_responsable.add(var_responsable, java.awt.BorderLayout.CENTER);
		}
		return pan_responsable;
	}
	
	/**
	 * This method initializes pan_scroll	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane initChapitres() {
		chapitres = new Vector();
		if (pan_scroll == null) {
			pan_scroll = new JScrollPane(getPanChapitres());
			remplirChapitres();
		}
		return pan_scroll;
	}
	
	private JPanel getPanChapitres(){
		if (panChapitres == null) {
			panChapitres = new JPanel();
			panChapitres.setLayout(new BoxLayout(panChapitres, BoxLayout.Y_AXIS));
		}
		return panChapitres;
	}
	
	private void remplirChapitres(){
		if(chapitres!=null){
			panChapitres.removeAll();
			for(int i=0; i<chapitres.size(); i++){
				panChapitres.add((ChapitreQuete)chapitres.elementAt(i), null);
			}
		}
	}
	
	public void setNomQuete(String nomQuete){
		this.nomQuete = nomQuete;
		this.setBorder(BorderFactory.createTitledBorder(null, nomQuete, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
	}
	
	public String getNomQuete(){
		return nomQuete;
	}
	
	public void setDescription(String description){
		var_description.setText(description);
	}
	
	public String getDescription(){
		return var_description.getText();
	}
	
	public void setResponsable(String responsable){
		var_responsable.setText(responsable);
	}
	
	public String getResponsable(){
		return var_responsable.getText();
	}

	public void addChapitre(int numChapitre, String objectif, ObjetConcret[] objets, long tempsRestant, boolean realise){
		chapitres.add(new ChapitreQuete(numChapitre, objectif, objets, tempsRestant, realise));
		remplirChapitres(); // Rafraîchissement des chapitres
	}
	
	public void addChapitre(ObjectifQuete objectifQuete){
		chapitres.add(new ChapitreQuete(objectifQuete));
		remplirChapitres(); // Rafraîchissement des chapitres
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Test Quete");
		modele.quete.Quete uneQuete= new modele.quete.Quete(1,"test","youhou");	
		ObjectifQuete testObjectif= new ObjectifQuete(1,"hello world");
		uneQuete.addObjectif(testObjectif);
		Quete quete = new Quete(true,uneQuete);
		
		
//		quete.setNomQuete("Quête du repas");
//		quete.setDescription("Va falloir faire la popote !!");
//		quete.setResponsable("Bob");
//		quete.addChapitre(1, "Préparer le repas", null, 123, true);
//		quete.addChapitre(2, "Manger le repas", null, 343, false);
//		quete.addChapitre(3, "Faire la vaisselle", null, 5537, false);
		frame.add(quete);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * @return
	 */
	public boolean isRealisee() {
		return modeleQuete.isRealisee();
	}
	
}

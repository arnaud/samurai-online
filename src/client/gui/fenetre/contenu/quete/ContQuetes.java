package client.gui.fenetre.contenu.quete;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import modele.quete.ObjectifQuete;
import client.Client;
import client.gui.fenetre.contenu.AbsContent;
import client.gui.fenetre.contenu.general.Bouton;
import client.modele.Monde;
import client.modele.Personnage;

import communication.CommClient;

/**
 * Fenêtre d'affichages des quêtes.
 * Regroupe l'ensemble des quêtes au format réduit.
 * Un clic sur  l'une de ces sous-fenêtres de quête permet l'affichage d'informations supplémentaires la concernant (chapitres)
 * @author -kouyio-
 *
 */
public class ContQuetes extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	private JSplitPane splitter = null;
	private JScrollPane scr_quetesencours = null;
	private JScrollPane scr_quetesfinies = null;

	private Vector quetes_en_cours;
	private Vector quetes_finies;
	private JPanel pan_quetesencours = null;
	private JPanel pan_quetesfinies = null;
	
	private Personnage personnage;
	private CommClient comm;
	private Bouton btn_quete = null;
	private Monde monde;
	/**
	 * This is the default constructor
	 */
	public ContQuetes() {
		this(null);
	}

	public ContQuetes(Monde monde) {
		super("Quêtes");
		personnage = monde.getPersonnage();
		comm = monde.getCommClient();
		quetes_en_cours = new Vector();
		quetes_finies = new Vector();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	
	private void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fenetre de quêtes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		this.setSize(new java.awt.Dimension(289,219));
		this.add(getSplitter(), null);
		this.add(getBtn_quete(), null);
		if(personnage!=null){
			for (Iterator it = personnage.getQuetes().iterator(); it.hasNext();) {
				modele.quete.Quete modeleQuete = (modele.quete.Quete ) it.next();
				addQuete(new client.gui.fenetre.contenu.quete.Quete(true,modeleQuete));
			}
		}
	}

	/**
	 * This method initializes splitter	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getSplitter() {
		if (splitter == null) {
			splitter = new JSplitPane();
			splitter.setContinuousLayout(true);
			splitter.setOneTouchExpandable(true);
			splitter.setDividerSize(8);
			splitter.setDividerLocation(200);
			splitter.setTopComponent(getScr_quetesencours());
			splitter.setBottomComponent(getScr_quetesfinies());
			splitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			splitter.setBackground(TRANSPARENT_COLOR);
		}
		return splitter;
	}

	/**
	 * This method initializes scr_quetesencours	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScr_quetesencours() {
		if (scr_quetesencours == null) {
			scr_quetesencours = new JScrollPane();
			scr_quetesencours.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scr_quetesencours.setViewportView(getPan_quetesencours());
		}
		return scr_quetesencours;
	}

	/**
	 * This method initializes scr_quetesfinies	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScr_quetesfinies() {
		if (scr_quetesfinies == null) {
			scr_quetesfinies = new JScrollPane();
			scr_quetesfinies.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scr_quetesfinies.setViewportView(getPan_quetesfinies());
			scr_quetesfinies.setBackground(TRANSPARENT_COLOR);
			//System.out.println(">>> Fabrication pan_quetefinies <<<");
		}
		return scr_quetesfinies;
	}

	/**
	 * This method initializes pan_quetesencours	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan_quetesencours() {
		if (pan_quetesencours == null) {
			pan_quetesencours = new JPanel();
			pan_quetesencours.setLayout(new BoxLayout(getPan_quetesencours(), BoxLayout.Y_AXIS));
			pan_quetesencours.setBackground(TRANSPARENT_COLOR);
			//System.out.println(">>> Fabrication pan_queteencours <<<");
		}
		return pan_quetesencours;
	}

	/**
	 * This method initializes pan_quetesfinies	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan_quetesfinies() {
		if (pan_quetesfinies == null) {
			pan_quetesfinies = new JPanel();
			pan_quetesfinies.setLayout(new BoxLayout(getPan_quetesfinies(), BoxLayout.Y_AXIS));
			pan_quetesfinies.setBackground(TRANSPARENT_COLOR);
		}
		return pan_quetesfinies;
	}
	
	/**
	 * Ajoute une quête
	 * @param quete Quête à ajouter
	 */
	public void addQuete(client.gui.fenetre.contenu.quete.Quete quete){
		if(quete.isRealisee()){
			quetes_en_cours.add(quete);
			//System.out.println(">>> Ajout dans quetes en cours <<<");
		}
		else{
			quetes_finies.add(quete);	
			//System.out.println(">>> Ajout dans quetes finies <<<");
		}
		update();
	}
	
	/**
	 * Mise à jour utilisée après l'ajout de quêtes.
	 * Permet un rafraîchissement des fenêtres en conséquence.
	 */
	private void update(){
		
		pan_quetesencours.removeAll();
		//System.out.println(">>>>> [GUI] Quetes ["+personnage.getQuetes().size()+"] :");
		for(Iterator it = personnage.getQuetes().iterator(); it.hasNext();){
			modele.quete.Quete modeleQuete = (modele.quete.Quete)it.next();
			Quete quete = new Quete(true, modeleQuete);
			//modeleQuete.getInfos();
			if(!quete.isRealisee()){
				pan_quetesencours.add(quete);
			//System.out.println("		Quete n°"+modeleQuete.getId());
			}
		}
		pan_quetesfinies.removeAll();
		for(Iterator it = personnage.getQuetes().iterator(); it.hasNext();){
			modele.quete.Quete modeleQuete = (modele.quete.Quete)it.next();
			Quete quete = new Quete(true, modeleQuete);
			//System.out.println(">>>>> [GUI] Quete realisee : <<<<<");
			//modeleQuete.getInfos();
			if(quete.isRealisee())
				pan_quetesfinies.add(quete);
		}
		System.out.println("");
	}
	
	Collection quetes;

	public void refresh() {
		if(quetes != personnage.getQuetes()){
			update();
			quetes = personnage.getQuetes();
		}
	}
	
	/**
	 * This method initializes btn_quete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn_quete() {
		if (btn_quete == null) {
			btn_quete = new Bouton("Nouvelle quête");
			btn_quete.setActionCommand("Nouvelle action");
			btn_quete.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try{
						System.out.println(">>>>>>>>>>>>>>>>>>["+ e.getActionCommand().toString() +"]");
						comm.askQuest(2);
						System.out.println(">>>>>>>>>>>>>>>>>>[Demande de quete realisee]");
					}catch(Exception e2){
						System.out.println("Exception : "+e2);
					}
				}
			});
		}
		return btn_quete;
	}

	public static void main(String[] args){
		JFrame frame = new JFrame("Test ContQuetes");
		ContQuetes vueContQuetes = new ContQuetes();
		frame.add(vueContQuetes);
		modele.quete.Quete uneQuete= new modele.quete.Quete(1,"test","youhou");	
		ObjectifQuete testObjectif= new ObjectifQuete(1,"hello world");
		uneQuete.addObjectif(testObjectif);
		client.gui.fenetre.contenu.quete.Quete vueQuete = new client.gui.fenetre.contenu.quete.Quete(true,uneQuete);
		vueContQuetes.addQuete(vueQuete);
		frame.pack();
		frame.setVisible(true);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

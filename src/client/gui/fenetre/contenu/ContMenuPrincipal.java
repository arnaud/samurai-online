package client.gui.fenetre.contenu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import client.gui.GuiPrincipal;
import client.gui.fenetre.contenu.general.Bouton;
import client.modele.Monde;

import com.jme.scene.Node;

public class ContMenuPrincipal extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	
	private Monde monde;
	
	private Bouton btn1 = null;
	private Bouton btn2 = null;
	private Bouton btn3 = null;
	private Bouton btn4 = null;
	private Bouton btn5 = null;
	private Bouton btn6 = null;

	/**
	 * This is the default constructor
	 */
	public ContMenuPrincipal(Monde monde) {
		super("Menu Principal", new GridLayout(6,1));
		this.monde = monde;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		add(getBtn1());
		add(getBtn2());
		add(getBtn3());
		add(getBtn4());
		add(getBtn5());
		add(getBtn6());
	}

	/**
	 * This method initializes btn1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn1() {
		if (btn1 == null) {
			btn1 = new Bouton("Feuille de personnage");
		}
		return btn1;
	}

	/**
	 * This method initializes btn2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn2() {
		if (btn2 == null) {
			btn2 = new Bouton("Inventaire");
			btn2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					monde.getGUI().switchInventaire();
				}
			});
		}
		return btn2;
	}

	/**
	 * This method initializes btn3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn3() {
		if (btn3 == null) {
			btn3 = new Bouton("Compétences");
		}
		return btn3;
	}

	/**
	 * This method initializes btn4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn4() {
		if (btn4 == null) {
			btn4 = new Bouton("Journal de quêtes");
			btn4.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					monde.getGUI().switchQuetes();
				}
			});
		}
		return btn4;
	}

	/**
	 * This method initializes btn5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn5() {
		if (btn5 == null) {
			btn5 = new Bouton("Paramètres");
		}
		return btn5;
	}

	/**
	 * This method initializes btn6	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtn6() {
		if (btn6 == null) {
			btn6 = new Bouton("Se déconnecter");
			btn6.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Monde.exit();
				}
			});
		}
		return btn6;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}

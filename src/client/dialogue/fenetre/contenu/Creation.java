/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.dialogue.fenetre.contenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import client.util.SpringUtilities;


/**
 * Fenêtre de création de compte
 * 
 */
public class Creation extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JLabel lab_nom = null;
	public JTextField tf_nom = null;
	private JLabel lab_pass = null;
	public JPasswordField tf_pass = null;
	private JLabel lab_email = null;
	public JTextField tf_email = null;
	private JLabel lab_sexe = null;
	public JComboBox cb_sexe;
	private JButton btn_annuler = null;
	
	public ActionListener actionBtnAnnuler;
	public ActionListener actionBtnValider;
	
	/**
	 * This method initializes 
	 * 
	 */
	public Creation() {
		super();
		setLayout(new BorderLayout());
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        
        //--- Début HAUT
        
        jPanel3 = new JPanel();
        jPanel3.setLayout(new BorderLayout());
        add(jPanel3, BorderLayout.NORTH);
        
        JEditorPane message = new JEditorPane();
        message.setBackground(new Color(240,240,240));
        message.setEditable(false);
        message.setContentType("text/html");
        message.setText("<center><b>Veuillez remplir les champs</b><br>Note : l'adresse mail doit être valide.</center>");
        //message.setBackground(new Color(0,0,0,0));
        jPanel3.add(message, BorderLayout.CENTER);
        
        //--- Fin HAUT
        
        //--- Début CENTRE
        
		jPanel = new JPanel();
		jPanel.setLayout(new SpringLayout());
        add(jPanel, BorderLayout.CENTER);
        
		lab_nom = new JLabel("Nom : ", JLabel.TRAILING);
		jPanel.add(lab_nom);
        
		tf_nom = new JTextField();
		tf_nom.addKeyListener(this);
		jPanel.add(tf_nom);
        
		lab_pass = new JLabel("Mot de passe : ", JLabel.TRAILING);
		jPanel.add(lab_pass);
		
		tf_pass = new JPasswordField();
		tf_pass.addKeyListener(this);
		jPanel.add(tf_pass);
        
		lab_email = new JLabel("Addresse mail : ", JLabel.TRAILING);
		jPanel.add(lab_email);
		
		tf_email = new JTextField();
		tf_email.addKeyListener(this);
		jPanel.add(tf_email);
		
		lab_sexe = new JLabel("Sexe : ", JLabel.TRAILING);
		jPanel.add(lab_sexe);
		
		String[] options = {"Homme", "Femme"};
		cb_sexe = new JComboBox(options);
		cb_sexe.addKeyListener(this);
		jPanel.add(cb_sexe);
		
		SpringUtilities.makeCompactGrid(jPanel,
                4, 2,		//rows, cols
                6, 6,		//initX, initY
                6, 6);		//xPad, yPad
		
		//--- Fin CENTRE
		
		//--- Début BAS

		jPanel2 = new JPanel();
		jPanel2.setLayout(new FlowLayout());
        add(jPanel2, BorderLayout.SOUTH);
        
        btn_annuler = new JButton("Annuler");
        jPanel2.add(btn_annuler);
        
        btn_valider = new JButton("Valider");
        jPanel2.add(btn_valider);
		
		//--- Fin BAS
		
	}
	
	public void addActions(){
		btn_valider.addActionListener(actionBtnValider);
		btn_annuler.addActionListener(actionBtnAnnuler);
	}
	
	/**
	 * Teste la connexion au serveur avec les informations fournies dans le formulaire.
	 * @return
	 */
	public boolean verifierConnexion(){
    	try {
    		//TODO: Ajouter le test de connexion au serveur
    		int portClient = Integer.parseInt(tf_email.getText());
            new DatagramSocket(portClient);
            return true;
        } catch (Exception e) {
        	return false;
        }
	}
	
 	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(new Creation(), BorderLayout.CENTER);
		frame.setSize(500,400);
		frame.setVisible(true);
	}
  }  //  @jve:decl-index=0:visual-constraint="10,10"

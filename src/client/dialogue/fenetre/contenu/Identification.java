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

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import client.util.SpringUtilities;

/**
 * Fenêtre d'identification
 * 
 */
public class Identification extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JLabel lab_identifiant = null;
	public JTextField tf_identifiant = null;
	private JLabel lab_password = null;
	public JPasswordField tf_password = null;
	private JButton creer = null;
	
	public ActionListener actionBtnCreer;
	public ActionListener actionBtnLogin;
	
	/**
	 * This method initializes 
	 * 
	 */
	public Identification() {
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
        message.setText("<center><b>Bienvenue à <i>Samurai Online</i> !</b><br>Veuillez vous identifier :</center>");
        //message.setBackground(new Color(0,0,0,0));
        jPanel3.add(message, BorderLayout.CENTER);
        
        //--- Fin HAUT
        
        //--- Début CENTRE
        
		jPanel = new JPanel();
		jPanel.setLayout(new SpringLayout());
        add(jPanel, BorderLayout.CENTER);
        
		lab_identifiant = new JLabel("Identifiant : ", JLabel.TRAILING);
		jPanel.add(lab_identifiant);
        
		tf_identifiant = new JTextField("");
		tf_identifiant.addKeyListener(this);
		jPanel.add(tf_identifiant);
        
		lab_password = new JLabel("Mot de passe : ", JLabel.TRAILING);
		jPanel.add(lab_password);
		
		tf_password = new JPasswordField("");
		tf_password.addKeyListener(this);
		jPanel.add(tf_password);
		
		SpringUtilities.makeCompactGrid(jPanel,
                2, 2,		//rows, cols
                6, 6,		//initX, initY
                6, 6);		//xPad, yPad
		
		//--- Fin CENTRE
		
		//--- Début BAS

		jPanel2 = new JPanel();
		jPanel2.setLayout(new FlowLayout());
        add(jPanel2, BorderLayout.SOUTH);
        
        creer = new JButton("Créer un compte");
        jPanel2.add(creer);
        
        btn_valider = new JButton("Entrer");
        jPanel2.add(btn_valider);
		
		//--- Fin BAS
		
	}
	
	public void addActions(){
		btn_valider.addActionListener(actionBtnLogin);
		creer.addActionListener(actionBtnCreer);
	}
	
 	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(new Identification(), BorderLayout.CENTER);
		frame.setSize(500,400);
		frame.setVisible(true);
	}
  }  //  @jve:decl-index=0:visual-constraint="10,10"

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
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import client.util.SpringUtilities;


/**
 * Fenêtre de connexion
 * 
 */
public class Connexion extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel jPanel = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JLabel lab_serveur = null;
	public JTextField tf_serveur = null;
	private JLabel lab_port = null;
	public JTextField tf_port = null;
	private JLabel lab_port_cl = null;
	public JTextField tf_port_cl = null;
	private JButton btn_aide = null;
	
	public ActionListener actionBtnAide;
	public ActionListener actionBtnConnect;
	
	/**
	 * This method initializes 
	 * 
	 */
	public Connexion() {
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
        message.setText("<center><b>Bienvenue à <i>Samurai Online</i> !</b><br>Choisissez le serveur de jeu à rejoindre :</center>");
        //message.setBackground(new Color(0,0,0,0));
        jPanel3.add(message, BorderLayout.CENTER);
        
        //--- Fin HAUT
        
        //--- Début CENTRE
        
		jPanel = new JPanel();
		jPanel.setLayout(new SpringLayout());
        add(jPanel, BorderLayout.CENTER);
        
		lab_serveur = new JLabel("Serveur : ", JLabel.TRAILING);
		jPanel.add(lab_serveur);
        
		tf_serveur = new JTextField();
		tf_serveur.setText("80.248.208.94");
		//tf_serveur.setText("localhost");
		tf_serveur.addKeyListener(this);
		jPanel.add(tf_serveur);
        
		lab_port = new JLabel("Port du serveur: ", JLabel.TRAILING);
		jPanel.add(lab_port);
		
		tf_port = new JTextField();
		tf_port.setText("8010");
		tf_port.addKeyListener(this);
		jPanel.add(tf_port);
        
		lab_port_cl = new JLabel("Port du client : ", JLabel.TRAILING);
		jPanel.add(lab_port_cl);
		
		tf_port_cl = new JTextField();
		tf_port_cl.setText("5010");
		tf_port_cl.addKeyListener(this);
		jPanel.add(tf_port_cl);
		
		SpringUtilities.makeCompactGrid(jPanel,
                3, 2,		//rows, cols
                6, 6,		//initX, initY
                6, 6);		//xPad, yPad
		
		//--- Fin CENTRE
		
		//--- Début BAS

		jPanel2 = new JPanel();
		jPanel2.setLayout(new FlowLayout());
        add(jPanel2, BorderLayout.SOUTH);
        
        btn_aide = new JButton("A propos");
        jPanel2.add(btn_aide);
        
		btn_valider = new JButton("Se connecter");
        jPanel2.add(btn_valider);
		
		//--- Fin BAS
		
	}
	
	public void addActions(){
		btn_valider.addActionListener(actionBtnConnect);
		btn_aide.addActionListener(actionBtnAide);
	}
	
 	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(new Connexion(), BorderLayout.CENTER);
		frame.setSize(500,400);
		frame.setVisible(true);
	}
}

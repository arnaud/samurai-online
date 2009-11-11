/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.dialogue.fenetre.contenu;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SpringLayout;

import client.util.SpringUtilities;

/** 
 * Fenêtre d'attente
 * 
 */
public class FenetreAttente extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel jPanel = null;
	private JPanel jPanel3 = null;
	private JLabel lab_patience = null;
	private JProgressBar pBar = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	public FenetreAttente(String msg) {
		super();
		setLayout(new BorderLayout());
		initialize(msg);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(String msg) {
        
        //--- Début HAUT
        
        jPanel3 = new JPanel();
        jPanel3.setLayout(new BorderLayout());
        add(jPanel3, BorderLayout.NORTH);
        
        JEditorPane message = new JEditorPane();
        message.setBackground(new Color(240,240,240));
        message.setEditable(false);
        message.setContentType("text/html");
        message.setText(msg);
        //message.setBackground(new Color(0,0,0,0));
        jPanel3.add(message, BorderLayout.CENTER);
        
        //--- Fin HAUT
        
        //--- Début CENTRE
        
		jPanel = new JPanel();
		jPanel.setLayout(new SpringLayout());
        add(jPanel, BorderLayout.CENTER);
        
        lab_patience = new JLabel("Veuillez patienter...");
        jPanel.add(lab_patience);
        
        pBar = new JProgressBar();
        pBar.setIndeterminate(true);
        pBar.setMaximum(5);
		jPanel.add(pBar);
		
		SpringUtilities.makeCompactGrid(jPanel,
                2, 1,		//rows, cols
                6, 6,		//initX, initY
                6, 6);		//xPad, yPad
		
		//--- Fin CENTRE
		
	}
	
	public void stopAttente(String messageOk){
		//TODO: Faire en sorte qu'il y ait une attente
		lab_patience.setText(messageOk);
		pBar.setIndeterminate(false);
		pBar.setValue(pBar.getMaximum());
	}
	
}

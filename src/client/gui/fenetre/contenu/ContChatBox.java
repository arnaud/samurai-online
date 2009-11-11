package client.gui.fenetre.contenu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import client.gui.fenetre.contenu.general.Bouton;
import client.modele.Personnage;

import com.jme.scene.Node;
import communication.CommClient;

public class ContChatBox extends AbsContentInputText {
	
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabPane = null;
	private JPanel pan1 = null;
	private JScrollPane scrollChatGeneral = null;
	public JEditorPane textChatGeneral = null;
	private JPanel panActionsGeneral = null;
	private JTextField chatGeneral = null;
	private Bouton btnOkGeneral = null;
	
	private JPanel pan2 = null;
	private JScrollPane scrollChatClan = null;
	public JEditorPane textChatClan = null;
	private JPanel panActionsClan = null;
	private JTextField chatClan = null;
	private Bouton btnOkClan = null;
	
	private JPanel pan3 = null;
	private JScrollPane scrollChatEquipe = null;
	public JEditorPane textChatEquipe = null;
	private JPanel panActionsEquipe = null;
	private JTextField chatEquipe = null;
	private Bouton btnOkEquipe = null;
	
	private Node rootNode;
	private CommClient commClient;
	
	

	/**
	 * This is the default constructor
	 */
	public ContChatBox(Node rootNode, CommClient commClient) {
		super("Fenêtre de conversation");
		this.rootNode = rootNode;
		this.commClient = commClient;
		initialize();
		addInfoText(textChatClan, "Fenêtre de conversation du clan activée");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.add(getTabPane(), java.awt.BorderLayout.CENTER);
	}

	/**
	 * This method initializes tabPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getTabPane() {
		if (tabPane == null) {
			tabPane = new JTabbedPane();
			tabPane.setUI(new BasicTabbedPaneUI());
			tabPane.addTab("Général", getPan1());
			tabPane.addTab("Clan", getPan2());
			tabPane.addTab("Groupe", getPan3());
		}
		return tabPane;
	}

	/**
	 * This method initializes pane1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan1() {
		if (pan1 == null) {
			pan1 = new JPanel(new BorderLayout());
			pan1.add(getScrollChatGeneral(), BorderLayout.CENTER);
			pan1.add(getPanActionsGeneral(), BorderLayout.SOUTH);
		}
		return pan1;
	}

	/**
	 * This method initializes pane1	
	 * 	
	 * @return javax.swing.JPane2	
	 */
	private JPanel getPan2() {
		if (pan2 == null) {
			pan2 = new JPanel(new BorderLayout());
			pan2.add(getScrollChatClan(), BorderLayout.CENTER);
			pan2.add(getPanActionsClan(), BorderLayout.SOUTH);
		}
		return pan2;
	}

	/**
	 * This method initializes pane3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPan3() {
		if (pan3 == null) {
			pan3 = new JPanel(new BorderLayout());
			pan3.add(getScrollChatEquipe(), BorderLayout.CENTER);
			pan3.add(getPanActionsEquipe(), BorderLayout.SOUTH);
		}
		return pan3;
	}

	/**
	 * This method initializes scrollChatGeneral	
	 * 	
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getScrollChatGeneral() {
		if (scrollChatGeneral == null) {
			scrollChatGeneral = new JScrollPane();
			scrollChatGeneral.setViewportView(getTextChatGeneral());
			scrollChatGeneral.setBackground(TRANSPARENT_COLOR);
		}
		return scrollChatGeneral;
	}

	/**
	 * This method initializes textChatGeneral	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getTextChatGeneral() {
		if (textChatGeneral == null) {
			textChatGeneral = new JEditorPane("text/html", "");
			textChatGeneral.setEditable(false);
			textChatGeneral.setBackground(TRANSPARENT_COLOR);
		}
		return textChatGeneral;
	}

	/**
	 * This method initializes panActionsGeneral	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanActionsGeneral() {
		if (panActionsGeneral == null) {
			panActionsGeneral = new JPanel(new BorderLayout());
			panActionsGeneral.add(getChatGeneral(), BorderLayout.CENTER);
			panActionsGeneral.add(getBtnOkGeneral(), BorderLayout.EAST);
		}
		return panActionsGeneral;
	}

	/**
	 * This method initializes chatGeneral	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getChatGeneral() {
		if (chatGeneral == null) {
			chatGeneral = new JTextField();
			chatGeneral.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						addText(textChatGeneral, getNomPersonnage(), chatGeneral);
						commClient.sendText(chatGeneral.getText());
						chatGeneral.setText("");
					}
				}
			});
		}
		return chatGeneral;
	}

	/**
	 * This method initializes btnOkGeneral	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOkGeneral() {
		if (btnOkGeneral == null) {
			btnOkGeneral = new Bouton("ok");
			btnOkGeneral.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					addText(textChatGeneral, getNomPersonnage(), chatGeneral);
					commClient.sendText(chatGeneral.getText());
					chatGeneral.setText("");
				}
			});
		}
		return btnOkGeneral;
	}


	/**
	 * This method initializes scrollChatClan	
	 * 	
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getScrollChatClan() {
		if (scrollChatClan == null) {
			scrollChatClan = new JScrollPane();
			scrollChatClan.setViewportView(getTextChatClan());
			scrollChatClan.setBackground(TRANSPARENT_COLOR);
		}
		return scrollChatClan;
	}

	/**
	 * This method initializes textChatClan	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getTextChatClan() {
		if (textChatClan == null) {
			textChatClan = new JEditorPane("text/html", "");
			textChatClan.setEditable(false);
			textChatClan.setBackground(TRANSPARENT_COLOR);
		}
		return textChatClan;
	}

	/**
	 * This method initializes panActionsClan	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanActionsClan() {
		if (panActionsClan == null) {
			panActionsClan = new JPanel(new BorderLayout());
			panActionsClan.add(getChatClan(), BorderLayout.CENTER);
			panActionsClan.add(getBtnOkClan(), BorderLayout.EAST);
		}
		return panActionsClan;
	}

	/**
	 * This method initializes chatClan	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getChatClan() {
		if (chatClan == null) {
			chatClan = new JTextField();
			chatClan.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						addText(textChatClan, getNomPersonnage(), chatClan);
						commClient.sendTextClan(chatClan.getText());
						chatClan.setText("");
					}
				}
			});
		}
		return chatClan;
	}

	/**
	 * This method initializes btnOkClan	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOkClan() {
		if (btnOkClan == null) {
			btnOkClan = new Bouton("ok");
			btnOkClan.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					addText(textChatClan, getNomPersonnage(), chatClan);
					commClient.sendTextClan(chatClan.getText());
					chatClan.setText("");
				}
			});
		}
		return btnOkClan;
	}


	/**
	 * This method initializes scrollChatEquipe	
	 * 	
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getScrollChatEquipe() {
		if (scrollChatEquipe == null) {
			scrollChatEquipe = new JScrollPane();
			scrollChatEquipe.setViewportView(getTextChatEquipe());
			scrollChatEquipe.setBackground(TRANSPARENT_COLOR);
		}
		return scrollChatEquipe;
	}

	/**
	 * This method initializes textChatEquipe	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getTextChatEquipe() {
		if (textChatEquipe == null) {
			textChatEquipe = new JEditorPane("text/html", "");
			textChatEquipe.setEditable(false);
			textChatEquipe.setBackground(TRANSPARENT_COLOR);
		}
		return textChatEquipe;
	}

	/**
	 * This method initializes panActionsEquipe	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanActionsEquipe() {
		if (panActionsEquipe == null) {
			panActionsEquipe = new JPanel(new BorderLayout());
			panActionsEquipe.add(getChatEquipe(), BorderLayout.CENTER);
			panActionsEquipe.add(getBtnOkEquipe(), BorderLayout.EAST);
		}
		return panActionsEquipe;
	}

	/**
	 * This method initializes chatEquipe	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getChatEquipe() {
		if (chatEquipe == null) {
			chatEquipe = new JTextField();
			chatEquipe.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						addText(textChatEquipe, getNomPersonnage(), chatEquipe);
						commClient.sendTextEquipe(chatEquipe.getText());
						chatEquipe.setText("");
					}
				}
			});
		}
		return chatEquipe;
	}

	/**
	 * This method initializes btnOkEquipe	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnOkEquipe() {
		if (btnOkEquipe == null) {
			btnOkEquipe = new Bouton("ok");
			btnOkEquipe.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					addText(textChatEquipe, getNomPersonnage(), chatEquipe);
					commClient.sendTextEquipe(chatEquipe.getText());
					chatEquipe.setText("");
				}
			});
		}
		return btnOkEquipe;
	}
	
	/**
	 * Revoit le nom du personnage du joueur.
	 * @return
	 */
	private String getNomPersonnage(){
		// Pas génial génial de prendre le premier enfant pour avoir le perso
		if(rootNode!=null){
			Personnage perso = (Personnage)rootNode.getChild("Personnage");
			return perso.getNom();
		}
		else
			return "null";
	}

	public void refresh() {
		chatGeneral.repaint();
		textChatGeneral.repaint();
		chatClan.repaint();
		textChatClan.repaint();
		chatEquipe.repaint();
		textChatEquipe.repaint();
	}

}

package client.dialogue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * Fenêtre de dialogue
 * @author Arnaud
 *
 */
public class Dialogue extends JDialog {
	
	private static final long serialVersionUID = 1L;
	boolean aEteOuvert; // Permet de n'ouvrir un lien dans le navigateur qu'une seule fois

	/**
	 * Constructeur par défaut
	 * @param nom Titre de la fenêtre
	 * @param message Message inclus dans la fenêtre
	 */
	public Dialogue(String nom, String message){
		super();
		setTitle(nom);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
        JEditorPane pane = new JEditorPane();
        pane.setBackground(new Color(240,240,240));
        pane.setEditable(false);
        pane.setContentType("text/html");
        pane.setText(message);
        pane.addHyperlinkListener(new HyperlinkListener(){
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if(!aEteOuvert){
					boolean isLinuxOS = System.getProperty("os.name").equals("Linux");
					String url = e.getURL().toString();
					try {
						if(isLinuxOS)
							Runtime.getRuntime().exec("firefox "+url); // linux
						else
							Runtime.getRuntime().exec("cmd /c "+url); // windows
					} catch (IOException e1) {
						System.err.println("N'a pas pu ouvrir le site internet.");
						System.err.println(" - soit votre OS n'est pas supporté,");
						System.err.println(" - soit vous n'avez pas de navigateur compatible.");
					}
				}
				aEteOuvert = true;
			}
        });
        setAlwaysOnTop(true);
		add(pane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		JButton fermer = new JButton("Fermer");
		fermer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panel.add(fermer);
		add(panel, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
}

package client.gui.fenetre.contenu.caract;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class PanCaract extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel lab_carac = null;
	private JLabel var_carac = null;
	private PanelCarac panel = null;
	private boolean is_bar;

	/**
	 * This is the default constructor
	 */
	public PanCaract() {
		super();
		initialize();
	}
	
	public PanCaract(String nom, int valeur_min, int valeur, int valeur_max, boolean is_bar) {
		super();
		this.is_bar = is_bar;
		initialize();
		setNom(nom);
		if(is_bar){
			panel = new PanelCarac(valeur_min, valeur, valeur_max, Color.RED);
			add(panel, java.awt.BorderLayout.EAST);
		}else{
			add(var_carac, java.awt.BorderLayout.EAST);
		}
		setValeur(valeur_min, valeur, valeur_max);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		var_carac = new JLabel();
		var_carac.setText("?");
		lab_carac = new JLabel();
		lab_carac.setText("?");
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);
		this.add(lab_carac, java.awt.BorderLayout.CENTER);
	}

	public void setNom(String nom){
		lab_carac.setText(nom);
	}

	public void setValeur(int valeur_min, int valeur, int valeur_max){
		if(is_bar){
			panel.setString(valeur+" / "+valeur_max);
		}else{
			if(Integer.MAX_VALUE==valeur_max)
				var_carac.setText(""+valeur);
			else
				var_carac.setText(""+valeur+" / "+valeur_max);
		}
	}
	
	private class PanelCarac extends JProgressBar {
		
		private static final long serialVersionUID = 1L;

		public PanelCarac(int valmin, int val, int valmax, Color couleur){
			super();
			setUI(new BasicProgressBarUI());
			setForeground(couleur);
			setBackground(new Color(0f,0f,0f,0f));
			setValue(val);
			setMinimum(valmin);
			setMaximum(valmax);
			setString(val+" / "+valmax);
			setFont(new Font(null, Font.PLAIN, 11));
			setStringPainted(true);
			setPreferredSize(new Dimension(80,14));
		}
	}
}

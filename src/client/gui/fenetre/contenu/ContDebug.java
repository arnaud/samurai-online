package client.gui.fenetre.contenu;

import java.awt.GridLayout;

import javax.swing.JLabel;

public class ContDebug extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel lab1 = null;
	private JLabel lab2 = null;
	private JLabel lab3 = null;
	private JLabel lab4 = null;
	private JLabel lab5 = null;
	private JLabel lab6 = null;

	public ContDebug() {
		super("Debug", new GridLayout(3,2));
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        lab1 = new JLabel("Position X : ");
        lab1.setForeground(DEFAULT_FG_COLOR);
        lab2 = new JLabel("...");
        lab2.setForeground(DEFAULT_FG_COLOR);
        lab3 = new JLabel("Position Y : ");
        lab3.setForeground(DEFAULT_FG_COLOR);
        lab4 = new JLabel("...");
        lab4.setForeground(DEFAULT_FG_COLOR);
        lab5 = new JLabel("Position Z : ");
        lab5.setForeground(DEFAULT_FG_COLOR);
        lab6 = new JLabel("...");
        lab6.setForeground(DEFAULT_FG_COLOR);
        this.add(lab1);
        this.add(lab2);
        this.add(lab3);
        this.add(lab4);
        this.add(lab5);
        this.add(lab6);
			
	}
	
	public void setPositionX(float pos) {
		lab2.setText("["+pos+"]");
	}
	
	public void setPositionY(float pos) {
		lab4.setText("["+pos+"]");
	}
	
	public void setPositionZ(float pos) {
		lab6.setText("["+pos+"]");
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}

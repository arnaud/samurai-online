package client.gui.fenetre.contenu;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ContExample extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	

	public ContExample(){
		super("Gui - exemple");
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.add(new JPanel(), BorderLayout.CENTER);
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
}

package client.gui.fenetre.contenu;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ContConsole extends AbsContentInputText {
	
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollChatGeneral = null;
	private JEditorPane textChatGeneral = null;
	private JPanel panActionsGeneral = null;
	private JTextField chatGeneral = null;
	
	

	/**
	 * This is the default constructor
	 */
	public ContConsole() {
		super("Console");
		initialize();
		addInfoText(textChatGeneral, "Console activée");
		addInfoText(textChatGeneral, "Taper la commande /help pour afficher l'aide");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.add(getScrollChatGeneral(), BorderLayout.CENTER);
		this.add(getPanActionsGeneral(), BorderLayout.SOUTH);
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
						makeAction(textChatGeneral, chatGeneral.getText());
						chatGeneral.setText("");
					}
				}
			});
		}
		return chatGeneral;
	}
	
	private void makeAction(JEditorPane editor, String commande){
		if(commande.charAt(0)=='/'){
			String action;
			if(commande.contains(" "))
				action = commande.substring(1,commande.indexOf(' '));
			else
				action = commande.substring(1);
			String[] params = commande.split(" ");
			if(action.equals("help"))
				addText(editor, ConsoleActions.getHelp());
			else if(action.equals("version"))
				addText(editor, ConsoleActions.getVersion());
			else if(action.equals("display"))
				addText(editor, ConsoleActions.getDisplay());
			else if(action.equals("position"))
				if(params.length>0){
					try {
					addText(editor, ConsoleActions.setPosition(Float.parseFloat(params[1]), Float.parseFloat(params[2])));
					}catch(Exception e){}
				}
				else
				addText(editor, ConsoleActions.getPosition());
			else if(action.equals("fps")){
				if(params.length>0)
					addText(editor, ConsoleActions.setFps(params[0].equals("on")));
			}
			else
				addText(editor, "Commande '"+action+"' non reconnue !");
		}else
			addText(editor, "Commande '"+commande+"' non reconnue !");
	}
	
	public void print(String message){
		addText(getTextChatGeneral(),message);
	}
	
	public void println(String message){
		print(message+"\n");
	}

	public void refresh() {
		scrollChatGeneral.repaint();
		textChatGeneral.repaint();
		panActionsGeneral.repaint();
		chatGeneral.repaint();
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"

package client.gui.fenetre.contenu;

import javax.swing.JEditorPane;
import javax.swing.JTextField;

public abstract class AbsContentInputText extends AbsContent {

	public AbsContentInputText(String nom) {
		super(nom);
	}
	
	private String mefText(String message, String couleur, String options){
		String res = "<font color='"+couleur+"'>"+message+"</font>";
		if(options==null) return res;
		for(int i=0; i<options.length(); i++){
			char option = options.charAt(i);
			res = "<"+option+">"+res+"</"+option+">";
		}
		return res;
	}
	
	public void addInfoText(JEditorPane editor, String message){
		String texte = editor.getText();
		int posFin = getTextEndPosition(editor);
		String newText = texte.substring(0, posFin) + ((posFin!=0)?"<br>":"") + mefText("-- "+message+" --", "#dddddd", "i") + "<br></body></html>";
		editor.setText(newText);
	}

	public void addText(JEditorPane editor, String message){
		String texte = editor.getText();
		int posFin = getTextEndPosition(editor);
		String newText = texte.substring(0, posFin) + ((posFin!=0)?"<br>":"")+"" + mefText(message, "white", null) + "<br></body></html>";
		editor.setText(newText);
	}

	public void addText(JEditorPane editor, String nomAuteur, JTextField textField){
		addText(editor, nomAuteur, textField.getText());
	}
	
	public void addText(JEditorPane editor, String nomAuteur, String message){
		String texte = editor.getText();
		int posFin = getTextEndPosition(editor);
		String newText = texte.substring(0, posFin) + ((posFin!=0)?"<br>":"")+ mefText(nomAuteur, "yellow", "b") + mefText(" : " + message, "white", null) + "<br></body></html>";
		editor.setText(newText);
	}
	
	protected int getTextEndPosition(JEditorPane editor){
		String texte = editor.getText();
		int pos = texte.lastIndexOf("<br>");
		if(pos<0) pos = 0;
		return pos;
	}
}

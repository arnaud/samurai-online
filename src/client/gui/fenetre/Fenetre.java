package client.gui.fenetre;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import client.gui.fenetre.contenu.AbsContent;

import com.jme.input.InputHandler;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.jmex.awt.swingui.JMEDesktop;

public class Fenetre extends JMEDesktop {
	
	private static final long serialVersionUID = 1L;
	
	//private Node parentNode;
	private JPanel contenu;
	private String nom;
	protected InputHandler input;
	private DisplaySystem display;
	public int width, height;
	public int posx, posy, posz;

	public Fenetre(Node parentNode, String nom, InputHandler input, DisplaySystem display, int width, int height, int posx, int posy, int posz) {
		super(nom, width, height, input);
		this.nom = nom;
		this.input = input;
		this.display = display;
		//this.parentNode = parentNode;
		this.width = width;
		this.height = height;
		this.posx = posx+width/2;
		this.posy = posy+height/2;
		this.posz = posz;
        this.setLocalTranslation(new Vector3f(this.posx, display.getHeight()-this.posy, this.posz));
        this.setRenderQueueMode(Renderer.QUEUE_ORTHO);
        this.setCullMode(Spatial.CULL_NEVER);
        this.setLightCombineMode(LightState.OFF);
	}
	
	public void setPosition(int posx, int posy, int posz) {
		this.posx = posx+width/2;
		this.posy = posy+height/2;
		this.posz = posz;
        this.getLocalTranslation().set( this.posx, display.getHeight()-this.posy, this.posz);
	}
	
	public void setContenu(JPanel contenu){
		this.contenu = contenu;
		try {
			((AbsContent)contenu).setNormalHeight(height);
		}catch(Exception e) {}
		this.getJDesktop().setLayout(new BorderLayout());
        this.getJDesktop().add(contenu, BorderLayout.CENTER);
        //setBgColor(contenu);
	}
	
	public void setBackground(Color c){
        this.getJDesktop().setBackground(c);
	}
	
	public void refresh(){
		try{
			//((AbsContent)contenu).refresh();
		}catch(ClassCastException e){} // Pour les icônes
		try{//windows
			getJDesktop().updateUI();
		}catch(Exception e){//linux
			getJDesktop().repaint();
		}
	}
	
	public String toString(){
		return "fenetre [nom="+nom+", posx="+posx+", posy="+posy+", posz="+posz+", width="+width+", height="+height+"]";
	}
	
	public AbsContent getContenu(){
		try{
			return (AbsContent)contenu;
		}catch(Exception e){}
		return null;
	}
}

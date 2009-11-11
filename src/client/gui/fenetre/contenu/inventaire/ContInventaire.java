package client.gui.fenetre.contenu.inventaire;

/*
 * DragPictureDemo.java is a 1.4 example that
 * requires the following files:
 *     Picture.java
 *     DTPicture.java
 *     PictureTransferHandler.java
 *     images/Maya.jpg
 *     images/Anya.jpg
 *     images/Laine.jpg
 *     images/Cosmo.jpg
 *     images/Adele.jpg
 *     images/Alexi.jpg
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import modele.objet.ObjetConcret;
import util.loader.ObjetInventaireLoader;
import client.gui.fenetre.contenu.AbsContent;

public class ContInventaire extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	ImageEmplacement[] cases;
    GestTransfertImage picHandler;

    /**
     * Constructeur par défaut
     *
     */
    public ContInventaire() {
        super("Inventaire");
        picHandler = new GestTransfertImage();
        cases = new ImageEmplacement[12];
        JPanel mugshots = new JPanel(new GridLayout(3, 4));
        for(int i=0; i<cases.length; i++)
        	cases[i] = new ImageEmplacement(null);
        cases[0] = new ImageEmplacement(ObjetInventaireLoader.loadImageObjet("armures","ashigarudou"));
        cases[1] = new ImageEmplacement(ObjetInventaireLoader.loadImageObjet("armes","katana_bushi"));
        cases[2] = new ImageEmplacement(ObjetInventaireLoader.loadImageObjet("armes","katana_ninja"));
        cases[5] = new ImageEmplacement(ObjetInventaireLoader.loadImageObjet("armes","kusarigama"));
        cases[6] = new ImageEmplacement(ObjetInventaireLoader.loadImageObjet("casques","kabuto"));
        cases[11] = new ImageEmplacement(ObjetInventaireLoader.loadImageObjet("armes","yumi"));

        for(int i=0; i<cases.length; i++){
        	cases[i].setTransferHandler(picHandler);
        	mugshots.add(cases[i]);
        }
        setPreferredSize(new Dimension(450, 630));
        add(mugshots, BorderLayout.CENTER);
    }

    /**
     * Ajoute un objet en position {x,y} de l'inventaire
     * @param x
     * @param y
     * @return
     */
    public boolean addObjet(ObjetConcret objet, int x, int y){
    	return addObjet(objet, x+y*4);
    }
    
    /**
     * Ajoute un objet dans l'emplacement 'w' de l'inventaire
     * @param objet
     * @param w
     * @return
     */
    private boolean addObjet(ObjetConcret objet, int w){
    	if(cases[w]==null){
    		cases[w] = new ImageEmplacement(ObjetInventaireLoader.loadImageObjet("armes",objet.getNom()));
    		return true;
    	}
    	return false;
    }

    public static void main(String[] args) {
		JFrame frame = new JFrame("Test Inventaire");
		ContInventaire vueInventaire = new ContInventaire();
		frame.add(vueInventaire);
		frame.pack();
		frame.setVisible(true);
    }

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}

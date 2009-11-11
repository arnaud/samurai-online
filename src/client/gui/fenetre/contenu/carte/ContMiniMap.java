package client.gui.fenetre.contenu.carte;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.gui.fenetre.contenu.AbsContent;
import client.gui.fenetre.contenu.general.Bouton;

public class ContMiniMap extends AbsContent {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel controls = null;
	private Bouton zoomIn = null;
	private Bouton zoomOut = null;
	private static FollowPicture image = null;
	private JPanel panInfos = null;
	private static JLabel position = null;
	private static JLabel nivZoom = null;
	private JPanel panZoom = null;

	public ContMiniMap() {
		super("Mini-carte");
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		image = new FollowPicture("data/images/kyushu_map.png");
		this.add(getPanInfos(), BorderLayout.NORTH);
		this.add(image, BorderLayout.CENTER);
		this.add(getControls(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes controls	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getControls() {
		if (controls == null) {
			controls = new JPanel(new BorderLayout());
			controls.add(getZoomOut(), BorderLayout.WEST);
			controls.add(getPanZoom(), BorderLayout.CENTER);
			controls.add(getZoomIn(), BorderLayout.EAST);
		}
		return controls;
	}

	/**
	 * This method initializes zoomIn	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getZoomIn() {
		if (zoomIn == null) {
			zoomIn = new Bouton("+");
			zoomIn.setFont(new Font(null,Font.BOLD, 12));
			zoomIn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					boolean isLimit = image.zoomIn();
					if(isLimit)
						zoomIn.setFont(new Font(null,Font.PLAIN, 12));
					else
						zoomIn.setFont(new Font(null,Font.BOLD, 12));
					updateNivZoom();
				}
			});
		}
		return zoomIn;
	}

	/**
	 * This method initializes zoomOut	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getZoomOut() {
		if (zoomOut == null) {
			zoomOut = new Bouton("-");
			zoomOut.setFont(new Font(null,Font.BOLD, 12));
			zoomOut.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					boolean isLimit = image.zoomOut();
					if(isLimit)
						zoomOut.setFont(new Font(null,Font.PLAIN, 12));
					else
						zoomOut.setFont(new Font(null,Font.BOLD, 12));
					updateNivZoom();
				}
			});
		}
		return zoomOut;
	}
	
	/**
	 * This method initializes panInfos	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanInfos() {
		if (panInfos == null) {
			panInfos = new JPanel();
			panInfos.setLayout(new BorderLayout());
			panInfos.add(getPosition(), BorderLayout.CENTER);
		}
		return panInfos;
	}
	
	/**
	 * This method initializes panZoom	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanZoom() {
		if (panZoom == null) {
			panZoom = new JPanel();
			panZoom.add(getNivZoom());
		}
		return panZoom;
	}

	/**
	 * This method initializes nivZoom	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getNivZoom() {
		if (nivZoom == null) {
			nivZoom = new JLabel();
			nivZoom.setForeground(DEFAULT_FG_COLOR);
			updateNivZoom();
		}
		return nivZoom;
	}
	

	private void updateNivZoom(){
		nivZoom.setText("Zoom: "+(image.getNivZoom()-1)+"/"+(image.getZoomMax()-1));
	}

	/**
	 * This method initializes position	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getPosition() {
		if (position == null) {
			position = new JLabel();
			position.setForeground(DEFAULT_FG_COLOR);
			position.setText("[x, y, z]");
		}
		return position;
	}
	
	public static void updatePosition(float posx, float posy, float posz){
		position.setText("["+posx+", "+posy+", "+posz+"]");
		image.updatePosition(posx, posy);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new BorderLayout());
		f.add(new ContMiniMap(), BorderLayout.CENTER);
		f.setVisible(true);
	}

	public void refresh() {
		controls.repaint();
		position.repaint();
	}
}

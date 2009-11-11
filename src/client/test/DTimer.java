package client.test;

/****
 * Classe DTimer : 
          gere un compteur de temps ainsi que l'affichage, utilisation du composant javax.swing.Timer 
 *        qui genere un evenement tout les x temps d'intervalle ;
 *        solution qui est plus pratique qu'une classe qui herite de Thread ou implemente l'interface 
 *        Runnable car ces deux dernieres solutions imposent un traitement graphique hors du processus 
 *        d'evenement "the event-dispatching thread" ce qui est normalement deconseille pour ne par dire interdi
 *
 ****/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class DTimer extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/****
	    * Attributs :
	    *
	    ****/
	protected JLabel viewTime;  // composant permettant l'affichage du temps ecoule
	protected int timeCount;	// variable permettant de memoriser le temps ecoule
	protected Timer timer;		// objet javax.swing.Timer

	/****
	    * Constructeur :
	    *
	    ****/
	// (par defaut) initialise le compteur a 0 et le delay a 1 seconde
	public DTimer ()
	{	this (0, 1000);
	}
	
	// construit un timer avec le temps initiale donnee et le delay a 1 seconde
	public DTimer (int initialTime)
	{	this (initialTime, 1000);
	}
	
	// construit un timer avec le temps et le delay donne
	public DTimer (int initialTime, int delay)
	{	super ("Demo chrono sans thread");
		
		this.timeCount = initialTime;
		this.viewTime = new JLabel (""+this.timeCount);
		this.timer = new Timer (delay, this);
		this.initGraphic ();
	}
	
	/****
	    * Methodes :
	    *
	    ****/
	// initialise la partie graphique de la demo
	protected void initGraphic ()
	{	JButton bouton = new JButton ("start/stop");
		bouton.setActionCommand ("Bouton");
		bouton.addActionListener (this);
		this.viewTime.setHorizontalAlignment (JLabel.CENTER);
		this.getContentPane ().add (bouton, BorderLayout.SOUTH);
		this.getContentPane ().add (this.viewTime, BorderLayout.CENTER);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.setLocation (250, 250);
		this.pack ();
		this.setVisible (true);
	}
	
	// lance le compteur de temps 
	public void startDTimer ()
	{	this.timer.start ();
	}
	
	// stop le compteur de temps 
	public void stopDTimer ()
	{	this.timer.stop ();
	}
	
	// permet de recuperer le temps deja ecoule
	public int getTime ()
	{	return ( this.timeCount );
	}
	
	// permet de connaitre l'etat d'activite du timer (lance ou non)
	public boolean isRunning ()
	{	return ( this.timer.isRunning () );
	}
	
	/****
	    * Methode de l'interface ActionListener : 
	    *        necessaire pour l'object javax.swing.Timer
	    *        methode appelle a intervalle de temps regulier par le timer
	    *        (utilise egalement par le bouton pour stopper et lancer le timer
	    *         la difference ce fait via l'instruction getActionCommand)
	    *
	    ****/
	public void actionPerformed (ActionEvent e)
	{	// Cas d'un evenement genere par le bouton
		if ( "Bouton".equals (e.getActionCommand ()) )
		{	// le timer est en court d'execution donc on l'arrete
			if ( this.isRunning () )
			{	this.stopDTimer ();
			}
			else // le timer est arrete donc on le lance ou relance
			{	this.startDTimer ();
			}
		}
		else // Cas d'un evenement genere par le composant javax.swing.Timer
		{	this.timeCount++;
			this.viewTime.setText (""+this.timeCount);
		}
	}

	public static void main (String argv [])
	{	new DTimer ();
	}
}
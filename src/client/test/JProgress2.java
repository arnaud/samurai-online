package client.test;

/*****> Class : JProgress2
*
**> Auteur : Johann Heymes
*
**> Creation : 04 juin 2002
*
*
**> Licence :
*		Ce programme est un logiciel gratuit ,  il peut être modifié et / ou
*	distribué en  accord avec  les termes de la licence  'GNU General Public
*	Licence'  tel qu'elle  est publiée  par la  'Free Software Foundation  .
*	(version 2 ou précédante).
*		Ce programme est distribué  dans l'espoir  qu'il pourra être utile ,
*	mais  SANS AUCUNE GARANTIE . Voir la  'GNU General Public License'  pour 
*	plus de détail.
*		Vous devriez avoir reçu une copie de la 'GNU General Public License'
*	avec la distribution  de ce programme,  si non,  vous pouvez écrire à la
*	'Free Software Foudation,  Inc.,  59 Temple Place -  Suite 330,  Boston,
*	MA 02111-1307, USA'.
*	ou a http://www.gnu.org/licenses/gpl.html
*
**> Description :
*		Cette classe montre la bonne maniere d'utiliser une JProgressBar
*	et explique comment.
*
***/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


public class JProgress2 extends JFrame implements ActionListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	
private JProgressBar progress;
private boolean isStarted;
private JButton bouton;
private int value;

public JProgress2 ()
{
	super ("JProgressBar");

	this.isStarted = false;
	this.value = 0;
	this.progress = new JProgressBar (0, 100);
	this.progress.setStringPainted (true);
	this.getContentPane ().add (this.progress);

	bouton = new JButton ("start");
	bouton.addActionListener (this);
	this.getContentPane ().add (bouton, BorderLayout.NORTH);

	this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	this.setLocation (200, 200);
	this.pack ();
	this.setVisible (true);
}


// methode de ActionListener appelle suite au clic sur le bouton
public void actionPerformed (ActionEvent e)
{
	if ( this.isStarted ) // effectue un reset
	{	this.progress.setValue (0);
		this.bouton.setText ("start");
		this.value = 0;
	}
	else // effectue le start
	{	this.bouton.setText ("reset");
		this.bouton.setEnabled (false);
		// ici on fait l'appel a la methode de lanceLongTraitement
		// rappel : nous sommes dans le processus d'evenement
		this.lanceLongTraitement ();
	}
	this.isStarted = ! this.isStarted;
}



// cree un nouveau Thread et appelle la methode start
// Cf une documentation sur les Thread si vous ne comprennez pas le
// mecanisme ....
public void lanceLongTraitement ()
{
	Thread t = new Thread (this);
	t.start ();
	// maintenant nous rendons la main au processus d'evenement
}

// methode de l'interface Runnable
// lance un nouveau thread qui va executer le code de la methode longTraitement
public void run ()
{	this.longTraitement ();
}


public void longTraitement ()
{	// execute la succession d'operation ... 
	// on est plus dans le processus d'evenement mais dans le nouveau Thread
	for (int i = 0; i < 100; i++)
	{	this.uneOperation ();
		// maintenant on appelle la methode pour mettre a jour la barre
		// de progression
		this.majProgress (); 
	}
}



// methode qui effectue une pause pour simuler une operation
private synchronized void uneOperation ()
{	try
	{	this.wait (100);
	}
	catch (InterruptedException e)
	{
	}
}



// methode qui met a jour la JProgressBar par le processus d'evenement
// Pourquoi obliger l'execution de cette methode par le processus d'evenement ?
// -> Cf : la docs du tutoriel de Sun section : "Threads and Swing"
// http://java.sun.com/docs/books/tutorial/uiswing/mini/threads.html
public void majProgress ()
{	if ( SwingUtilities.isEventDispatchThread () )
	{	progress.setValue (++value);
	}
	else
	{	Runnable callMAJ = new Runnable ()
		{	public void run ()
			{	majProgress ();
            }
        };
        SwingUtilities.invokeLater (callMAJ);
	}
}


public static void main (String argv [])
{	new JProgress2 ();
}

}
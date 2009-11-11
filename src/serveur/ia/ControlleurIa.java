/*
 * Created on 29 mai 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package serveur.ia;

import java.util.Vector;

/**
 * @author jacquema
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ControlleurIa extends Thread {
	public Vector ias;
	
	public ControlleurIa(Vector ias) {
		this.ias = ias;
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int i=0; i<ias.size(); i++) {
				Ia ia = (Ia)ias.get(i);
				ia.act();
			}
		}
	}
}

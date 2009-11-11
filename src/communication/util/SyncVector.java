/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package communication.util;

import java.util.Vector;

/**
 * @author jacquema
 */
public class SyncVector extends Vector {
	
	private static final long serialVersionUID = 1L;
	
	Vector vector = new Vector();
	
	public synchronized boolean add(Object o) {
		boolean res = super.add(o);
		//System.out.println("ajout d'un packet dans ceux à envoyer");
		notify();
		return res;
	}
	
	public synchronized boolean isEmpty() {
		boolean res = super.isEmpty();
		if(res) {
			//System.out.println("packets à envoyer vide? " + res);
			try {
				wait();
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		return super.isEmpty();
	}
	
	public synchronized Object get(int i) {
		boolean res = super.isEmpty();
		if(res) {
			try {
				wait();
			} catch(InterruptedException ex) {}
		}
		return super.get(i);
	}
}

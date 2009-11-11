/*
 * Created on 14 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import java.util.Vector;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Thread_test extends Thread {
	float x;
	float y;
	float z;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		System.out.println ("Hello!");
		try {
			// TODO Auto-generated method stub
			Thread.currentThread().wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true);
		//super.run();
	}
	
	public static void main (String[] args) {
		Vector tests = new Vector();
		for (int i = 0; i<1000; i++) {
			Thread_test test = new Thread_test();
			test.start ();
			tests.add (test);

		}
		while (true);
	}
}

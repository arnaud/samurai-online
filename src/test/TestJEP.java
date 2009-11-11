/*
 * Created on 26 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import org.nfunk.jep.JEP;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestJEP {
	
	public static void main(String[] args) {
		JEP myParser = new JEP ();
		myParser.setImplicitMul(true);
		myParser.addStandardConstants();
		myParser.addStandardFunctions();
		
		String exp = "force+10*dexterité";
		
		myParser.addVariable("force", 10);
		myParser.addVariable("dexterité", 5);
		
		myParser.parseExpression(exp);
		System.out.println (myParser.getValue());
	}
	
}

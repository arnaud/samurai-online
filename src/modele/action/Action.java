/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.action;

import java.io.Serializable;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Action implements Serializable {

	/**
	 * 
	 * @uml.property name="competence"
	 * @uml.associationEnd javaType="Competence" inverse="action:modele.action.Competence"
	 * multiplicity="(1 1)"
	 */
	protected Competence competence;

	public Action () {
	}
	
	/**
	 *  
	 * @uml.property name="competence"
	 * 
	 */
	public Competence getCompetence() {
		return competence;
	}

	/**
	 *  
	 * @uml.property name="competence"
	 * 
	 */
	public void setCompetence(Competence competence) {
		this.competence = competence;
	}
	
	public String getNom(){
		if(competence==null)
			return "?";
		else
			return competence.getNom();
	}
}

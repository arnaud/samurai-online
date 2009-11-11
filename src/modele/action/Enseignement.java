/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.action;

/**
 * @author canonlo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Enseignement {

	/**
	 * 
	 * @uml.property name="competence"
	 * @uml.associationEnd javaType="Competence" inverse="enseignement:modele.action.Competence"
	 * multiplicity="(1 1)"
	 */
	private Competence competence;

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

}

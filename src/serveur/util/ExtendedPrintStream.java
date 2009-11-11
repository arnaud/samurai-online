package serveur.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Cette classe hérite de PrintStream et est utilisée pour définir les flux de sortie (fichiers) des variables statiques de la classe Sys.
 * Elle permet notamment une disposition d'enregistrement d'informations grâce à une entête définie dans la méthode getEntete().
 * @author kouyio
 *
 */
public class ExtendedPrintStream extends PrintStream {

	public ExtendedPrintStream(PrintStream ps){
		super(ps);
	}

	public ExtendedPrintStream(String fileName) throws FileNotFoundException {
		super(new FileOutputStream(new File(fileName)), true);
	}
	
	public void print(String msg){
		synchronized (this) {
		    String[] message = msg.split("\n");
		    for(int i=0; i<message.length; i++){
		    	if(i!=0){
		    		super.print("\n");
					super.print(getEntete());
		    	}
		    	super.print(message[i]);
			}
		}
	}
	
	public void println(String msg){
		synchronized (this) {
			super.print(getEntete());
		    print(msg);
	    	super.print("\n");
		}
	}
	
	public void print(boolean bool){
		synchronized (this) {
			print(""+bool);
		}
	}
	
	public void println(boolean bool){
		synchronized (this) {
			println(""+bool);
		}
	}
	
	public void print(int val){
		synchronized (this) {
			print(""+val);
		}
	}
	
	public void println(int val){
		synchronized (this) {
			println(""+val);
		}
	}
	
	public void print(long val){
		synchronized (this) {
			print(""+val);
		}
	}
	
	public void println(long val){
		synchronized (this) {
			println(""+val);
		}
	}
	
	public void print(float val){
		synchronized (this) {
			print(""+val);
		}
	}
	
	public void println(float val){
		synchronized (this) {
			println(""+val);
		}
	}
	
	public void print(Object o){
		synchronized (this) {
			print(o.toString());
		}
	}
	
	public void println(Object o){
		synchronized (this) {
			println(o.toString());
		}
	}
	
	/**
	 * @return Retourne la date et heure actuelle d'enregistrement de l'information.
	 */
	private String getEntete(){ 
    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.setTime(new Date(System.currentTimeMillis()));
    	
    	Integer jour = new Integer(calendar.get(Calendar.DAY_OF_MONTH));
    	Integer mois = new Integer(calendar.get(Calendar.MONTH)+1); // Mois bizzarement bizarre
    	Integer annee = new Integer(calendar.get(Calendar.YEAR));
    	Integer heure = new Integer(calendar.get(Calendar.HOUR_OF_DAY));
    	Integer minute = new Integer(calendar.get(Calendar.MINUTE));
    	Integer seconde = new Integer(calendar.get(Calendar.SECOND));
    	
		Integer[] args = {annee, mois, jour, heure, minute, seconde};
		
		String ret = String.format("%4d-%2d-%2d|%2d:%2d:%2d", args);
		ret = ret.replaceAll(" ", "0");
		ret += "|";
		
    	return ret;
 	}
	
}
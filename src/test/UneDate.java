/*
 * Created on 16 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * @author toto
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
class UneDate implements Serializable {
    static int ii = 2;
    int jour, mois, annee;
    
    
    public UneDate(int j, int m, int a) {
       jour = j; mois = m; annee = a;
    }
	/* (non-Javadoc)
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeObject(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeInt(jour);
		out.writeInt(mois);
		out.writeInt(annee);
	}
	/* (non-Javadoc)
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	public void readObject(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
        jour = in.readInt();
        mois = in.readInt();
        annee = in.readInt();
	}
 }
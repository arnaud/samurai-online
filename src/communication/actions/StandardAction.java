package communication.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Vector;

import modele.action.Interaction;
import modele.objet.Arme;
import modele.objet.Batiment;
import modele.objet.ObjetConcret;
import modele.objet.ObjetDecoratif;
import modele.objet.Outil;
import modele.personne.Personnage;
import modele.quete.ObjectifQuete;
import modele.quete.Quete;
import serveur.Serveur;
import client.Client;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import communication.CommClient;
import communication.CommServeur;
import communication.util.SetMove;

public class StandardAction implements NetworkAction {
	public final static byte PING = 1;
	public final static byte BONJOUR = 2;
	public final static byte INITME = 3;
	public final static byte INTERACTION = 4;
	public final static byte SAY = 5;
	public final static byte SAYCLAN = 6;
	public final static byte SAYTEAM = 7;
	public final static byte MOVE = 8;
	public final static byte ORIENTER = 9;
	public final static byte MOVEINTER = 10;
	public final static byte INITPERSO = 11;
	public final static byte INITINTER = 12;
	public final static byte AUTREPERSO = 13;
	public final static byte SENDQUEST = 14;
	public final static byte QUESTOBJECTIVE = 15;
	public final static byte AUTREDECO = 16;
	public final static byte INITFINISHED = 17;
	public final static byte ASKQUEST = 18;
	public final static byte AUTREBATI = 19;
	public final static byte AUTREARME = 20;
	public final static byte AUTREOUTIL = 21;
	public final static byte AUTREFORET = 22;
	
	protected byte type;
	protected byte priorite=0;
	protected int key=-1;
	protected String address;
	protected int port;
	protected byte[] objet = null;
	
	public StandardAction() {
		type=0;
	}
	
	public StandardAction(byte type) {
		this.type=type;
	}
	
	public StandardAction(byte type, byte priorite) {
		this(type);
		this.priorite=priorite;
	}
	
	public StandardAction(byte type, byte priorite, int key) {
		this(type,priorite);
		this.key=key;
	}
	
	public StandardAction(byte type, byte priorite, int key, String address, int port) {
		this(type,priorite,key);
		this.address=address;
		this.port=port;
	}
	
	public StandardAction(byte type, byte priorite, int key, String address, int port, byte[] objet) {
		this(type,priorite,key,address,port);
		this.objet=objet;
	}
	
	public byte getType() {
		return type;
	}
	
	public void setType(byte type) {
		this.type=type;
	}
	
	public byte getPriorite() {
		return priorite;
	}
	
	public int getKey() {
		return key;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public byte[] getObjet() {
		return objet;
	}
	
	public void clientAction(Client client, CommClient commClient) {
		// TODO Auto-generated method stub

	}

	public void serveurAction(Serveur serveur, CommServeur commServeur) {
		// TODO Auto-generated method stub

	}
	
	public static byte[] getBytes(Class t, Object objet) {

		try {
			ByteArrayOutputStream fluxSortie = new ByteArrayOutputStream() ;
			ObjectOutput os = new ObjectOutputStream(fluxSortie);
    	
			if(t.getName().compareTo("java.lang.String")==0) {
				os.writeObject((String)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("java.lang.Integer")==0) {
				os.writeObject((Integer)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("java.lang.Long")==0) {
				os.writeObject((Long)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("com.jme.math.Vector3f")==0) {
				os.writeObject((Vector3f)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("com.jme.math.Quaternion")==0) {
				os.writeObject((Quaternion)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("communication.util.SetMove")==0) {
				os.writeObject((SetMove)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("modele.personne.Personnage")==0) {
				os.writeObject((Personnage)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("modele.objet.ObjetDecoratif")==0) {
				os.writeObject((ObjetDecoratif)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("modele.action.Interaction")==0) {
				os.writeObject((Interaction)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("modele.quete.Quete")==0) {
				os.writeObject((Quete)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("modele.quete.ObjectifQuete")==0) {
				os.writeObject((ObjectifQuete)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("modele.objet.Batiment")==0) {
				os.writeObject((Batiment)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("modele.objet.Arme")==0) {
				os.writeObject((Arme)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("modele.objet.Outil")==0) {
				os.writeObject((Outil)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else if(t.getName().compareTo("java.util.Vector")==0) {
				os.writeObject((Vector)(objet));
				os.flush();
				os.close();
				return fluxSortie.toByteArray();
			} else {
				os.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
	}
	
	public static Object getObject(Class t, byte[] flux) {
		try {
			ByteArrayInputStream  fluxEntree = new ByteArrayInputStream(flux);
			ObjectInputStream ois = new ObjectInputStream(fluxEntree);
			
			if(t.getName().compareTo("java.lang.String")==0) {
				String obj = (String)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("java.lang.Integer")==0) {
				Integer obj = (Integer)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("java.lang.Long")==0) {
				Long obj = (Long)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("com.jme.math.Vector3f")==0) {
				Vector3f obj = (Vector3f)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("com.jme.math.Quaternion")==0) {
				Quaternion obj = (Quaternion)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("communication.util.SetMove")==0) {
				SetMove obj = (SetMove)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("modele.personne.Personnage")==0) {
				Personnage obj = (Personnage)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("modele.objet.ObjetDecoratif")==0) {
				ObjetDecoratif obj = (ObjetDecoratif)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("modele.action.Interaction")==0) {
				Interaction obj = (Interaction)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("modele.quete.Quete")==0) {
				Quete obj = (Quete)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("modele.quete.ObjectifQuete")==0) {
				ObjectifQuete obj = (ObjectifQuete)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("modele.objet.Batiment")==0) {
				Batiment obj = (Batiment)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("modele.objet.Arme")==0) {
				ObjetConcret obj = (Arme)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("modele.objet.Outil")==0) {
				ObjetConcret obj = (Outil)ois.readObject();
				ois.close();
				return obj;
			} else if(t.getName().compareTo("java.util.Vector")==0) {
				Vector obj = (Vector)ois.readObject();
				ois.close();
				return obj;
			} else {
				ois.close();
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();	
		}
		return null;
	}

}

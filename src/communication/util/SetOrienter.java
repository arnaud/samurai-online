package communication.util;

import java.io.Serializable;

import com.jme.math.Quaternion;

public class SetOrienter implements Serializable{
	private int id;
	private Quaternion objet;
	
	public SetOrienter(int id, Quaternion objet) {
		this.id=id;
		this.objet=objet;
	}
	
	public int getId() {
		return id;
	}
	
	public Quaternion getQuaternion() {
		return objet;
	}
}

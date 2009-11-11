package communication.util;

import java.io.Serializable;

import com.jme.math.Vector3f;

public class SetMove implements Serializable{
	private int id;
	private Vector3f objet;
	
	public SetMove(int id, Vector3f objet) {
		this.id=id;
		this.objet=objet;
	}
	
	public int getId() {
		return id;
	}
	
	public Vector3f getVector3f() {
		return objet;
	}
}

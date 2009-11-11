package test;

/*package test;

import com.jme.bounding.BoundingSphere;
import com.jme.intersection.BoundingCollisionResults;
import com.jme.intersection.CollisionResults;
import com.jme.intersection.TriangleCollisionResults;
import com.jme.math.Vector3f;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import com.jme.scene.shape.Sphere;

public class TestDeplacement {
	public static void main(String[] args) {
		Node rootNode = new Node("root");
		Node n1 = new Node ("Sphere1");
		Node n2 = new Node ("Sphere2");
		
    	Sphere sphere1 = new Sphere ("boule1", 100, 100, 50f);
    	sphere1.setModelBound(new BoundingSphere());
    	sphere1.updateModelBound();
    	sphere1.setLocalTranslation(new Vector3f (50,0,0));
    	
    	Sphere sphere2 = new Sphere ("boule2", 100, 100, 50f);
    	sphere2.setModelBound(new BoundingSphere());
    	sphere2.updateModelBound();
    	sphere2.setLocalTranslation(new Vector3f (50,0,50));
    	
    	n1.attachChild(sphere1);
    	n2.attachChild(sphere2);
    	rootNode.attachChild(n1);
    	rootNode.attachChild(n2);
    	
    	System.out.println (sphere1.radius);
    	System.out.println (sphere2);
    	System.out.println (sphere1.getLocalTranslation());
    	System.out.println (sphere2.getLocalTranslation());
		
    	BoundingCollisionResults results = new BoundingCollisionResults() {
			public void processCollisions() {
				if (getNumber() > 0) {
					System.out.println("Collision: YES");
				} else {
					System.out.println("Collision: NO");
				}
			}
		};
		
		results.clear();
    	n1.calculateCollisions(rootNode, results);
    	if (n1.hasCollision(rootNode, false)) {
    		System.out.println("Une collision");
		}
	}
}*/
import com.jme.app.SimpleGame;
import com.jme.bounding.OrientedBoundingBox;
import com.jme.curve.BezierCurve;
import com.jme.curve.Curve;
import com.jme.curve.CurveController;
import com.jme.image.Texture;
import com.jme.intersection.BoundingCollisionResults;
import com.jme.intersection.CollisionResults;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Text;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;

/**
 * <code>TestCollision</code>
 * 
 * @author Mark Powell
 * @version $Id: TestDeplacement.java,v 1.1 2006/04/16 08:40:02 lcc Exp $
 */
public class TestDeplacement extends SimpleGame {

	private TriMesh t;

	private TriMesh t2;

	private Text text;

	private Node scene;

	private Quaternion rotQuat = new Quaternion();

	private float angle = 0;

	private Vector3f axis = new Vector3f(1, 0, 0);

	private float tInc = -40.0f;

	private float t2Inc = -10.0f;
	
	private CollisionResults results;
	
	private Node n1, n2;
	
	private CurveController cont;

	long old = System.currentTimeMillis();
	long nv = System.currentTimeMillis();
	long diff = 0;
	int count=0;
	
	/**
	 * Entry point for the test,
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TestDeplacement app = new TestDeplacement();
		app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
		app.start();
//		app.simpleInitGame();
//		app.simpleUpdate();
	}

	protected void simpleUpdate () {
//		while (true) {
			simpleUpdateN(0.01f);
			cont.update (0.01f);
//			scene.updateGeometricState(0.01f, true);
//		}
	}
	
	protected void simpleUpdateN(float time) {
		if (time < 1) {
			angle = angle + (time * 1);
			if (angle > 360) {
				angle = 0;
			}
		}

		rotQuat.fromAngleAxis(angle, axis);

		t.setLocalRotation(rotQuat);

		t.getLocalTranslation().y += tInc * time;
		//t2.getLocalTranslation().x += t2Inc * time;

		if (t.getLocalTranslation().y > 40) {
			t.getLocalTranslation().y = 40;
			tInc *= -1;
		} else if (t.getLocalTranslation().y < -40) {
			t.getLocalTranslation().y = -40;
			tInc *= -1;
		}

//		if (t2.getLocalTranslation().x > 40) {
//			t2.getLocalTranslation().x = 40;
//			t2Inc *= -1;
//		} else if (t2.getLocalTranslation().x < -40) {
//			t2.getLocalTranslation().x = -40;
//			t2Inc *= -1;
//		}
		
//		System.out.println(t.getLocalTranslation());
//		System.out.println(t2.getLocalTranslation());
		
		results.clear();
		n1.calculateCollisions(scene, results);
		
		if(n1.hasCollision(scene, false)) {
			System.out.println("hasCollision also reports true");
		}
	}

	/**
	 * builds the trimesh.
	 * 
	 * @see com.jme.app.SimpleGame#initGame()
	 */
	protected void simpleInitGame() {
		results = new BoundingCollisionResults() {
			public void processCollisions() {
				if (getNumber() > 0) {
					System.out.println("Collision: YES");
				} else {
					//System.out.println("Collision: NO");
				}
			}
		};
		
		display.setTitle("Collision Detection");
		cam.setLocation(new Vector3f(0.0f, 0.0f, 75.0f));
		cam.update();

		text = new Text("Text Label", "Collision: No");
		text.setLocalTranslation(new Vector3f(1, 60, 0));
		fpsNode.attachChild(text);

		scene = new Node("3D Scene Root");

		Vector3f max = new Vector3f(5, 5, 5);
		Vector3f min = new Vector3f(-5, -5, -5);

		n1 = new Node("Node 1");
		n2 = new Node("Node 2");
		
		t = new Box("Box 1", min, max);
		t.setModelBound(new OrientedBoundingBox());
		t.updateModelBound();
		t.setLocalTranslation(new Vector3f(0, 30, 0));

		t2 = new Box("Box 2", min, max);
		t2.setModelBound(new OrientedBoundingBox());
		t2.updateModelBound();
		t2.setLocalTranslation(new Vector3f(30, 0, 0));
		n1.attachChild(t);
		n2.attachChild(t2);
		scene.attachChild(n1);
		scene.attachChild(n2);
		
		Vector3f[] v = {new Vector3f(-50,0,0), new Vector3f(0,20,0), new Vector3f(50,0,0)};
		
		Curve curve = new BezierCurve ("courbe", v);
		
		cont = new CurveController (curve, t2, 0.1f, 50f);
		cont.setSpeed(0.1f);

		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled(true);
		ts.setTexture(TextureManager.loadTexture(
				TestCollision.class.getClassLoader().getResource(
						"jmetest/data/images/Monkey.jpg"), Texture.MM_LINEAR,
				Texture.FM_LINEAR));

		scene.setRenderState(ts);
		rootNode.attachChild(scene);
	}
}
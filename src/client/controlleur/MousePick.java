/*
 * Created on 24 mai 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.controlleur;

import client.modele.Monde;

import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.intersection.BoundingPickResults;
import com.jme.intersection.PickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

public class MousePick extends MouseInputAction {
    private Node scene;
    private float shotTime = 0;
    private Monde monde;

    public MousePick(Node scene, Monde monde) {
        this.scene = scene;
        this.monde = monde;
    }

    public void performAction(InputActionEvent evt) {
        shotTime += evt.getTime();
        if (MouseInput.get().isButtonDown(0) && shotTime > 0.01f) {
        	shotTime = 0;
        	
            Vector2f screenPos = new Vector2f(MouseInput.get().getXAbsolute(), MouseInput.get().getYAbsolute());
            Vector3f startPoint = DisplaySystem.getDisplaySystem().getWorldCoordinates(screenPos, 0);
            Vector3f endPoint = DisplaySystem.getDisplaySystem().getWorldCoordinates(screenPos, 1);
            
            Ray ray = new Ray(startPoint, endPoint.subtract(startPoint));
            
            PickResults results = new BoundingPickResults();
            results.setCheckDistance(true);
            scene.findPick(ray,results);
            
            for(int i = 0; i < results.getNumber(); i++)
            	monde.setObjetSelec (results.getPickData(i).getTargetMesh());
            
            results.clear();
        }
    }
}

/*
 * Created on 3 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package modele.monde;

import com.jme.math.Vector3f;
import com.jmex.terrain.TerrainPage;

public class Carte extends TerrainPage {
	
	private static final long serialVersionUID = 1L;
	
	// Facteurs d'élargissement de la carte
	private final static int SCALE_FACTOR_XY = 15;
	private final static int SCALE_FACTOR_Z = 1;
    private final static Vector3f terrainScale = new Vector3f(SCALE_FACTOR_XY, SCALE_FACTOR_Z, SCALE_FACTOR_XY);
    
    protected DataRawHeightMap heightMap;
	
    /**
     * Constructeur par defaut, appelï¿½ indirectement depuis les modï¿½les spï¿½cifiques du client et du serveur.
     * @param heightMap
     */
    public Carte(DataRawHeightMap heightMap){
        super("Terrain", 17, heightMap.getSize(), terrainScale, heightMap.getHeightMap(), false);
        this.heightMap = heightMap;
		this.isCollidable = false;
    }
    
	public Carte() {
        this(new DataRawHeightMap());
	}
}

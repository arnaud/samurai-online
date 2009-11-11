package modele.monde;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import com.jme.math.Matrix3f;
import com.jme.util.LoggingSystem;
import com.jmex.terrain.util.AbstractHeightMap;

public class DataRawHeightMap extends AbstractHeightMap {
	
	private int[] heightData;
	private DataInputStream input;
	private final int size = 513;
    
    public DataRawHeightMap() {
    	input = new DataInputStream(DataRawHeightMap.class.getClassLoader().getResourceAsStream("data/terrain/kyushu_513.raw"));
    	load();
    	filtrage();
    }
    
    public boolean load(){

        //initialize the height data attributes
        heightData = new int[size*size];

        //attempt to connect to the supplied file.

        try {
            int bpd=2;
            
            if(heightData.length != input.available()/bpd) {
                LoggingSystem.getLogger().log(Level.WARNING, "Incorrect map size. Aborting raw load.");
            }
            //read in each byte from the raw file.
            for (int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    int index;
                    
                    index=(i*size) + j;
                    
                    heightData[index] = input.readUnsignedByte();
                }
            }
            input.close();
            
        } catch (FileNotFoundException e) {
            LoggingSystem.getLogger().log(
                Level.WARNING,
                "Heightmap file" + input.toString() + " not found.");
            return false;
        } catch (IOException e1) {
            LoggingSystem.getLogger().log(
                Level.WARNING,
                "Error reading data from " + input.toString());
            return false;
        }

        LoggingSystem.getLogger().log(
            Level.WARNING,
            "Successfully loaded " + input.toString());
        System.out.println("Height Map chargée !");
        return true;
    }
    
    private void filtrage(){
		int[][] newHeightMap = new int[size][size];
	    // Filtrage du terrain
	    for(int i=0; i<size; i++){
	    	for(int j=0; j<size; j++){
	    		if(0 < i && i < size-1 && 0 < j && j < size-1){
	    			// Création de la matrice de zone
	    			Matrix3f zone = new Matrix3f();
	    			zone.m00 = getScaledHeightAtPoint(i-1, j-1);
	    			zone.m01 = getScaledHeightAtPoint(i, j-1);
	    			zone.m02 = getScaledHeightAtPoint(i+1, j-1);
	    			zone.m10 = getScaledHeightAtPoint(i-1, j);
	    			zone.m11 = getScaledHeightAtPoint(i, j);
	    			zone.m12 = getScaledHeightAtPoint(i+1, j);
	    			zone.m20 = getScaledHeightAtPoint(i-1, j+1);
	    			zone.m21 = getScaledHeightAtPoint(i, j+1);
	    			zone.m22 = getScaledHeightAtPoint(i+1, j+1);
	    			// Calcul du filtre appliqué à la carte
	    			int pond1;
	    			int pond2;
	    			int pond3;
	    			pond1 = (int) Math.log(zone.m11/50)+1;
	    			pond2 = (int) Math.log(zone.m11/80)+1;
	    			pond3 = (int) Math.log(zone.m11/80)+1;
	    			if(zone.m11<=10.0) { // La rivière reste une rivière
	        			//pond1 = 5;
	        			//pond2 = 1;
	        			//pond3 = 1;
	        			
	    			} else if(zone.m11>=150) { // La montagne reste une montagne
	        			//pond1 = 3;
	        			//pond2 = 2;
	        			//pond3 = 1;
	    			} else { // Autre que rivière et montagne
	        			//pond1 = 5;
	        			//pond2 = 2;
	        			//pond3 = 2;
	    			}
	    			// 323
	    			// 212
	    			// 323
	    			int pondTotal = pond1 + 4*pond2 + 4*pond3;
					newHeightMap[i][j] = (int)(pond3*zone.m00+pond2*zone.m01+pond3*zone.m02+pond2*zone.m10+pond1*zone.m11+pond2*zone.m12+pond3*zone.m20+pond2*zone.m21+pond3*zone.m22)/pondTotal;
	    			// Application du filtre sur la heightMap
	    		}
	    	}
	    }
	    for(int i=0; i<size; i++)
	    	for(int j=0; j<size; j++)
	    		setHeightAtPoint((int)(newHeightMap[i][j]/2),i,j);

    }
    
    public int getSize() {
        return size;
    }
    
    public void setHeightAtPoint(int height, int x, int z) {
        heightData[x + (z*size)] = height;
    }

    public float getScaledHeightAtPoint(int x, int z) {
        return ((heightData[x + (z*size)]) * heightScale);
    }
    
    public int[] getHeightMap() {
        return heightData;
    }
    
    public int getTrueHeightAtPoint(int x, int z) {
        //System.out.println( heightData[x + (z*size)]);
        return heightData[x + (z*size)];
    }

}

package client.test;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.ImageIcon;

public class ImageUtil {

    // This method returns a buffered image with the contents of an image
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
    
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
    
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
    
        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.TRANSLUCENT;
    
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
    
        if (bimage == null) {
            // Create a buffered image using the default color model
            int  type = BufferedImage.TYPE_INT_ARGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
    
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
    
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
    
        return bimage;
    }
    
    //----- Partie filtrage -----
    
	public final static int FILTRAGE_NONE = 0;
	public final static int FILTRAGE_BLUR = 1;
	public final static int FILTRAGE_SHARPEN = 2;
	public final static int FILTRAGE_EMBOSS = 3;

	private final static float[] normal = new float[] {
        1, 1, 1,
        1, 1, 1,
        1, 1, 1};
	private final static float[] blur = new float[] {
        1f/9f, 1f/9f, 1f/9f,
        1f/9f, 1f/9f, 1f/9f,
        1f/9f, 1f/9f, 1f/9f};
	private final static float[] sharpen = new float[] {
        -1, -1, -1,
        -1, 9, -1,
        -1, -1, -1};
	private final static float[] emboss = new float[] {
        -2, 0, 0,
        0, 1, 0,
        0, 0, 2};
    
    /**
     * Applique un filtrage à l'image parmis une liste prédéfinie de filtres.
     * @param bimage Image bufferisée
     * @param typeFiltrage FILTRAGE_NONE, FILTRAGE_BLUR, FILTRAGE_SHARPEN ou FILTRAGE_EMBOSS
     * @return
     */
    public static BufferedImage filtrer(BufferedImage bimage, int typeFiltrage)
    {
    	float[] filtrage;
    	
    	switch(typeFiltrage){
	    	case(FILTRAGE_BLUR):
	    		filtrage = blur;
	    		break;
	    	case(FILTRAGE_SHARPEN):
	    		filtrage = sharpen;
    			break;
	    	case(FILTRAGE_EMBOSS):
	    		filtrage = emboss;
    			break;
	    	default:
	    		filtrage = normal;
	    		break;
    	}
    	
        Kernel kernel = new Kernel(3, 3, filtrage);
        BufferedImageOp op = new ConvolveOp(kernel);
        bimage = op.filter(bimage, null);
        
        return bimage;
    }
    
    /**
     * Applique un effet de transparence à l'image entière.
     * @param bimage Image bufferisée
     * @param degre Degré de transparence. Compris entre 0 et 255.
     * @return
     */
	public static BufferedImage appliquerTransparence(BufferedImage bimage, int degre) {
		int w = bimage.getWidth();
		int h = bimage.getHeight();
		int[] rgbs = new int[w*h];
		bimage.getRGB(0,0,w,h,rgbs,0,w);
		for(int i=0; i<rgbs.length; i++){
			int rgb = rgbs[i];
			int alpha = degre;//(rgb >>24 ) & 0xFF;
			int rouge = (rgb >>16 ) & 0xFF;
			int vert = (rgb >> 8 ) & 0xFF;
			int bleu = rgb & 0xFF;
			rgbs[i] = (alpha<<24)+(rouge<<16)+(vert<<8)+bleu;
		}
		bimage.setRGB(0,0,w,h,rgbs,0,w);
        return bimage;
	}
}

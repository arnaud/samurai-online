/*
 * Created on 18 avr. 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package communication;

/**
 * @author jacquema
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Paquet {
	public static final byte AUTH = 1;
	public static final byte PING = 2;
	public static final byte PONG = 3;
	public static final byte MOVE = 4;
	private String string;
	private byte[] bytes;
	
	public Paquet(String string) {
		this.string = string;
	}
	
	public Paquet(String string, byte[] bytes) {
		this(string);
		this.bytes=bytes;
	}
	
	public void setString(String string) {
		this.string = string;
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	public String getString() {
		return string;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public boolean hasOject() {
		//System.out.println("byte" + bytes);
		//return (bytes==null?false:true);
		if(bytes==null) {
			return false;
		} else {
			return true;
		}
	}
}

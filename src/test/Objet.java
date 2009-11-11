package test;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

class Objet implements Externalizable {
    float x;
    float y;
    float z;

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        x = in.readFloat ();
        y = in.readFloat ();
        z = in.readFloat ();
    }
    
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeFloat (x);
        out.writeFloat (y);
        out.writeFloat (z);
    }
    
    public static void main (String [] args) {
        try {
        ByteArrayOutputStream memoryOutputStream = new ByteArrayOutputStream();
        Objet x = new Objet();
        ObjectOutputStream serializer = new ObjectOutputStream(memoryOutputStream);
        serializer.writeObject(x);
        serializer.flush();
        System.out.println ("Taille : " + memoryOutputStream.size ());
        System.out.println (memoryOutputStream);
        } catch (java.io.IOException e) {}
        
    }
}


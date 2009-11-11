package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
class Flux {
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        String contenu = "Un contenu pour ce fichier !";
        ObjectOutputStream fluxSortieObjet = new ObjectOutputStream(new FileOutputStream(new File("fichier.txt")));
        
        
        fluxSortieObjet.writeObject(contenu);
        System.out.println ("Sauvegarde du fichier objet !");
        fluxSortieObjet.flush();
        fluxSortieObjet.close();
        
        String resultat;
        ObjectInputStream fluxEntreeObjet =new ObjectInputStream(new FileInputStream(new File("fichier.txt")));
        resultat = (String)fluxEntreeObjet.readObject();
        System.out.println ("Lecture du fichier objet !");
        System.out.println(resultat);
        fluxEntreeObjet.close();
    }
}
package org.broadcaststorm;

import java.io.*;
import java.util.List;

/**
 * Created by needspeed on 1/17/16.
 */
public class Serializer {
    public static void serialize(List<Song> songslist) {
        try {
            FileOutputStream fileOut = new FileOutputStream("/tmp/songs.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(songslist);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/songs.ser");
        }
        catch(IOException i) {
              i.printStackTrace();
        }
    }

    public static List<Song> deserialize() {
        List<Song> out = null;

       try {
           FileInputStream fileIn = new FileInputStream("/tmp/songs.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           out = (List<Song>) in.readObject();
           in.close();
           fileIn.close();
       }
       catch(IOException i) {
         i.printStackTrace();
         return null;
       }
       catch(ClassNotFoundException c) {
         System.out.println("Employee class not found");
         c.printStackTrace();
         return null;
       }


        return out;
    }
}

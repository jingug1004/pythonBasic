package test;

import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
 
public class SerializeTest{
    public static void main(String args[]) throws Exception{
        //This is the object we're going to serialize.
        String name = "bob";
 
        //We'll write the serialized data to a file "name.ser"
        FileOutputStream fos = new FileOutputStream("name.ser");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(name);
        os.close();
 
        //Read the serialized data back in from the file "name.ser"
        FileInputStream fis = new FileInputStream("name.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
 
        //Read the object from the data stream, and convert it back to a String
        String nameFromDisk = (String)ois.readObject();
 
        //Print the result.
        System.out.println(nameFromDisk);
        ois.close();
    }
}
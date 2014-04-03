package arthur.backend.whisperer;

import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import arthur.backend.media.*;
import arthur.backend.translator.ArthurType;

public class JsWhisperer implements java.io.Serializable {

  public static final String BLOBNAME = "whisperblob.ser";

  public static transient MediaMaster master = new MediaMaster();
  public static transient ArrayList<ArthurType> globals = new ArrayList<ArthurType>();

  public ArrayList<ArthurType> localGlobals;
  public ArrayList<String> localMediaFiles;

  public JsWhisperer() {
    localGlobals = globals;
    localMediaFiles = null;
  }

  public static void addVar(ArthurType var) {
    globals.add(var);
  }

  public static void addMedia(ArthurMedia media) {
    String id = "" + (master.finalMedia.size() + 1);
    master.addMedia(id, media);
  }

  public static void writeToBlob() {
    JsWhisperer whisp = new JsWhisperer();
    whisp.localMediaFiles = master.storeMedias();
    whisp.writeOut(BLOBNAME);
  }

  public static JsWhisperer restoreFromBlob() {
    JsWhisperer blob = null;
    try {
         FileInputStream fileIn = new FileInputStream(BLOBNAME);
         ObjectInputStream in = new ObjectInputStream(fileIn);
         blob = (JsWhisperer) in.readObject();
         in.close();
         fileIn.close();
    } catch(IOException e) {
         e.printStackTrace();
         return blob;
    } catch(ClassNotFoundException e) {
         e.printStackTrace();
         return blob;
    }
    return blob;
  }

  public void writeOut(String filename) {
    try {
       FileOutputStream fileOut = new FileOutputStream(filename);
       ObjectOutputStream out = new ObjectOutputStream(fileOut);
       out.writeObject(this);
       out.close();
       fileOut.close();
    } catch(IOException e) {
        e.printStackTrace();
    }
  }

}

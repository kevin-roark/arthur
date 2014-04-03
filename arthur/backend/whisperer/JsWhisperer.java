package arthur.backend.whisperer;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import arthur.backend.media.*;

public class JsWhisperer implements java.io.Serializable {

  public static final String BLOBNAME = "whisperblob.ser";

  public static transient MediaMaster master = new MediaMaster();
  public static HashMap<String, GlobalMedia> mediaMap = new HashMap<String, GlobalMedia>();
  public static HashMap<String, MediaContainer> globalMap = new HashMap<String, MediaContainer>();
  public static transient ArrayList<GlobalMedia> globals = new ArrayList<GlobalMedia>();

  public ArrayList<GlobalMedia> localGlobals;
  public ArrayList<String> localMediaFiles;

  public JsWhisperer() {
    localGlobals = globals;
    localMediaFiles = null;
  }

  public String toString() {
    String s = "";
    if (localMediaFiles != null) {
      s += "Media files:\n";
      for (String m : localMediaFiles) {
        s += "  " + m + "\n";
      }
    }

    s += "Globals:\n";
    for (GlobalMedia g : localGlobals) {
      s += "  " + g.toString() + "\n";
    }
    return s;
  }

  public static void addGlobal(String name, Object val) {
    ArthurMedia media = (ArthurMedia) val;
    GlobalMedia g = new GlobalMedia(name, media);
    globals.add(g);
    mediaMap.put(name, g);
    MediaContainer mc = globalMap.get(name);
    if (mc != null) {
      g.setMediaFile(mc);
    }
  }

  public static GlobalMedia getGlobalMedia(String varname) {
    GlobalMedia gm = mediaMap.get(varname);
    return gm;
  }

  public static void addMedia(ArthurMedia media, String name) {
    if (name != null) {
      MediaContainer c = master.addMedia(name, media);
      GlobalMedia gm = getGlobalMedia(name);
      if (gm != null) {
        gm.setMediaFile(c);
      }
      globalMap.put(name, c);
    } else {
      String id = "" + (master.finalMedia.size() + 1);
      MediaContainer c = master.addMedia(id, media);
      globalMap.put(name, c);
    }
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
         System.out.println("file not found");
         return new JsWhisperer();
    } catch(ClassNotFoundException e) {
         e.printStackTrace();
         return blob;
    }
    return blob;
  }

  public static void removeBlobFile() {
    try {
      Process p = Runtime.getRuntime().exec("rm -f " + BLOBNAME);
      p.waitFor();
    } catch (Exception e) {
      e.printStackTrace();
    }
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

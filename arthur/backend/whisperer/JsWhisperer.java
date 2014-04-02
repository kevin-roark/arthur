package arthur.backend.whisperer;

import java.util.ArrayList;

import arthur.backend.media.*;

public class JsWhisperer {

  public static MediaMaster master = new MediaMaster();
  public static int medias = 0;

  public static ArrayList<ArthurType> globals = new ArrayList<ArthurType>();

  public static add(ArthurMedia media) {
    medias++;
    String id = "" + medias;
    master.addMedia(id, media);
  }

}

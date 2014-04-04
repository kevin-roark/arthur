package arthur.backend.whisperer;

import java.util.ArrayList;

import arthur.backend.media.*;

public class MediaMaster {

  public ArrayList<MediaContainer> finalMedia;

  public MediaMaster() {
    this.finalMedia = new ArrayList<MediaContainer>();
  }

  public void addMedia(String name, ArthurMedia media) {
    MediaContainer c = new MediaContainer(name, media);
    this.finalMedia.add(c);
  }

  /* writes medias to file and returns list of filenames */
  public ArrayList<String> storeMedias() {
    ArrayList<String> names = new ArrayList<String>();
    for (MediaContainer mc : this.finalMedia) {
      mc.storeMedia();
      names.add(mc.filename);
    }
    return names;
  }

}

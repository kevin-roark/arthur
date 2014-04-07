package arthur.backend.whisperer;

import java.util.ArrayList;

import arthur.backend.media.*;

public class MediaMaster implements java.io.Serializable {

  public ArrayList<MediaContainer> finalMedia;

  public MediaMaster() {
    this.finalMedia = new ArrayList<MediaContainer>();
  }

  public MediaContainer addMedia(String name, ArthurMedia media) {
    MediaContainer c = new MediaContainer(name, media);
    this.finalMedia.add(c);
    return c;
  }

  /* writes medias to file and returns list of filenames */
  public MediaMaster storeMedias() {
    for (MediaContainer mc : this.finalMedia) {
      mc.storeMedia();
    }
    return this;
  }

}

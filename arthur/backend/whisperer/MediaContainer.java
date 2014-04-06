package arthur.backend.whisperer;

import arthur.backend.media.*;

import arthur.backend.ArthurCompiler;

public class MediaContainer {

  public String name;
  public ArthurMedia media;
  public String filename;

  public MediaContainer(String name, ArthurMedia media) {
    this.name = name;
    this.media = media;
    setFilename();
  }

  private void setFilename() {
    String n = ArthurCompiler.MEDIA_DIR + "/" + this.name;
    switch (this.media.type) {
      case ArthurColor.COLOR:
        this.filename = n + "__color__.json"; break;
      case ArthurImage.IMAGE:
        this.filename = n + "__image__.jpg"; break;
      case ArthurNumber.NUMBER:
        this.filename = n + "__number__.json"; break;
      case ArthurSound.SOUND:
        this.filename = n + "__sound__.mp3"; break;
      case ArthurString.STRING:
        this.filename = n + "__string__.json"; break;
      case ArthurVideo.VIDEO:
        this.filename = n + "__video__.mp4"; break;
      default:
        this.filename = n + "_void";
    }
  }

  public void storeMedia() {
    // when we write add the parent folder
    this.media.writeToFile(ArthurCompiler.DEFAULT_OUT_NAME + "/" + this.filename);
  }

}

package arthur.backend.whisperer;

import arthur.backend.media.*;

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
    switch (this.media.type) {
      case ArthurColor.COLOR:
        this.filename = this.name + "__color.txt"; break;
      case ArthurImage.IMAGE:
        this.filename = this.name + "__image.png"; break;
      case ArthurNumber.NUMBER:
        this.filename = this.name + "__number.txt"; break;
      case ArthurSound.SOUND:
        this.filename = this.name + "__sound.mp3"; break;
      case ArthurString.STRING:
        this.filename = this.name + "__string.txt"; break;
      case ArthurVideo.VIDEO:
        this.filename = this.name + "__video.mp4"; break;
      default:
        this.filename = this.name + "_void";
    }
  }

  public void storeMedia() {
    this.media.writeToFile(this.filename);
  }

}

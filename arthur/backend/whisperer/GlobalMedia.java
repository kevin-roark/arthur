package arthur.backend.whisperer;

import arthur.backend.media.*;

public class GlobalMedia implements java.io.Serializable {

  public String name;
  public ArthurMedia value;
  public String type;
  public String mediaFile;

  public GlobalMedia(String name, ArthurMedia value) {
    this.name = name;
    this.value = value;
    this.type = value.type;
  }

  public GlobalMedia(String name, Boolean value) {
    this.name = name;
    this.value = new ArthurBoolean(value);
    this.type = this.value.type;
  }

  public void setMediaFile(MediaContainer c) {
    this.mediaFile = c.filename;
  }

  public String toString() {
    String s = "media: " + name + " | type: " + type + " | value: " + value;
    if (this.mediaFile != null) {
      s += " | file: " + this.mediaFile;
    }
    return s;
  }

}

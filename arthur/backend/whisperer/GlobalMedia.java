package arthur.backend.whisperer;

import arthur.backend.media.*;

public class GlobalMedia implements java.io.Serializable {

  public String name;
  public transient ArthurMedia value;
  public String type;

  public GlobalMedia(String name, ArthurMedia value) {
    this.name = name;
    this.value = value;
    this.type = value.type;
  }

  public String toString() {
    return "media variable named " + name + " with value type " + type;
  }

}

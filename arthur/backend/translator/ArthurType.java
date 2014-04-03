package arthur.backend.translator;

import arthur.backend.media.ArthurMedia;

public abstract class ArthurType implements java.io.Serializable {

  public String name;
  public transient ArthurMedia value;

  public String toString() {
    return "arthurtype named " + name;
  }

}

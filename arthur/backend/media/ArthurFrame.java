package arthur.backend.media;

public class ArthurFrame implements java.io.Serializable {

  public ArthurNumber x, y, w, h;

  public ArthurFrame(ArthurNumber x, ArthurNumber y, ArthurNumber w, ArthurNumber h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  public String toString() {
    return "frame | " + x + " | " + y + " | " + w + " | " + h;
  }

  public String json() {
    String js = "{";
    js += "'x': " + this.x.val + ", ";
    js += "'y': " + this.y.val + ", ";
    js += "'w': " + this.w.val + ", ";
    js += "'h': " + this.h.val + "";
    js += "}";
    js = js.replace("'", "\"");
    return js;
  }

}

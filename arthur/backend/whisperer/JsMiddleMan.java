package arthur.backend.whisperer;

import arthur.backend.media.*;

public class JsMiddleMan {

  public static int MIN_LEN = 93;

  public String translation;
  public JsWhisperer whisperer;

  public JsMiddleMan(String translation, JsWhisperer whisperer) {
    this.translation = translation;
    this.whisperer = whisperer;
  }

  public String augment() {
    this.removeStupidFunctions();
    int placeToAdd = this.addGlobalValue();
    this.addArthurMedia(placeToAdd);
    return this.translation;
  }

  private int addGlobalValue() {
    int lastAfterIdx = 0;
    for (GlobalMedia gm : this.whisperer.localGlobals) {
      String dec = "var " + gm.name;
      String decVal = dec + " = " + gm.value.jsLiteral();
      decVal.replace("\n", "");
      int idx = this.translation.indexOf(dec);
      if (idx != -1) {
        String before = this.translation.substring(0, idx);
        String after = this.translation.substring(idx + dec.length());
        this.translation = before + decVal + after;
        lastAfterIdx = idx + decVal.length();
      }
    }
    return Math.max(lastAfterIdx + 1, MIN_LEN);
  }

  private void removeStupidFunctions() {

  }

  private void addArthurMedia(int idx) {
    String before = this.translation.substring(0, idx);
    String after = this.translation.substring(idx);
    String additions = "\n";
    for (String s : this.whisperer.localMediaFiles) {
      if (s.indexOf("color") != -1) {
        additions += "addArthurColor('" + s + "');";
      } else if (s.indexOf("image") != -1) {
        additions += "addArthurImage('" + s + "');";
      } else if (s.indexOf("number") != -1) {
        additions += "addArthurNumber('" + s + "');";
      } else if (s.indexOf("sound") != -1) {
        additions += "addArthurSound('" + s + "');";
      } else if (s.indexOf("video") != -1) {
        additions += "addArthurVideo('" + s + "');";
      } else if (s.indexOf("string") != -1) {
        additions += "addArthurString('" + s + "');";
      }

      additions += "\n";
    }

    additions += "\n";

    this.translation = before + additions + after;
  }

}

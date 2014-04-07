package arthur.backend.whisperer;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import arthur.backend.media.*;
import arthur.backend.translator.js.JsArthurTranslator;

public class JsMiddleMan {

  public static int MIN_LEN = JsArthurTranslator.introLength();

  public static final Pattern DECP = Pattern.compile("var [a-zA-Z_0-9]+\\.set\\((.*?)\\)");

  public String translation;
  public JsWhisperer whisperer;

  public JsMiddleMan(String translation, JsWhisperer whisperer) {
    this.translation = translation;
    this.whisperer = whisperer;
  }

  public String augment() {
    this.removeStupidFunctions();
    this.fixVarDeclarations();
    int placeToAdd = this.addGlobalValue();
    this.addArthurMedia(placeToAdd);
    return this.translation;
  }

  private int addGlobalValue() {
    int lastAfterIdx = 0;
    for (GlobalMedia gm : this.whisperer.localGlobals) {
      String dec = "var " + gm.name;
      String decVal = dec + " = " + literalWrapper(gm, gm.value.jsLiteral());
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

  private String literalWrapper(GlobalMedia gm, String literal) {
    if (gm.mediaFile != null)
      return "literalWrapper(" + literal + ", '" + gm.mediaFile + "')";
    else
      return "literalWrapper(" + literal + ")";
  }

  private void removeStupidFunctions() {

  }

  /* replaces var x.set with var x = new Media(); x.set */
  private void fixVarDeclarations() {
    while (true) {
      Matcher m = DECP.matcher(this.translation);
      if (m.find()) {
          String setter = m.group();
          int transIdx = this.translation.indexOf(setter);
          int afterIdx = transIdx + setter.length();
          String before = this.translation.substring(0, transIdx);
          String after = this.translation.substring(afterIdx);

          int setIdx = setter.indexOf(".set(");
          int spaceIdx = setter.indexOf(" ");
          String varname = setter.substring(spaceIdx + 1, setIdx);
          String fix = "var " + varname + " = new ArthurMedia();\n";
          fix += varname + setter.substring(setIdx);

          this.translation = before + fix + after;
      } else {
        return;
      }
    }
  }

  private void addArthurMedia(int idx) {
    String before = this.translation.substring(0, idx);
    String after = this.translation.substring(idx);
    String additions = "\n";
    for (MediaContainer mc : this.whisperer.localMediaFiles.finalMedia) {
      ArthurMedia m = mc.media;
      switch(m.type) {
        case ArthurColor.COLOR:
          additions += "addArthurColor('" + m.json() + "', '" + mc.filename + "')";
          break;
        case ArthurNumber.NUMBER:
          additions += "addArthurNumber('" + m.json() + "', '" + mc.filename + "')";
          break;
        case ArthurImage.IMAGE:
          additions += "addArthurImage('" + m.json() + "', '" + mc.filename + "')";
          break;
        case ArthurSound.SOUND:
          additions += "addArthurSound('" + m.json() + "', '" + mc.filename + "')";
          break;
        case ArthurVideo.VIDEO:
          additions += "addArthurVideo('" + m.json() + "', '" + mc.filename + "')";
          break;
        case ArthurString.STRING:
          additions += "addArthurString('" + m.json() + "', '" + mc.filename + "')";
          break;
        default: break;
      }

      additions += "\n";
    }

    additions += "\n";

    this.translation = before + additions + after;
  }

}

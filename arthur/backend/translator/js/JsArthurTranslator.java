package arthur.backend.translator.js;

import java.util.ArrayList;

import arthur.frontend.ParseNode;
import arthur.backend.translator.ArthurTranslator;
import arthur.backend.translator.ArthurType;

public class JsArthurTranslator extends ArthurTranslator {

  public JsArthurTranslator(ParseNode source) {
    this(source, true, 0);
  }

  public JsArthurTranslator(ParseNode source, boolean isStatement, int bd) {
    this.source = source;
    this.blockDepth = bd;
    this.ignoreChildren = false;
    this.isStatement = isStatement;
  }

  public String getIntro() {
    String s = "/**\n";
    s += " * An automatically generated translation from arthur to javascript.\n";
    s += " */\n\n";
    s += "$(function() {\n\n";

    // types
    s += "var arthur = require('./arthur/index');\n";
    s += "var ArthurMedia = arthur.ArthurMedia;\n";
    s += "var ArthurColor = arthur.ArthurColor;\n";
    s += "var ArthurNumber = arthur.ArthurNumber;\n";
    s += "var ArthurString = arthur.ArthurString;\n";
    s += "var ArthurImage = arthur.ArthurImage;\n";
    s += "var ArthurSound = arthur.ArthurSound;\n";
    s += "var ArthurVideo = arthur.ArthurVideo;\n";

    // adding media
    s += "var addArthurColor = arthur.addArthurColor;\n";
    s += "var addArthurImage = arthur.addArthurImage;\n";
    s += "var addArthurNumber = arthur.addArthurNumber;\n";
    s += "var addArthurString = arthur.addArthurString;\n";
    s += "var addArthurSound = arthur.addArthurSound;\n";
    s += "var addArthurVideo = arthur.addArthurVideo;\n";
    s += "var add = arthur.add;\n\n";

    // builtins
    s += "var ms = arthur.builtins.ms;\n";
    s += "var rand = arthur.builtins.rand;\n";
    s += "var cooler = arthur.builtins.cooler;\n";
    s += "var frame = arthur.builtins.frame;\n";
    s += "var literalWrapper = arthur.literalWrapper;\n\n";

    // colors oh boy
    s += "var RED = arthur.builtins.RED;\n";
    s += "var WHITE = arthur.builtins.WHITE;\n";
    s += "var BLACK = arthur.builtins.BLACK;\n";
    s += "var BLUE = arthur.builtins.BLUE;\n";
    s += "var GREEN = arthur.builtins.GREEN;\n";
    s += "var ORANGE = arthur.builtins.ORANGE;\n";
    s += "var YELLOW = arthur.builtins.YELLOW;\n";
    s += "var PERRYWINKLE = arthur.builtins.PERRYWINKLE;\n";
    s += "var ARTHURS_SKIN = arthur.builtins.ARTHURS_SKIN;\n";
    s += "var SARCOLINE = arthur.builtins.SARCOLINE;\n";
    s += "var COQUELICOT = arthur.builtins.COQUELICOT;\n";
    s += "var SMARAGDINE = arthur.builtins.SMARAGDINE;\n";
    s += "var ALMOND = arthur.builtins.ALMOND;\n";
    s += "var ASPARAGUS = arthur.builtins.ASPARAGUS;\n";
    s += "var BURNT_SIENNA = arthur.builtins.BURNT_SIENNA;\n";
    s += "var CERULEAN = arthur.builtins.CERULEAN;\n";
    s += "var DANDELION = arthur.builtins.DANDELION;\n";
    s += "var DENIM = arthur.builtins.DENIM;\n";
    s += "var ELECTRIC_LIME = arthur.builtins.ELECTRIC_LIME;\n";
    s += "var FUZZY_WUZZY = arthur.builtins.FUZZY_WUZZY;\n";
    s += "var GOLDENROD = arthur.builtins.GOLDENROD;\n";
    s += "var JAZZBERRY_JAM = arthur.builtins.JAZZBERRY_JAM;\n";
    s += "var MAC_AND_CHEESE = arthur.builtins.MAC_AND_CHEESE;\n";
    s += "var MAHOGANY = arthur.builtins.MAHOGANY;\n";
    s += "var MANGO_TANGO = arthur.builtins.MANGO_TANGO;\n";
    s += "var MAUVELOUS = arthur.builtins.MAUVELOUS;\n";
    s += "var PURPLE_PIZZAZZ = arthur.builtins.PURPLE_PIZZAZZ;\n";
    s += "var RAZZMATAZZ = arthur.builtins.RAZZMATAZZ;\n";
    s += "var SALMON = arthur.builtins.SALMON;\n";
    s += "var SILVER = arthur.builtins.SILVER;\n";
    s += "var TICKLE_ME_PINK = arthur.builtins.TICKLE_ME_PINK;\n";
    s += "var WILD_BLUE_YONDER = arthur.builtins.WILD_BLUE_YONDER;\n";
    s += "var WISTERIA = arthur.builtins.WISTERIA;\n";
    s += "var LASER_LEMON = arthur.builtins.LASER_LEMON;\n";
    s += "var EGGPLANT = arthur.builtins.EGGPLANT;\n";
    s += "var CHARTREUSE = arthur.builtins.CHARTREUSE;\n\n";

    // cruft to make builtins work
    s += "var updateMedia = arthur.updateMedia;\n";
    s += "var hasLoop = typeof loop != 'undefined';\n";
    s += "function looper() { requestAnimationFrame(looper); if (hasLoop) loop(); updateMedia(); }\n";

    s += "if (typeof key != 'undefined') $(document).keypress(function(ev) {key(new ArthurString(String.fromCharCode(ev.which))); } );\n";

    s += "if (typeof click != 'undefined') $(document).click(function(e) { click(new ArthurNumber(e.clientX), new ArthurNumber(e.clientY)); });\n";

    s += "if (typeof move != 'undefined') $(document).mousemove(function(e) { move(new ArthurNumber(e.clientX), new ArthurNumber(e.clientY)); });\n";

    return s + "\n";
  }

  public static int introLength() {
    JsArthurTranslator t = new JsArthurTranslator(null);
    return t.getIntro().length();
  }

  public String getOutro() {
    String s = "\nlooper();\n";
    return s + "\n});\n";
  }

  public String functionCode(ParseNode n) {
    ParseNode returnType = n.children.get(0);
    ParseNode fname = n.children.get(1);
    ParseNode parameters = n.children.get(2);
    JsArthurFun fun = new JsArthurFun(fname.val, returnType.val);
    activeFunction = fun;
    return "\n" + fun.getFunDec();
  }

  public String varCode(ParseNode n) {
    ParseNode type = n.children.get(0);
    ParseNode name = n.children.get(1);
    JsArthurVar var = new JsArthurVar(name.val, type.val);
    return var.getVarDec();
  }

  public String parameterCode(ParseNode param) {
    ParseNode ptype = param.children.get(0);
    ParseNode pname = param.children.get(1);
    JsArthurVar p = new JsArthurVar(pname.val, ptype.val);
    activeFunction.addParameter(p);
    return p.getParamDec();
  }

  public String setToSymbol() {
    return ".set(";
  }

  public String setToEndSymbol() {
    return ")";
  }

  public String numDec() {
    return "var ";
  }

  public String castCode(ParseNode n) {
    ParseNode type = n.children.get(1);
    ParseNode one = n.children.get(0);
    String id;

    if (one.val.equals("Identifier")) {
      id = one.children.get(0).val;
    } else if (one.val.equals("Color")) {
      id = colorLiteral(one);
    } else if (one.val.equals("number")) {
      id = numberLiteral(one);
    } else if (one.val.equals("String")) {
      id = stringLiteral(one);
    } else {
      id = "BAD CAST";
    }

    return id + ".castTo(\"" + type.val + "\")";
  }

  public String colorLiteral(ParseNode n) {
    return JsArthurVar.colorLiteral(n);
  }

  public String numberLiteral(ParseNode n) {
    ParseNode num = n.children.get(0);
    return JsArthurVar.numLiteral(num.val);
  }

  public String stringLiteral(ParseNode n) {
    ParseNode str = n.children.get(0);
    return JsArthurVar.stringLiteral(str.val);
  }

  public String createAndTranslate(ParseNode source, boolean statement) {
    JsArthurTranslator t = new JsArthurTranslator(source, statement, this.blockDepth);
    return t.translateTree();
  }

}

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

    // builtins
    s += "var ms = arthur.builtins.ms;\n";
    s += "var literalWrapper = arthur.literalWrapper;\n";

    s += "var updateMedia = arthur.updateMedia;\n";
    s += "function looper() { requestAnimationFrame(looper); loop(); updateMedia(); }\n";

    return s + "\n";
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

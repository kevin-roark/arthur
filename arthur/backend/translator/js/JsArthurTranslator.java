package arthur.backend.translator.js;

import java.util.ArrayList;

import arthur.frontend.ParseNode;
import arthur.backend.translator.ArthurTranslator;
import arthur.backend.translator.ArthurType;
import arthur.backend.whisperer.JsWhisperer;


public class JsArthurTranslator extends ArthurTranslator {

  public JavaArthurTranslator(ParseNode source, JsWhisperer whisperer) {
    this(source, true, whisperer, 0);
  }

  public JavaArthurTranslator(ParseNode source, boolean isStatement, JsWhisperer whisperer, int bd) {
    this.source = source;
    this.globals = whisperer.globals;
    this.blockDepth = bd;
    this.ignoreChildren = false;
    this.isStatement = isStatement;
    this.whisperer = whisperer;
  }

  public String getIntro() {
    String s = "/**\n";
    s += " * An automatically generated translation from arthur to javascript.\n";
    s += " */\n\n";
    s += "$(function() {\n";
    return s;
  }

  public String getOutro() {
    return "\n});\n";
  }

  public String functionCode(ParseNode n) {
    ParseNode returnType = n.children.get(0);
    ParseNode fname = n.children.get(1);
    ParseNode parameters = n.children.get(2);
    JsArthurFun fun = new JsArthurFun(fname.val, returnType.val);
    if (blockDepth == 0)
      this.globals.add(fun);
    activeFunction = fun;
    return "\n" + fun.getFunDec();
  }

  public String varCode(ParseNode n) {
    ParseNode type = n.children.get(0);
    ParseNode name = n.children.get(1);
    JsArthurVar var = new JsArthurVar(name.val, type.val);
    if (blockDepth == 0)
      this.globals.add(var);
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
    JsArthurTranslator t = new JsArthurTranslator(source, statement, null, this.blockDepth);
    return t.translateTree();
  }

}

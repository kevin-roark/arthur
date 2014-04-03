package arthur.backend.translator.java;

import java.util.ArrayList;

import arthur.frontend.ParseNode;
import arthur.backend.translator.ArthurTranslator;
import arthur.backend.translator.ArthurType;
import arthur.backend.whisperer.JsWhisperer;

public class JavaArthurTranslator extends ArthurTranslator {

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
    s += " * An automatically generated translation from arthur to java.\n";
    s += " */\n\n";
    s += "import arthur.backend.media.*;\n";
    s += "import arthur.backend.whisperer.*;\n";
    s += "import static arthur.backend.builtins.java.JavaBuiltins.*;\n";
    s += "\npublic class ArthurTranslation {\n";
    s += "public ArthurTranslation() { init(); JsWhisperer.writeToBlob(); }\n\n";
    return s;
  }

  public String getOutro() {
    String s = "";
    s += "\n}\n";
    return s;
  }

  public String functionCode(ParseNode n) {
    ParseNode returnType = n.children.get(0);
    ParseNode fname = n.children.get(1);
    ParseNode parameters = n.children.get(2);
    JavaArthurFun fun = new JavaArthurFun(fname.val, returnType.val);
    if (blockDepth == 0)
      this.globals.add(fun);
    activeFunction = fun;
    return "\n" + fun.getFunDec();
  }

  public String varCode(ParseNode n) {
    ParseNode type = n.children.get(0);
    ParseNode name = n.children.get(1);
    JavaArthurVar var = new JavaArthurVar(name.val, type.val);
    if (blockDepth == 0)
      this.globals.add(var);
    return var.getVarDec();
  }

  public String parameterCode(ParseNode param) {
    ParseNode ptype = param.children.get(0);
    ParseNode pname = param.children.get(1);
    JavaArthurVar p = new JavaArthurVar(pname.val, ptype.val);
    activeFunction.addParameter(p);
    return p.getVarDec();
  }

  public String colorLiteral(ParseNode n) {
    return JavaArthurVar.colorLiteral(n);
  }

  public String numberLiteral(ParseNode n) {
    ParseNode num = n.children.get(0);
    return JavaArthurVar.numLiteral(num.val);
  }

  public String stringLiteral(ParseNode n) {
    ParseNode str = n.children.get(0);
    return JavaArthurVar.stringLiteral(str.val);
  }

  public String createAndTranslate(ParseNode source, boolean statement) {
    JavaArthurTranslator t = new JavaArthurTranslator(source, statement, this.whisperer, this.blockDepth);
    return t.translateTree();
  }

}

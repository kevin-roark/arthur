package arthur.backend.translator.java;

import java.util.ArrayList;

import arthur.frontend.ParseNode;
import arthur.backend.translator.ArthurTranslator;
import arthur.backend.translator.ArthurType;

public class JavaArthurTranslator extends ArthurTranslator {

  public JavaArthurTranslator(ParseNode source) {
    this(source, true);
  }

  public JavaArthurTranslator(ParseNode source, boolean isStatement) {
    this.source = source;
    this.globals = new ArrayList<ArthurType>();
    this.blockDepth = 0;
    this.ignoreChildren = false;
    this.isStatement = isStatement;
  }

  public String getIntro() {
    String s = "/**\n";
    s += " * An automatically generated translation from arthur to java.\n";
    s += " */\n\n";
    s += "public class ArthurTranslation {\n";
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
    JavaArthurTranslator t = new JavaArthurTranslator(source, statement);
    return t.translateTree();
  }

}

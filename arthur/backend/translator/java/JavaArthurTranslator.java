package arthur.backend.translator.java;

import java.util.ArrayList;

import arthur.frontend.ParseNode;
import arthur.backend.translator.ArthurTranslator;
import arthur.backend.translator.ArthurType;

public class JavaArthurTranslator extends ArthurTranslator {

  public JavaArthurTranslator(ParseNode source) {
    this(source, true, 0);
  }

  public JavaArthurTranslator(ParseNode source, boolean isStatement, int bd) {
    this.source = source;
    this.blockDepth = bd;
    this.ignoreChildren = false;
    this.isStatement = isStatement;
  }

  public String getIntro() {
    String s = "/**\n";
    s += " * An automatically generated translation from arthur to java.\n";
    s += " */\n\n";

    // imports
    s += "import arthur.backend.media.*;\n";
    s += "import arthur.backend.whisperer.*;\n";
    s += "import static arthur.backend.builtins.java.JavaBuiltins.*;\n";
    s += "import java.lang.reflect.Field;";


    // class and main
    s += "\npublic class ArthurTranslation {\n";
    s += "\npublic static void main(String[] args) { ArthurTranslation a = new ArthurTranslation(); }\n";

    // addFields
    s += "\npublic void addFields(Field[] fields) {\n for (Field f : fields) {\n";
    s += "  try {\n";
    s += "    Object val = f.get(this); JsWhisperer.addGlobal(f.getName(), val);\n";
    s += "  } catch(IllegalAccessException e) { e.printStackTrace(); }\n }\n }\n";

    // isField
    s += "public static String _isField(Object o, Object v) {\n";
    s += "  for (Field f : o.getClass().getDeclaredFields()) {\n";
    s += "    try {\n";
    s += "      Object val = f.get(o);\n";
    s += "      if (val == v) { return f.getName(); }\n";
    s += "    } catch(IllegalAccessException e) { e.printStackTrace(); }\n";
    s += "  }\n";
    s += "  return null;\n";
    s += "}\n";

    // add
    s += "\npublic void add(ArthurMedia media) {\n";
    s += "  String name = _isField(this, media);\n";
    s += "  _addMedia(media, name); \n}\n";

    // constructor
    s += "public ArthurTranslation() { init(); addFields(getClass().getDeclaredFields()); JsWhisperer.writeToBlob(); }\n\n";
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
    activeFunction = fun;
    return "\n" + fun.getFunDec();
  }

  public String varCode(ParseNode n) {
    ParseNode type = n.children.get(0);
    ParseNode name = n.children.get(1);
    JavaArthurVar var = new JavaArthurVar(name.val, type.val);
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
    JavaArthurTranslator t = new JavaArthurTranslator(source, statement, this.blockDepth);
    return t.translateTree();
  }

}

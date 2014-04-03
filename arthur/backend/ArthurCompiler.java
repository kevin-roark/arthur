package arthur.backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import arthur.frontend.ParseNode;
import arthur.frontend.Parser;

import arthur.backend.whisperer.JsWhisperer;
import arthur.backend.translator.java.JavaArthurTranslator;

public class ArthurCompiler {

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("usage: java ArthurCompiler <arthur_source_program_file>");
      return;
    }

    String Filename = args[0];

    FileReader reader;
    try {
        reader = new FileReader(Filename);
    } catch (FileNotFoundException e) {
        System.out.println("That file doesn't exist so it can't be arthur program fool!!");
        return;
    }

    Parser parser = new Parser(false);
    ParseNode s = parser.doParsing(reader);

    JsWhisperer whisperer = new JsWhisperer();

    JavaArthurTranslator translator = new JavaArthurTranslator(s, whisperer);
    String translation = translator.translateTree();
    writeTranslation(translation);

  }

  public static void writeTranslation(String translation) {
    try {
      PrintWriter out = new PrintWriter("ArthurTranslation.java");
      out.println(translation);
      out.close();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public ArthurCompiler() {

  }

}

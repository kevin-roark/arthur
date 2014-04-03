package arthur.backend.translator.js;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import arthur.frontend.Parser;
import arthur.frontend.ParseNode;
import arthur.backend.whisperer.JsWhisperer;

public class JsTranslatorTester {

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("usage: java JsArthurTranslator <arthur_source_program_file>");
      return;
    }

    String Filename = args[0];

    FileReader reader;
    try {
        reader = new FileReader(Filename);
    } catch (FileNotFoundException e) {
        System.out.println("That file doesn't exist!!");
        return;
    }

    Parser parser = new Parser(false);
    ParseNode s = parser.doParsing(reader);

    JsArthurTranslator translator = new JsArthurTranslator(s);
    String translation = translator.translateTree();
    System.out.println(translation);
  }

}

package arthur.backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;

import arthur.frontend.ParseNode;
import arthur.frontend.Parser;

import arthur.backend.whisperer.JsWhisperer;
import arthur.backend.translator.java.JavaArthurTranslator;

public class ArthurCompiler {

  public static final String TNAME = "ArthurTranslation";

  public static void main(String[] args) throws IOException, InterruptedException {
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

    JavaArthurTranslator translator = new JavaArthurTranslator(s);
    String translation = translator.translateTree();
    writeTranslation(translation);

    runTranslation();

    JsWhisperer whisperer = restoreWhisperer();
    System.out.println(whisperer);

  }

  public static void writeTranslation(String translation) {
    try {
      PrintWriter out = new PrintWriter(TNAME + ".java");
      out.println(translation);
      out.close();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static void removeTranslationFile() throws IOException {
    Runtime.getRuntime().exec("rm -f " + TNAME + ".java");
    Runtime.getRuntime().exec("rm -f " + TNAME + ".class");
  }

  public static void runTranslation() throws IOException, InterruptedException {
    Process p = Runtime.getRuntime().exec("javac " + TNAME + ".java");
    p.waitFor();
    p = Runtime.getRuntime().exec("java " + TNAME);
    p.waitFor();
  }

  public static JsWhisperer restoreWhisperer() throws IOException {
    JsWhisperer w = JsWhisperer.restoreFromBlob();
    JsWhisperer.removeBlobFile();
    removeTranslationFile();
    return w;
  }

  public ArthurCompiler() {

  }

}

package arthur.backend;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

import arthur.frontend.ParseNode;
import arthur.frontend.Parser;

import arthur.backend.whisperer.JsWhisperer;
import arthur.backend.whisperer.JsMiddleMan;
import arthur.backend.translator.java.JavaArthurTranslator;
import arthur.backend.translator.js.JsArthurTranslator;

public class ArthurCompiler {

  public static final String TNAME = "ArthurTranslation";
  public static final String JSNAME = "arthurtrans.js";
  public static final String DEFAULT_OUT_NAME = "buster";
  public static final String MEDIA_DIR = "media";
  public static String mdirname = DEFAULT_OUT_NAME + "/" + MEDIA_DIR;

  public static void main(String[] args) throws IOException, InterruptedException {
    if (args.length < 1) {
      System.out.println("usage: java ArthurCompiler <options> <arthur_source_program_file>");
      return;
    }

    boolean verbose = true;
    boolean run = true;
    boolean clean = true;

    for (int i = 0; i < args.length - 1; i++) {
      String arg = args[i];
      if (arg.equals("-s")) {
        verbose = false;
      }
      if (arg.equals("-trans")) {
        run = false;
        System.out.println("only translating");
      }
      if (arg.equals("-dirty")) {
        clean = false;
        System.out.println("not removing client fluff");
      }
    }

    // read the source
    String filename = args[args.length - 1];
    if (verbose)
      System.out.println("reading the source from " + filename);
    FileReader reader;
    try {
        reader = new FileReader(filename);
    } catch (FileNotFoundException e) {
        System.out.println("That file doesn't exist so it can't be arthur program fool!!");
        return;
    }

    // parse the source
    if (verbose)
      System.out.println("parsing the source");
    Parser parser = new Parser(false);
    ParseNode s = parser.doParsing(reader);

    // create output directory
    String dirname = makeBundleDir(filename);
    if (verbose)
      System.out.println("bulding output directory at " + dirname);

    // translate to java
    if (verbose)
      System.out.println("translating to java");
    JavaArthurTranslator javaTranslator = new JavaArthurTranslator(s);
    String javaTranslation = javaTranslator.translateTree();
    writeTranslation(javaTranslation);

    if (!run)
      return;

    // run the java translation
    if (verbose)
      System.out.println("running the java translation");
    runTranslation();

    // get the relevant output
    if (verbose)
      System.out.println("restoring the java output");
    JsWhisperer whisperer = restoreWhisperer();

    // translate to javascript
    if (verbose)
      System.out.println("translating to javascript");
    JsArthurTranslator jsTranslator = new JsArthurTranslator(s);
    String jsTranslation = jsTranslator.translateTree();

    // augment the javascript
    if (verbose)
      System.out.println("meddling the javascript");
    JsMiddleMan meddler = new JsMiddleMan(jsTranslation, whisperer);
    String meddledJs = meddler.augment();

    // build the output
    if (verbose)
      System.out.println("building output");
    buildClient(dirname, meddledJs, clean);
  }

  public static void execAndPrint(String exec, boolean print) throws IOException, InterruptedException {
    Process p = Runtime.getRuntime().exec(exec);
    if (print) {
      System.out.println(exec);
      IoUtils.copy(p.getInputStream(), System.out);
    }
    p.waitFor();
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

  public static void removeTranslationFile() throws IOException, InterruptedException {
    String rm = "rm -f " + TNAME + ".java";
    execAndPrint(rm, false);
    String rm2 = "rm -f " + TNAME + ".class";
    execAndPrint(rm2, false);
  }

  public static void runTranslation() throws IOException, InterruptedException {
    String compile = "javac " + TNAME + ".java";
    String run = "java -Xmx4g " + TNAME;
    execAndPrint(compile, false);
    execAndPrint(run, false);
  }

  public static JsWhisperer restoreWhisperer() throws IOException, InterruptedException {
    JsWhisperer w = JsWhisperer.restoreFromBlob();
    JsWhisperer.removeBlobFile();
    removeTranslationFile();
    return w;
  }

  public static String makeBundleDir(String filename) throws IOException, InterruptedException {
    //String dirname = filename.substring(0, filename.lastIndexOf('.'));
    String dirname = DEFAULT_OUT_NAME;
    File dir = new File(dirname);

    // if the directory does not exist, create it
    if (!dir.exists()) {
      dir.mkdir();
    }

    mdirname = dirname + "/" + MEDIA_DIR;
    File mediaDir = new File(mdirname);
    if (!mediaDir.exists()) {
      mediaDir.mkdir();
    }

    // copy all the relevant stuff
    String ex = "./arthur/copyclient.js " + dirname;
    execAndPrint(ex, false);

    return dirname;
  }

  public static void buildClient(String dirname, String javascript, boolean clean) throws IOException, InterruptedException {
    try {
      // write the js
      String filename = dirname + "/js/" + JSNAME;
      PrintWriter out = new PrintWriter(filename);
      out.println(javascript);
      out.close();

      // build it with browserify
      String ex;
      if (clean)
        ex = "make jsbuild";
      else
        ex = "make dirtybuild";
      execAndPrint(ex, true);

    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
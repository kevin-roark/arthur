/**
 * Tester for Lexer generated by arthur.flex.
 */
package arthur.frontend;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class yacctest {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java yacctest <name_of_file>");
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
        parser.doParsingAndPrint(reader);
    }
}

/**
 * Tester for Lexer generated by arthur.flex.
 */
package arthur.frontend;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class lextest {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java lextest <name_of_file>");
            return;
        }

        String Filename = args[0];

        FileReader Reader;
        try {
            Reader = new FileReader(Filename);
        } catch (FileNotFoundException e) {
            System.out.println("That file doesn't exist!!");
            return;
        }

        Lexer Lexer = new Lexer(Reader);

        Token token;

        do {
            try {
                token = Lexer.yylex();
            } catch(IOException e) {
                System.out.println("got this exception getting token: " + e);
                break;
            }
            if (token != null) {
                    System.out.println(token);
            }
        } while(token != null && token.tokenType != Tokens.EOF);
    }

}

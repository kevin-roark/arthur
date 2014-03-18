/**
 * Kevin Roark - ker2143
 *
 * Java program to run the jflex output for problem 2
 */

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class lextest {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java lextest <name_of_dict_file>");
            return;
        }

        String dictFilename = args[0];

        FileReader dictReader;
        try {
            dictReader = new FileReader(dictFilename);
        } catch (FileNotFoundException e) {
            System.out.println("That file doesn't exist!!");
            return;
        }

        Lexer dictLexer = new Lexer(dictReader);

        Token token;

        do {
            try {
                token = dictLexer.yylex();
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

/**
 * Kevin Roark - ker2143
 *
 * Java program to run the jflex output for problem 2
 */

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class problem2 {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java problem2 <name_of_dict_file>");
            return;
        }

        String dictFilename = args[0];
        System.out.println("Reading dictionary from file:" + dictFilename);

        FileReader dictReader;
        try {
            dictReader = new FileReader(dictFilename);
        } catch (FileNotFoundException e) {
            System.out.println("That file doesn't exist!!");
            return;
        }

        Lexer dictLexer = new Lexer(dictReader);

        String token;
        int longest = 0;
        ArrayList<String> longestWords = new ArrayList<String>();

        System.out.println("Searching for the longest words that are " +
            "telephone-formable from the digits '8538'.");

        do {
            try {
                token = dictLexer.yylex();
            } catch(IOException e) {
                System.out.println("got this exception getting token: " + e);
                break;
            }
            if (token != null) {
                if (token.length() > longest) {
                    longestWords = new ArrayList<String>();
                    longestWords.add(token);
                    longest = token.length();
                    System.out.println("New longest word: " + token);
                }
                else if (token.length() == longest) {
                    longestWords.add(token);
                }
                else {
                  /* do nothing */
                }
            }
        } while(token != null);

        System.out.println("All done! Longest words:");
        for (String word : longestWords) {
            System.out.println(word);
        }
    }

}

import java.io.FileReader;
import java.io.FileNotFoundException;

public class problem4 {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java problem4 <name_of_expression_file>");
            return;
        }

        String expressionFilename = args[0];
        System.out.println("Reading expressions from file: " + expressionFilename);

        FileReader expressionReader;
        try {
            expressionReader = new FileReader(expressionFilename);
        } catch (FileNotFoundException e) {
            System.out.println("That file doesn't exist!!");
            return;
        }

        Parser expressionParser = new Parser(false);
        expressionParser.doParsing(expressionReader);
    }
}
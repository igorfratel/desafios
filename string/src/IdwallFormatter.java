package idwall.desafio.string;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public class IdwallFormatter extends StringFormatter {

    public IdwallFormatter(int limit) {
        super(limit);
    }

    /**
     * Should format as described in the challenge
     *
     * @param text
     * @param justify
     * @return final_text
     */
     @Override
    public String format(String text, Boolean justify) {
        if(text == null || text.isEmpty()) throw new IllegalArgumentException("format: argument text can't be null or empty");
        String[] split_text = text.split("\n{2,}"); //split blocks separated by blank lines
        String final_text = "";

        final_text = format_block(split_text[0], justify, super.get_limit());
        for (int i = 1; i < split_text.length; i++)
                final_text += "\n\n" + format_block(split_text[i], justify, super.get_limit());
        return final_text;
    }

    /**
     * Formats block of text as described in the challenge
     *
     * @param block
     * @param limit
     * @param justify
     * @return formatted_block
     */
     private String format_block(String block, Boolean justify, int limit) {

        String[] split_block = block.split(" |\n");
        int characters_to_print = 0; //characters that will be printed on the current line
        String formatted_block = "";

        for (int i = 0; i < split_block.length; i++) {

                if(characters_to_print == 0) { //First word of the line is always printed
                        formatted_block += split_block[i];
                        characters_to_print += split_block[i].length();
                        continue;
                }

                characters_to_print += split_block[i].length();
                if(characters_to_print <= limit - 3) { //Word in the middle of line
                        formatted_block += " " + split_block[i];
                        characters_to_print++;
                        continue;
                }
                if(characters_to_print <= limit - 1) { //Word at the end of line
                        formatted_block += " " + split_block[i] + "\n";
                        characters_to_print++;
                }
                else{ //Can't add word to line
                        formatted_block += "\n";
                        i--;
                }
                characters_to_print = 0;
        }
        if(justify) return justify_text(formatted_block, limit);
        return formatted_block;
    }

    /**
     * Receives block of text and returns a justified version
     *
     * @param text a String containing a block of text
     * @param limit an int containing the desired length of the justified lines of the text
     * @return justified_text
     */
    private String justify_text(String text, int limit) {
        String[] split_text = text.split("\n");
        String justified_text = "";

        justified_text += justify_line(split_text[0], limit);
        for (int i = 1; i < split_text.length; i++)
                justified_text += "\n" + justify_line(split_text[i], limit);
        return justified_text;
    }

    /**
     * Receives line and returns a justified version with length equal to "limit"
     *
     * @param line a String containing a line
     * @param limit an int containing the desired length of the justified line
     * @return justified_line
     */
    private String justify_line(String line, int limit) {
        String[] split_line = line.split(" ");
        String justified_line = "";
        int spaces_per_gap = (limit - line.length())/(split_line.length- 1);
        int remainder_spaces = (limit - line.length())%(split_line.length - 1);

        String space_string = "";
        for (int i = 0; i < spaces_per_gap; i++) space_string+=" ";
        justified_line += split_line[0];
        for (int i = 1; i < split_line.length; i++) {
                justified_line += space_string + " ";
                if(i <= remainder_spaces) justified_line += " ";
                justified_line += split_line[i];
        }
        return justified_line;
    }

    /**
     * Receives two Strings and prints them in a specific format
     *
     * @param expected a String containing the expected result of the test
     * @param generated a String containing the obtained result
     */
    private static void print_tests(String expected, String generated) {
            System.out.println("EXPECTED: ");
            System.out.println(expected);
            System.out.println("--------------------------");
            System.out.println("GENERATED: ");
            System.out.println(generated);
            System.out.println("==========================");
    }

    public static void main(String[] args) {
        int CONST_LIMIT = 40;
        System.out.println("Starting IdwallFormatter tests: \n");
        IdwallFormatter f1 = new IdwallFormatter(CONST_LIMIT);

        String line1 = "the surface of the deep, and the Spirit";
        String line2 = "of God was hovering over the waters.";
        String line3 = "And God said, \"Let there be light,\" and";
        String line4 = "there was light. God saw that the light";

        String e_line1 = "the  surface of the deep, and the Spirit";
        String e_line2 = "of  God was  hovering over  the  waters.";
        String e_line3 = "And  God said, \"Let there be light,\" and";
        String e_line4 = "there  was light. God saw that the light";

        print_tests(e_line1, f1.justify_line(line1, CONST_LIMIT));
        print_tests(e_line2, f1.justify_line(line2, CONST_LIMIT));
        print_tests(e_line3, f1.justify_line(line3, CONST_LIMIT));
        print_tests(e_line4, f1.justify_line(line4, CONST_LIMIT));



        String block1 = "In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.";

        String e_block1 = "In the beginning God created the heavens" + "\n"+
                           "and   the  earth.   Now  the  earth  was" + "\n"+
                           "formless  and empty,  darkness was  over" + "\n"+
                           "the  surface of the deep, and the Spirit" + "\n"+
                           "of  God was  hovering over  the  waters.";

        print_tests(e_block1, f1.format_block(block1, true, CONST_LIMIT));

        String text1 = "In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.\n" +
                        "\n" +
                        "And God said, \"Let there be light,\" and there was light. God saw that the light";

        String e_text1 = "In the beginning God created the heavens" + "\n"+
                        "and   the  earth.   Now  the  earth  was" + "\n"+
                        "formless  and empty,  darkness was  over" + "\n"+
                        "the  surface of the deep, and the Spirit" + "\n"+
                        "of  God was  hovering over  the  waters." + "\n"+
                        "\n"+
                        "And  God said, \"Let there be light,\" and" + "\n"+
                        "there  was light. God saw that the light";

        print_tests(e_text1 , f1.format(text1, true));

    }
}

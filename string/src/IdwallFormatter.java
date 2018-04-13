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
     * @return final_text
     */
     @Override
     public String format(String text, Boolean justify) {

        String[] split_text = text.split("\\ |\n");
        int characters_to_print = 0; //characters that will be printed on the current line
        String final_text = "";

        //DEBUG
        for (int i = 0; i < split_text.length; i++) {
                System.out.println(split_text[i]);
                System.out.println("----");
        }
        for (int i = 0; i < split_text.length; i++) {

                if(characters_to_print == 0) { //First word of the line is always printed
                        final_text += split_text[i];
                        characters_to_print += split_text[i].length();
                        continue;
                }

                characters_to_print += split_text[i].length();
                if(characters_to_print <= super.get_limit() - 3) { //Word in the middle of line
                        final_text += " " + split_text[i];
                        characters_to_print++;
                        continue;
                }
                if(characters_to_print <= super.get_limit() - 1) { //Word at the end of line
                        final_text += " " + split_text[i] + "\n";
                        characters_to_print++;
                }
                else{ //Can't add word to line
                        final_text += "\n";
                        i--;
                }
                characters_to_print = 0;
        }
        if(justify) return justify_text(final_text, super.get_limit());
        return final_text;
    }

    private String justify_text(String formatted_text, int limit) {
        String[] split_text = formatted_text.split("\n");
        String justified_text = "";

        justified_text += justify_line(split_text[0], limit);
        for (int i = 1; i < split_text.length; i++)
                justified_text += "\n" + justify_line(split_text[i], limit);
        return justified_text;
    }

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

    public static void main(String[] args) {
        System.out.println("Starting IdwallFormatter unit tests: ");
        IdwallFormatter f1 = new IdwallFormatter(40);
        String line1 = "In the beginning God created the heavens";
        String line2 = "and the earth. Now the earth was";
        System.out.println(f1.justify_line(line1, 40));
        System.out.println(f1.justify_line(line2, 40));

    }
}

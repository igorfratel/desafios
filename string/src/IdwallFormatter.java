package idwall.desafio.string;

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
    public String format(String text) {

        /*regex that uses whitespaces and line-breaks followed by another line-break as delimiters.
         *The linebreak followed by another linebreak condition is used to mantain whitelines
         *(ignores multiple whitelines, though)*/
        String[] split_text = text.split("\\ |\n(?=\n)");
        int characters_to_print = 0;
        String final_text = "";

        //DEBUG
        for (int i = 0; i < split_text.length; i++){
            System.out.println(split_text[i]);
            System.out.println("----");
        }

        for (int i = 0; i < split_text.length; i++) {

            //DEBUG(begin)
            System.out.println(split_text[i]);
            System.out.println(characters_to_print);
            System.out.println("------------");

            if(characters_to_print == 0){ //First word of the line is always printed
                final_text += split_text[i];
                characters_to_print += split_text[i].length();
                continue;
            }

            characters_to_print += split_text[i].length();
            if(characters_to_print <= super.get_limit() - 3){ //Word in the middle of line
                final_text += " " + split_text[i];
                characters_to_print++;
                continue;
            }
            if(characters_to_print <= super.get_limit() - 1){ //Word at the end of line
                final_text += " " + split_text[i] + "\n";
                characters_to_print++;
            }
            else{ //Can't add word to line
                final_text += "\n";
                i--;
            }
            characters_to_print = 0;
        }
        return final_text;
    }

    public static void main(String[] args) {
        System.out.println("Starting IdwallFormatter unit tests: ");

    }
}

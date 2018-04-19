package idwall.desafio.string;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 */
public abstract class StringFormatter {

    private Integer limit;

    public StringFormatter(int limit) {
        if(limit <= 0) throw new IllegalArgumentException("StringFormatter: argument limit must be positive");
        this.limit = limit;
    }

    public Integer get_limit() {
        return limit;
    }

    /**
     * It receives a text and should return it formatted
     *
     * @param text
     * @return
     */
    public abstract String format(String text, Boolean justify);
}

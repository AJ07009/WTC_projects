package za.co.wethinkcode.linklists.words;

public class WordsModel {
    private Integer id;
    private String words;
    private String short_word;
    private String long_word;
    private Integer max_words;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMax_words() {
        return max_words;
    }

    public void setMax_words(Integer max_words) {
        this.max_words = max_words;
    }

    public String getShort_word() {
        return short_word;
    }

    public void setShort_word(String short_word) {
        this.short_word = short_word;
    }

    public String getLong_word() {
        return long_word;
    }

    public String getWords() {
        return words;
    }

    public void setLong_word(String long_word) {
        this.long_word = long_word;
    }

    public static WordsModel create(String long_word, Integer max_words ){
        WordsModel wordsModel = new WordsModel();
        wordsModel.setLong_word(long_word);
        wordsModel.setMax_words(max_words);
        return wordsModel;
    }
}

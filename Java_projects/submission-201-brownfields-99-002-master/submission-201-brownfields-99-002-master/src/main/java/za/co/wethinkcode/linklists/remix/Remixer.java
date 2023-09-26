package za.co.wethinkcode.linklists.remix;

import za.co.wethinkcode.linklists.words.WordUrn;

import java.util.ArrayList;

// COMPLETE/EXTEND THIS CLASS
public class Remixer {
    private final WordUrn wordUrn;

    public Remixer(WordUrn wordUrn) {
        this.wordUrn = wordUrn;
    }

    public String shorten(String longUrl, int maxWords) {
        String shortUrl = "";
        System.out.println("Short Url");
        for (int i = 0; i < maxWords; i++) {
            shortUrl += wordUrn.giveWord();
            if (i < maxWords-1){
                shortUrl += "-";
            }
        }

        return shortUrl;
    }

}

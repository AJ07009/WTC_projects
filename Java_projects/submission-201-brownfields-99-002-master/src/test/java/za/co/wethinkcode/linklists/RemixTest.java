package za.co.wethinkcode.linklists;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.linklists.remix.Remixer;
import za.co.wethinkcode.linklists.words.WordUrn;
import za.co.wethinkcode.linklists.words.WordsModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class RemixTest {
    // fake test double
    class FakeWordUrn implements WordUrn {
        private ArrayList<String> words;

        FakeWordUrn(ArrayList<String> fixture) {
            words = fixture;
        }

        @Override
        public WordsModel get(String id) throws SQLException {
            return null;
        }

        @Override
        public String giveWord() { // pops word from list and removes it
            if (words.size() > 0) {
                String top = words.get(words.size() - 1);
                words.remove(words.size() - 1);
                return top;
            }
            else return null;
        }
    }

    @Test
    void testPicksSpecifiedCountOfWordsFromWordUrn() {
        ArrayList<String> fixture = new ArrayList<>(Arrays.asList("a","b","c"));
        FakeWordUrn urn = new FakeWordUrn(fixture);
        Remixer remixer = new Remixer(urn);

        String shortUrl = remixer.shorten("http://home.xy", 3);

        assertThat(shortUrl.split("-").length).isEqualTo(3);
        assertThat(shortUrl).isEqualTo("c-b-a"); // since we make the implementation in the fake to be deterministic
    }

}

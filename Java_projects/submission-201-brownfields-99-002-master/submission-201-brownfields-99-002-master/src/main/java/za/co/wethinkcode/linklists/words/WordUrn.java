package za.co.wethinkcode.linklists.words;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Interface for objects that can magically produce a word
// An Urn is another name for a jar.
public interface WordUrn {
    WordsModel get(String id) throws SQLException;

    String giveWord();

}

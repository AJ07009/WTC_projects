package za.co.wethinkcode.linklists.links;

// Extend/complete this
public interface LinkRepository {
    boolean exists(String shortUrl);
    String getMappedLongUrl(String shortUrl);
}

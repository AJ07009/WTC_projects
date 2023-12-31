package za.co.wethinkcode.Utility;

import org.json.simple.JSONObject;

@SuppressWarnings({"unchecked", "unused"})
public class ResponseBuilder {
    JSONObject response;

    /**
     * Constructs a new JsonResponseBuilder and adds the data field.
     */
    public ResponseBuilder(){
        response = new JSONObject();
        response.put("data", null);
    }

    /**
     * Adds the given key and object to the current response.
     * @param key of value.
     * @param value;
     */
    public void add(String key, Object value) {
        response.put(key, value);
    }

    /**
     * Returns the value at the key given. If the value is null a empty string is returned.
     * @param key of value.
     * @return value at key or empty string if value is null.
     */
    public String getValue(String key) {
        if (response.get(key) == null) {
            return "";
        }
        else return response.get(key).toString();
    }

    /**
     * Add the given Object to the data field of the response. This will either be a JSONObject or a JSONArray.
     * @param data to insert.
     */
    public void addData(Object data) {
        this.response.put("data", data);
    }

    /**
     * Override for toString method this will create a JSONString instead.
     * @return JSONString.
     */
    @Override
    public String toString(){
        return this.response.toJSONString();
    }
}

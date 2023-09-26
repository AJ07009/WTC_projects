package za.co.wethinkcode.linklists.motd;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MotdResponse {
    private String motd;

    @JsonCreator
    public MotdResponse(String motd){
        this.motd = motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public String getMotd() {
        return motd;
    }


}

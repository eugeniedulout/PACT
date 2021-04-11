package com.example.testmenu;

import org.json.JSONException;
import org.json.JSONObject;

public class Market {
    private int marketId;
    private String marketName;
    private String marketLogoUrl;
    private String openHour;
    private String closeHour;

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketLogoUrl() {
        return marketLogoUrl;
    }

    public void setMarketLogoUrl(String marketLogoUrl) {
        this.marketLogoUrl = marketLogoUrl;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    public Market(int marketId, String marketName, String marketLogoUrl, String openHour, String closeHour) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketLogoUrl = marketLogoUrl;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public Market(JSONObject json_market) {
        try {
            this.marketId = json_market.getInt("market_id");
            this.marketName = json_market.getString("name");
            this.marketLogoUrl = json_market.getString("logo");
            this.openHour = json_market.getString("open_hours");
            this.closeHour = json_market.getString("close_hours");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public JSONObject toJSON() {
        JSONObject json_market = new JSONObject();
        try {
            json_market.put("market_id", this.marketId);
            json_market.put("name", this.marketName);
            json_market.put("logo", this.marketLogoUrl);
            json_market.put("open_hours", this.openHour);
            json_market.put("close_hours", this.closeHour);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json_market;
    }


}

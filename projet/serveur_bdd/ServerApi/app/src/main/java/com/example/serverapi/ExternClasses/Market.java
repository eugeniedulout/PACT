package com.example.serverapi.ExternClasses;

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
}

package com.example.testmenu;

public class Market {
    private String marketName;
    private String marketLogo;
    private String openHour;
    private String closeHour;
    private int marketId;

    public Market(int marketId, String marketName, String marketLogo,String openHour, String closeHour) {
        this.marketName = marketName;
        this.marketLogo = marketLogo;
        this.marketId = marketId;
        this.openHour = openHour;
        this.closeHour  = closeHour;
    }

    public int getMarketId() {
        return marketId;
    }

    public String getOpenHour() {
        return openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getMarketLogo() {
        return marketLogo;
    }

    public void setMarketLogo(String marketLogo) {
        this.marketLogo = marketLogo;
    }
}

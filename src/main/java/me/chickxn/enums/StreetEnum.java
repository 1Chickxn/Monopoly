package me.chickxn.enums;

import org.bukkit.entity.Player;

public enum StreetEnum {

    BADSTRASSE(60, 1, null),
    TURMSTRASSE(60, 3, null),
    SÜDBAHNHOF(200, 5, null),
    CHAUSSESTRASSE(100, 6, null),
    ELISENSTRASSE(100, 8, null),
    POSTSTRASSE(120, 9, null),
    SEESTRASSE(140, 11, null),
    ELEKTRIZITÄTSWERK(150, 12, null),
    HAFENSTRASSE(140, 13, null),
    NEUESTRASSE(160, 14, null),
    WESTBAHNHOF(200, 15, null),
    MÜNCHENERSTRASSE(180, 16, null),
    WIENERSTRASSE(180, 18, null),
    BERLINERSTRASSE(200, 19, null),
    THEATERSTRASSE(220, 21, null),
    MUSEUMSTRASSE(220, 23, null),
    OPERNPLATZ(240, 24, null),
    NORDBAHNHOF(200, 25, null),
    LESSINGSTRASSE(260, 26, null),
    SCHILLERSTRASSE(260, 27, null),
    WASSERWERK(150, 28, null),
    GOETHESTRASSE(280, 29, null),
    RATHAUSPLATZ(300, 31, null),
    HAUPTSTRASSE(300, 32, null),
    BAHNHOFSTRASSE(320, 34, null),
    HAUPTBAHNHOF(200, 35, null),
    PARKSTRASSE(350, 37, null),
    SCHLOSSALLEE(400, 39, null)
    ;

    private int price;
    private int number;
    private Player player;


    StreetEnum(int price, int number, Player player) {
        this.price = price;
        this.number = number;
        this.player = player;
    }

    public int getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

package me.chickxn.handler;

import me.chickxn.enums.MonopolyFigureEnum;
import org.bukkit.entity.Player;

public class MonopolyPlayerHandler {

    private Player player;
    private MonopolyFigureEnum monopolyFigureEnum;

    public MonopolyPlayerHandler(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MonopolyFigureEnum getSelectedFigure() {
        return monopolyFigureEnum;
    }

    public void setSelectedFigure(MonopolyFigureEnum selectedFigure) {
        this.monopolyFigureEnum = selectedFigure;
    }
}
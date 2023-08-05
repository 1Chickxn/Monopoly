package me.chickxn.listener;

import me.chickxn.Monopoly;
import me.chickxn.enums.GameStateEnum;
import me.chickxn.enums.MonopolyFigureEnum;
import me.chickxn.handler.MonopolyPlayerHandler;
import me.chickxn.inventorys.FigureInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {
        Player player = playerQuitEvent.getPlayer();
        int onlinePlayers = (Bukkit.getOnlinePlayers().size() - 1);
        MonopolyFigureEnum monopolyFigureEnum = FigureInventory.getSelectedFigure(player);
        FigureInventory.selectedFigures.remove(monopolyFigureEnum);
        if (Monopoly.getInstance().getGameStateHandler().getGameStateEnum().equals(GameStateEnum.LOBBY)) {
            if (onlinePlayers < 2) {
                Monopoly.getInstance().getGameStateHandler().stopCountdown();
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(Monopoly.getInstance().getPrefix() + "Das Spiel wurde pausiert§8!");
                }
            }
        }
    }
}

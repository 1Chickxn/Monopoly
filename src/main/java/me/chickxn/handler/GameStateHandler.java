package me.chickxn.handler;

import me.chickxn.Monopoly;
import me.chickxn.enums.GameStateEnum;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameStateHandler {

    private GameStateEnum gameStateEnum;
    private static final int countdownSeconds = 30;
    private Timer timer;
    private int countdownRemaining;
    private boolean isCountdownRunning = false;


    public GameStateHandler() {
        this.gameStateEnum = GameStateEnum.LOBBY;
    }

    public void setGameStateEnum(GameStateEnum gameStateEnum) {
        this.gameStateEnum = gameStateEnum;
    }

    public GameStateEnum getGameStateEnum() {
        return gameStateEnum;
    }

    public void startCountdown() {
        if (isCountdownRunning) {
            return;
        }

        if (timer != null) {
            timer.cancel();
        }

        countdownRemaining = countdownSeconds;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (countdownRemaining <= 0) {
                    timer.cancel();
                    timer = null;
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.sendMessage(Monopoly.getInstance().getPrefix() + "Das Spiel startet jetzt§8!");
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                onlinePlayer.getInventory().clear();
                                onlinePlayer.teleport(Monopoly.getInstance().getLocationHandler().getLocation("FIELD_LOS"));
                            }
                            Monopoly.getInstance().getGameEngine().startGame();
                        }
                    }.runTask(Monopoly.getInstance());
                    Monopoly.getInstance().getGameStateHandler().setGameStateEnum(GameStateEnum.INGAME);
                } else {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        onlinePlayer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Monopoly.getInstance().getPrefix() + "Das Spiel startet in §c" + countdownRemaining + " §7Sekunden."));
                    }
                    countdownRemaining--;
                }
            }
        }, 0, 1000);
        isCountdownRunning = true;
    }

    public void stopCountdown() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            isCountdownRunning = false;
        }
    }

    public void shortCountdown(int seconds) {
        countdownRemaining = seconds;
        if (countdownRemaining < 0) {
            countdownRemaining = 0;
        }
    }

    public int getSeconds() {
        return countdownRemaining;
    }
}

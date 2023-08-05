package me.chickxn;

import lombok.Getter;
import me.chickxn.commands.SetupCommand;
import me.chickxn.commands.StartCommand;
import me.chickxn.engine.GameEngine;
import me.chickxn.enums.GameStateEnum;
import me.chickxn.handler.GameStateHandler;
import me.chickxn.handler.LocationHandler;
import me.chickxn.handler.MonopolyPlayerHandler;
import me.chickxn.handler.PlayerHandler;
import me.chickxn.inventorys.FigureInventory;
import me.chickxn.listener.PlayerJoinListener;
import me.chickxn.listener.PlayerQuitListener;
import me.chickxn.listener.ServerListPingListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Monopoly extends JavaPlugin {

    @Getter
    private static Monopoly instance;

    @Getter
    private GameStateHandler gameStateHandler;

    @Getter
    private LocationHandler locationHandler;

    @Getter
    private PlayerHandler playerHandler;

    @Getter
    private GameEngine gameEngine;

    @Getter
    private String prefix = "§8» §cMonopoly §8| §7";

    @Override
    public void onEnable() {
        instance = this;
        this.locationHandler = new LocationHandler();
        this.playerHandler = new PlayerHandler();
        this.gameEngine = new GameEngine(this);
        this.gameStateHandler = new GameStateHandler();
        this.gameStateHandler.setGameStateEnum(GameStateEnum.LOBBY);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new FigureInventory(), this);
        pluginManager.registerEvents(new ServerListPingListener(), this);
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("start").setExecutor(new StartCommand());
    }

    @Override
    public void onDisable() {

    }
}

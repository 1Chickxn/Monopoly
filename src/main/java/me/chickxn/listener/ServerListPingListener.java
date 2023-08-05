package me.chickxn.listener;

import me.chickxn.Monopoly;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPlayerPing(ServerListPingEvent serverListPingEvent) {
        serverListPingEvent.setMotd(Monopoly.getInstance().getPrefix() + Monopoly.getInstance().getGameStateHandler().getGameStateEnum());
        serverListPingEvent.setMaxPlayers(8);
    }
}

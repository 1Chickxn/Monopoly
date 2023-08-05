package me.chickxn.listener;

import me.chickxn.Monopoly;
import me.chickxn.enums.GameStateEnum;
import me.chickxn.handler.GameStateHandler;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        Monopoly.getInstance().getPlayerHandler().setInventory(player);
        player.sendMessage(Monopoly.getInstance().getPrefix() + "Willkommen bei §cMonopoly");
        player.sendMessage(Monopoly.getInstance().getPrefix() + "Du weißt nicht wie Monopoly funktioniert§8?");
        TextComponent textComponent = new TextComponent(Monopoly.getInstance().getPrefix() + "Hier §cklicken §7für ein Tutorial§8!");
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Klick mich um das §cTutorial §7anzuschauen")));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://youtube.com/minecraft-monopoly/"));
        player.spigot().sendMessage(textComponent);
        player.teleport(Monopoly.getInstance().getLocationHandler().getLocation("SPAWN"));
        if (Bukkit.getOnlinePlayers().size() > 1) {
            if (!Monopoly.getInstance().getGameStateHandler().getGameStateEnum().equals(GameStateEnum.INGAME)) {
                Monopoly.getInstance().getGameStateHandler().startCountdown();
            }else{
                player.kickPlayer(Monopoly.getInstance().getPrefix() + "Das spiel ist bereits gestartet§8!");
            }
        }
    }
}

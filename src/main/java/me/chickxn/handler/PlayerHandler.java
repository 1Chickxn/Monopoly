package me.chickxn.handler;

import me.chickxn.Monopoly;
import me.chickxn.builder.ItemBuilder;
import me.chickxn.enums.GameStateEnum;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerHandler {

    public void setInventory(Player player) {
        if (Monopoly.getInstance().getGameStateHandler().getGameStateEnum().equals(GameStateEnum.LOBBY)) {
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            player.getInventory().setItem(4, new ItemBuilder(Material.FIREWORK_STAR).setDisplayname("§8» §cFiguren §8| §7§oRechtsklick").build());
        }else{
            player.getInventory().setItem(8, new ItemBuilder(Material.ENDER_EYE).setDisplayname("§8» §cStraßen §8| §7§oRechtsklick").build());
        }
    }
}

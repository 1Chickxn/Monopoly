package me.chickxn.engine;

import me.chickxn.Monopoly;
import me.chickxn.builder.ItemBuilder;
import me.chickxn.enums.StreetEnum;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameEngine implements Listener {

    private Random random;
    private List<Player> dicePlayer;
    public HashMap<Player, Integer> currentField;

    public GameEngine(Plugin plugin) {
        this.dicePlayer = new ArrayList<>();
        this.random = new Random();
        this.currentField = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void startGame() {
        dicePlayer.clear();
        for (Player player : Bukkit.getOnlinePlayers()) {
            currentField.put(player, 0);
            Monopoly.getInstance().getPlayerHandler().setInventory(player);
        }
        startNextTurn();
    }

    private void startNextTurn() {
        if (dicePlayer.isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                dicePlayer.add(player);
            }
        }
        Player player = dicePlayer.get(0);
        player.sendMessage(Monopoly.getInstance().getPrefix() + "Dein Zug beginnt!");
        player.getInventory().addItem(new ItemBuilder(Material.SHULKER_BOX).setDisplayname("§8» §cWürfel §8| §7").build());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (isDieItem(event.getItem())) {
                int rollResult = rollDice();
                player.sendMessage(Monopoly.getInstance().getPrefix() + "Du hast eine " + rollResult + " gewürfelt!");
                int currentPosition = currentField.getOrDefault(player, 0);
                currentField.put(player, (currentPosition + rollResult));
                if (currentField.get(player).intValue() >= 39) {
                    player.sendMessage(Monopoly.getInstance().getPrefix() + "Du hast §c200€ §7bekommen§8!");
                    currentField.put(player, (currentField.get(player) - 39));
                }
                player.sendMessage(Monopoly.getInstance().getPrefix() + "Du bist auf Feld " + currentField.get(player));
                player.getInventory().removeItem(player.getItemInHand());

                if (currentField.get(player).intValue() == 0) {
                    player.teleport(Monopoly.getInstance().getLocationHandler().getLocation("FIELD_LOS"));
                }else{
                    player.teleport(Monopoly.getInstance().getLocationHandler().getLocation("FIELD_" + currentField.get(player)));
                }

                StreetEnum currentStreet = getCurrentStreet(player);

                if (currentStreet != null) {
                    int currentPrice = currentStreet.getPrice();
                    Player owner = currentStreet.getPlayer();
                    if (owner == null) {
                        currentStreet.setPlayer(player);
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "Du hast die §c" + currentStreet.name() + " §7für §c" + currentPrice + " §7gekauft§8!");
                    }else if(owner == player) {
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "Die Straße §c" + currentStreet.name() + " §7gehört die bereits§8!!");
                    }else{
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "Die Straße §c" + currentStreet.name() + " §7gehört bereits §c" + owner.getName() + "§8!");
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "Du zahlst nun an §c" + owner.getName() + " §7eine Gebühr von §c{STREET_COST}§8!");
                        owner.sendMessage(Monopoly.getInstance().getPrefix() + "Der Spieler §c" + player.getName() + " §7ist auf deiner Straße gelandet§8!");
                        owner.sendMessage(Monopoly.getInstance().getPrefix() + "Dir wurden §c{STREET_COST_PAID} §7gutgeschrieben§8!");
                    }
                }
                dicePlayer.remove(player);
                startNextTurn();
                event.setCancelled(true);
            }
        }
    }

    private boolean isDieItem(ItemStack item) {
        return item != null && item.getType() == Material.SHULKER_BOX && item.hasItemMeta()
                && item.getItemMeta().getDisplayName().equals("§8» §cWürfel §8| §7");
    }

    private int rollDice() {
        int result;
        do {
            result = random.nextInt(12) + 1;
        } while (result == 1);
        return result;
    }

    public StreetEnum getCurrentStreet(Player player) {
        int currentPosition = currentField.getOrDefault(player, 0);
        for (StreetEnum street : StreetEnum.values()) {
            if (street.getNumber() == currentPosition) {
                return street;
            }
        }
        return null;
    }
}

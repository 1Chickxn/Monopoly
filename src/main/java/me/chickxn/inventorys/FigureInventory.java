package me.chickxn.inventorys;

import me.chickxn.Monopoly;
import me.chickxn.builder.ItemBuilder;
import me.chickxn.creator.InventoryCreator;
import me.chickxn.enums.MonopolyFigureEnum;
import me.chickxn.handler.MonopolyPlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class FigureInventory implements Listener {

    public static Map<MonopolyFigureEnum, String> selectedFigures = new HashMap<>();

    public void openInventory(Player player) {
        InventoryCreator inventoryCreator = InventoryCreator.createInventory(Monopoly.getInstance().getPrefix() + "Figuren", 4 * 9);
        inventoryCreator.setItems(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname(" ").build(), 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26,27, 28, 29,30,31,32,33,34,35);

        List<MonopolyFigureEnum> figures = Arrays.asList(MonopolyFigureEnum.values());
        Collections.shuffle(figures);

        int currentSlot = 10;
        for (MonopolyFigureEnum figure : figures) {
            while (inventoryCreator.getInventory().getItem(currentSlot) != null) {
                currentSlot++;
                if (currentSlot >= 26) {
                    break;
                }
            }
            if (currentSlot >= 26) {
                break;
            }
            Material material = getMaterialForFigure(figure);
            if (selectedFigures.containsKey(figure)) {
                inventoryCreator.setItem(new ItemBuilder(material).setDisplayname("§8» §c" + figure.toString() + " §8| §7§oLinksklick").setLore("§8» §c" + selectedFigures.get(figure)).build(), currentSlot);
            } else {
                inventoryCreator.setItem(new ItemBuilder(material).setDisplayname("§8» §c" + figure.toString() + " §8| §7§oLinksklick").setLore("§8» §cX").build(), currentSlot);
            }
            currentSlot++;
            currentSlot++;
        }

        player.openInventory(inventoryCreator.getInventory());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        if (playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK) || playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (playerInteractEvent.getMaterial() == Material.FIREWORK_STAR) {
                this.openInventory(player);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (!event.getInventory().equals(player.getOpenInventory().getTopInventory())) {
            return;
        }
        if (event.getView().getTitle().equals(Monopoly.getInstance().getPrefix() + "Figuren")) {
            if (event.getRawSlot() >= 0 && event.getRawSlot() <= 35) {
                event.setCancelled(true);
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.getItemMeta() == null) {
                    return;
                }
                ItemMeta itemMeta = clickedItem.getItemMeta();
                if (itemMeta.getLore() == null || itemMeta.getLore().isEmpty()) {
                    return;
                }
                String lore = itemMeta.getLore().get(0);
                if (lore.startsWith("§8» §cX")) {
                    String figureName = itemMeta.getDisplayName().replace("§8» §c", "").replace(" §8| §7§oLinksklick", "");
                    MonopolyFigureEnum figure = MonopolyFigureEnum.valueOf(figureName);
                    if (isFigureAvailable(figure)) {
                        MonopolyFigureEnum oldFigure = getSelectedFigure(player);
                        if (oldFigure != null) {
                            selectedFigures.remove(oldFigure);
                        }
                        new MonopolyPlayerHandler(player).setSelectedFigure(figure);
                        selectedFigures.put(figure, player.getName());
                        player.sendMessage(Monopoly.getInstance().getPrefix() + "Du hast die Figur §c" + figure.toString() + " §7ausgewählt!");
                        player.closeInventory();
                    } else {
                        player.sendMessage("Diese Figur wurde bereits ausgewählt!");
                    }
                }
            }
        }
    }

    private boolean isFigureAvailable(MonopolyFigureEnum figure) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            MonopolyPlayerHandler playerHandler = new MonopolyPlayerHandler(onlinePlayer);
            if (playerHandler.getSelectedFigure() == figure) {
                return false;
            }
        }
        return true;
    }


    private Material getMaterialForFigure(MonopolyFigureEnum figure) {
        switch (figure) {
            case HUT:
                return Material.LEATHER_HELMET;
            case SCHUBKARRE:
                return Material.BARREL;
            case SCHIFF:
                return Material.OAK_BOAT;
            case SCHUH:
                return Material.LEATHER_BOOTS;
            case HUND:
                return Material.WOLF_SPAWN_EGG;
            case FINGERHUT:
                return Material.CAULDRON;
            case KATZE:
                return Material.COD_BUCKET;
            case AUTO:
                return Material.MINECART;
            default:
                return Material.BARRIER;
        }
    }

    public static MonopolyFigureEnum getSelectedFigure(Player player) {
        for (Map.Entry<MonopolyFigureEnum, String> entry : selectedFigures.entrySet()) {
            if (entry.getValue().equals(player.getName())) {
                return entry.getKey();
            }
        }
        return null;
    }
}

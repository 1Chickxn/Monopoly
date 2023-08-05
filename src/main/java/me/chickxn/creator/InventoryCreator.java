package me.chickxn.creator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryCreator {

    private Inventory inventory;

    public static InventoryCreator createInventory(String name, int size) {
        InventoryCreator inventoryCreator = new InventoryCreator();
        inventoryCreator.inventory = Bukkit.createInventory(null, size, name);

        return inventoryCreator;
    }

    public InventoryCreator setItems(ItemStack itemStack, Integer... integers) {
        for(int i = 0; i < integers.length; i++) {
            inventory.setItem(integers[i], itemStack);
        }
        return this;
    }

    public InventoryCreator setItem(ItemStack itemStack, Integer integer) {
        inventory.setItem(integer, itemStack);
        return this;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
package me.chickxn.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;

    private final ItemMeta itemMeta;

    public ItemBuilder(Material mat) {
        this.itemStack = new ItemStack(mat);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material mat, int subid) {
        this.itemStack = new ItemStack(mat, 1, (byte) subid);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayname(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addLore(String lore) {
        List<String> targetLore = this.itemMeta.getLore();
        if (targetLore == null)
            targetLore = new ArrayList<>();
        targetLore.add(lore);
        this.itemMeta.setLore(targetLore);
        return this;
    }

    public ItemBuilder addLore(ArrayList<String> lore) {
        List<String> targetLore = this.itemMeta.getLore();
        if (targetLore == null)
            targetLore = new ArrayList<>();
        targetLore.addAll(lore);
        this.itemMeta.setLore(targetLore);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        List<String> targetLore = this.itemMeta.getLore();
        if (targetLore == null)
            targetLore = new ArrayList<>();
        targetLore.addAll(Arrays.asList(lore));
        this.itemMeta.setLore(targetLore);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.itemMeta.removeEnchant(enchantment);
        return this;
    }

    public ItemBuilder hideEnchantments() {
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        this.itemMeta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag... flag) {
        this.itemMeta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder showEnchantments() {
        this.itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag flag) {
        this.itemMeta.removeItemFlags(flag);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag... flag) {
        this.itemMeta.removeItemFlags(flag);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
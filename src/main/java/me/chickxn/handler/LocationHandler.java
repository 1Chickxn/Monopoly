package me.chickxn.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationHandler {

    public static File folder;
    public static File file;
    public static YamlConfiguration cfg;

    static {
        folder = new File("plugins/Monopoly-1.0-SNAPSHOT/");
        file = new File("plugins/Monopoly-1.0-SNAPSHOT/location.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void setupFiles() {
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveLocations() {
        try {
            cfg.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public double getSpawnHeight() {
        return cfg.getDouble("Locations.Spawn.Y");
    }

    public double getHeight() {
        return cfg.getDouble("Locations.Height");
    }

    public void setLocation(final String name, final Location loc) {
        final double x = loc.getBlockX() + 0.5;
        final double y = loc.getBlockY();
        final double z = loc.getBlockZ() + 0.5;
        final double yaw = Math.round(loc.getYaw() / 45.0f) * 45;
        final double pitch = Math.round(loc.getPitch() / 45.0f) * 45;
        final String worldName = loc.getWorld().getName();
        cfg.set("Locations." + name + ".X", x);
        cfg.set("Locations." + name + ".Y", y);
        cfg.set("Locations." + name + ".Z", z);
        cfg.set("Locations." + name + ".Yaw", yaw);
        cfg.set("Locations." + name + ".Pitch", pitch);
        cfg.set("Locations." + name + ".worldName", worldName);
        saveLocations();
    }

    public void setHeight(final String name, final double height) {
        cfg.set("Locations." + name, height);
        saveLocations();
    }

    public void removeLocation(final String name) {
        final String path = "Locations." + name;
        cfg.set(path, null);
        saveLocations();
    }

    public void removeMap(final int name) {
        final String path = "Map." + name;
        cfg.set(path, null);
        saveLocations();
    }

    public boolean locationIsExisting(final String name) {
        return cfg.get("Locations." + name) != null;
    }

    public Location getLocation(final String name) {
        final double x = cfg.getDouble("Locations." + name + ".X");
        final double y = cfg.getDouble("Locations." + name + ".Y");
        final double z = cfg.getDouble("Locations." + name + ".Z");
        final double yaw = cfg.getDouble("Locations." + name + ".Yaw");
        final double pitch = cfg.getDouble("Locations." + name + ".Pitch");
        final String worldName = cfg.getString("Locations." + name + ".worldName");
        final Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
        loc.setYaw((float) yaw);
        loc.setPitch((float) pitch);
        return loc;
    }
}

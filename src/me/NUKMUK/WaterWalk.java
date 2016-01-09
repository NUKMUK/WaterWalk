package me.NUKMUK;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class WaterWalk extends JavaPlugin implements Listener {

    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getConfig().options().copyDefaults(true);
    }

    private static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo().getBlock().getLocation().add(0, -1, 0).getBlock().getType() == Material.STATIONARY_WATER) {
            for (Block b : getNearbyBlocks(e.getPlayer().getLocation(), getConfig().getInt("radius"))) {
                if (b.getLocation().getY() < e.getPlayer().getLocation().getY() && b.getLocation().getY() > e.getPlayer().getLocation().add(0, -2, 0).getY()) {
                    if (b.getType() == Material.STATIONARY_WATER) {
                        if (Math.random() < 0.5) {
                            e.getPlayer().sendBlockChange(e.getTo().getBlock().getLocation().add(0, -1, 0), Material.STAINED_CLAY, (byte) (Math.random() * 16));
                            e.getPlayer().sendBlockChange(e.getTo().getBlock().getLocation().add(0, -2, 0), Material.STAINED_CLAY, (byte) (Math.random() * 16));
                            if ((b.getLocation() != e.getPlayer().getLocation())) {
                                e.getPlayer().sendBlockChange(b.getLocation(), Material.STAINED_GLASS, (byte) (Math.random() * 15 + 1));
                            }
                        }
                    }
                }
            }
        }
    }
}

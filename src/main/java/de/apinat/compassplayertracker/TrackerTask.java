package de.apinat.compassplayertracker;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class TrackerTask extends BukkitRunnable {
    private final Player player;

    public TrackerTask(UUID player) {
        this.player = Bukkit.getPlayer(player);
    }

    @Override
    public void run() {
        if (player.getWorld().getEnvironment() != World.Environment.NORMAL) return;

        ArrayList<Player> trackedPlayers = new ArrayList<>();
        try {
            for (Player player : player.getWorld().getPlayers()) {
                if (!player.getUniqueId().equals(this.player.getUniqueId())
                        && player.getGameMode() != GameMode.SPECTATOR
                        && !player.isSneaking()) {
                    trackedPlayers.add(player);
                }
            }
        } catch (Exception ignored) {
        }

        trackedPlayers.sort(new TrackerComparator(this.player));

        String message;
        if (!trackedPlayers.isEmpty()) {
            Player nearest = trackedPlayers.get(0);
            assert nearest != null;
            player.setCompassTarget(nearest.getLocation());
            message = "§f§lNearest Player: §e" + nearest.getName();
            message += "   " + "§f§lDistance: §e" + String.format("%.2f", nearest.getLocation().distance(player.getLocation()));
//            this.message += "   " + "§f§lHeight: §e" + String.format("%.2f", nearest.getLocation().getY() - player.getLocation().getY());
        } else {
            message = "";
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            player.sendActionBar(message);
        }
    }
}

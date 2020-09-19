package de.apinat.compassplayertracker;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class CompassPlayerTracker extends JavaPlugin implements Listener {
    HashMap<UUID, TrackerTask> playerTasks = new HashMap<>();

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        createTask(e.getPlayer().getUniqueId());
    }

//    @EventHandler
//    public void onPlayerSpawn(PlayerRespawnEvent e) {
//        e.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
//    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        createTask(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (this.playerTasks.containsKey(e.getPlayer().getUniqueId()))
            this.playerTasks.remove(e.getPlayer().getUniqueId()).cancel();
    }

    private void createTask(UUID player) {
        if (this.playerTasks.containsKey(player)) return;
        TrackerTask trackerTask = new TrackerTask(player);
        trackerTask.runTaskTimer(this, 0L, 5L);
        this.playerTasks.put(player, trackerTask);
    }
}

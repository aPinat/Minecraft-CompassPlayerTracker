package de.apinat.compassplayertracker;

import org.bukkit.entity.Player;

import java.util.Comparator;

public class TrackerComparator implements Comparator<Player> {
    private final Player player;

    public TrackerComparator(Player p) {
        this.player = p;
    }

    @Override
    public int compare(Player p1, Player p2) {
        return this.compare(p2.getLocation().distance(player.getLocation()), p1.getLocation().distance(player.getLocation()));
    }

    private int compare(double a, double b) {
        return a > b ? -1 : (a > b ? 1 : 0);
    }
}

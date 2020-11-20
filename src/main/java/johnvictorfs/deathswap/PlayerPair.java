package johnvictorfs.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerPair {
    public Player first;
    public Player second;

    public PlayerPair(Player firstPlayer, Player secondPlayer) {
        this.first = firstPlayer;
        this.second = secondPlayer;
    }

    public String toString() {
        return "(" + this.first.getName() + ", " + this.second.getName() + ")";
    }

    public void swapPlaces() {
        Location firstLocation = this.first.getLocation();
        Location secondLocation = this.second.getLocation();

        this.first.teleport(secondLocation);
        this.first.setVelocity(new org.bukkit.util.Vector(0, 0, 0));
        this.first.setFallDistance(0f);

        this.second.teleport(firstLocation);
        this.second.setVelocity(new org.bukkit.util.Vector(0, 0, 0));
        this.second.setFallDistance(0f);

        Bukkit.broadcastMessage(ChatColor.YELLOW + "Swapped " + this.first.getName() + " with " + this.second.getName());
    }
}

package johnvictorfs.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ChatCountdown {
    private final DeathSwap plugin;
    int taskID;
    int secondsCountdown;

    public ChatCountdown(DeathSwap plugin, int secondsCountdown) {
        this.plugin = plugin;
        this.secondsCountdown = secondsCountdown;
    }

    public void startSecondsCountdown() {
        final int startCountdown = this.secondsCountdown;

        taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (this.secondsCountdown != 0) {
                Bukkit.broadcastMessage(ChatColor.BLUE + "Swapping players in " + this.secondsCountdown + "...");
            } else {
                plugin.getServer().getScheduler().cancelTask(taskID);
                this.secondsCountdown = startCountdown;
            }

            this.secondsCountdown--;
        }, 20L, 20L);

    }
}

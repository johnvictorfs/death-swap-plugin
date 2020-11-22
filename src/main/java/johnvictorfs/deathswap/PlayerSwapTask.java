package johnvictorfs.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerSwapTask implements Runnable {
    private final DeathSwap plugin;

    public PlayerSwapTask(DeathSwap plugin) {
        this.plugin = plugin;
    }

    public void waitForCountdown(int seconds) {
        ChatCountdown countdown = new ChatCountdown(plugin, seconds);
        countdown.startSecondsCountdown();

        while (Bukkit.getScheduler().isQueued(countdown.taskID)) ;
    }

    public void run() {
        this.waitForCountdown(10);

        if (Bukkit.getOnlinePlayers().size() < 2) {
            Bukkit.broadcastMessage(ChatColor.RED + "Not enough players in the server to swap.");
            return;
        }

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this::swapPlayers);
    }

    void swapPlayers() {
        Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
        Bukkit.getOnlinePlayers().toArray(players);

        List<PlayerPair> pairs = this.playerPairs(players);

        pairs.forEach(PlayerPair::swapPlaces);

        Bukkit.broadcastMessage(ChatColor.BLUE + "Finished swapping players.");
    }

    List<PlayerPair> playerPairs(Player[] players) {
        List<Player> playersList = Arrays.asList(players);
        Collections.shuffle(playersList);

        Iterator<Player> playersIterator = playersList.iterator();

        List<PlayerPair> pairs = new ArrayList<>();
        while(playersIterator.hasNext()) {
            Player first = playersIterator.next();
            if (!playersIterator.hasNext()) break;

            PlayerPair pair = new PlayerPair(first, playersIterator.next());
            pairs.add(pair);
        }

        return pairs;
    }

    String playerName(Player player, ChatColor color) {
        return ChatColor.BLUE + player.getName() + color;
    }

    String playerName(Player player) {
        return ChatColor.BLUE + player.getName() + ChatColor.GREEN;
    }
}

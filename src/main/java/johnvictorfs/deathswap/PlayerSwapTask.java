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
        List<Player> playersListCopy = Arrays.asList(Arrays.copyOf(players, players.length));
        Collections.reverse(playersListCopy);

        Iterator<Player> player1 = playersList.iterator();
        Iterator<Player> player2 = playersListCopy.iterator();

        List<PlayerPair> pairs = new ArrayList<>();
        Set<UUID> alreadyPaired = new HashSet<>();
        while (player1.hasNext() && player2.hasNext()) {
            Player first = player1.next();
            Player second = player1.next();

            if (alreadyPaired.contains(first.getUniqueId()) || alreadyPaired.contains(second.getUniqueId())) continue;

            alreadyPaired.add(first.getUniqueId());
            alreadyPaired.add(second.getUniqueId());

            pairs.add(new PlayerPair(first, second));
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

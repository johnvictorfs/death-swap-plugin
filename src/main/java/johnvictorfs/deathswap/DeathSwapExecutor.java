package johnvictorfs.deathswap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;


public class DeathSwapExecutor implements CommandExecutor {
    private final DeathSwap plugin;
    private final Runnable swapPlayers;
    private BukkitTask swapPlayersTask;
    private int swapPlayersTaskId = 0;

    public DeathSwapExecutor(DeathSwap plugin) {
        this.plugin = plugin;
        this.swapPlayers = new PlayerSwapTask(plugin);
    }

    /**
     * Start or Stop Death swap every X minutes (default is 5)
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        if (args[0].equalsIgnoreCase("start")) {
            return this.startSwapTask(args);
        } else if (args[0].equalsIgnoreCase("stop")) {
            return this.stopSwapTask();
        } else if (args[0].equalsIgnoreCase("now")) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, this.swapPlayers);
            return true;
        }

        return false;
    }

    private boolean startSwapTask(String[] args) {
        if (Bukkit.getScheduler().isQueued(this.swapPlayersTaskId)) {
            Bukkit.broadcastMessage(ChatColor.RED + "This task is already running. Stop it first if you want to re-run it.");
            return true;
        }

        int secondsPeriod = 300;
        if (args.length > 1) {
            try {
                secondsPeriod = Integer.parseInt(args[1]) * 60;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        this.swapPlayersTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, this.swapPlayers, secondsPeriod * 20, secondsPeriod * 20);
        this.swapPlayersTaskId = this.swapPlayersTask.getTaskId();

        int minutes = secondsPeriod / 60;

        Bukkit.broadcastMessage(ChatColor.GREEN + "Started Death Swap every " + minutes + pluralize(" minute", "s", minutes) + ".");

        return true;
    }

    String pluralize(String singular, String end, int count) {
        return count > 1 ? singular + end : singular;
    }

    private boolean stopSwapTask() {
        if (Bukkit.getScheduler().isQueued(this.swapPlayersTaskId)) {
            this.swapPlayersTask.cancel();
            Bukkit.broadcastMessage(ChatColor.RED + "Stopped Death Swap.");
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "This task is not running already.");
        }

        return true;
    }
}
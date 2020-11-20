package johnvictorfs.deathswap;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class DeathSwap extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Started Death Swap plugin");

        Objects.requireNonNull(this.getCommand("death_swap")).setExecutor(new DeathSwapExecutor(this));
        Objects.requireNonNull(this.getCommand("death_swap")).setTabCompleter(new DeathSwapTabComplete());
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
        getLogger().info("Stopped Death Swap plugin");
    }
}

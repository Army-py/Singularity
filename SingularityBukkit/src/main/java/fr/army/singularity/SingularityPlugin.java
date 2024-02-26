package fr.army.singularity;

import org.bukkit.plugin.java.JavaPlugin;

public class SingularityPlugin extends JavaPlugin {

    public static SingularityPlugin plugin;

    public void onEnable() {
        plugin = this;

        getLogger().info("SingularityPlugin has been enabled!");
    }

    public void onDisable() {
        getLogger().info("SingularityPlugin has been disabled!");
    }
}

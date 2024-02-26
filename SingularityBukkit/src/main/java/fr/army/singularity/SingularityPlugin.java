package fr.army.singularity;

import fr.army.singularity.network.channel.ChannelRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class SingularityPlugin extends JavaPlugin {

    public static SingularityPlugin plugin;

    private ChannelRegistry channelRegistry;

    public void onEnable() {
        plugin = this;

        channelRegistry = new ChannelRegistry();
        channelRegistry.register(this);

        getLogger().info("SingularityPlugin has been enabled!");
    }

    public void onDisable() {
        channelRegistry.unregister(this);

        getLogger().info("SingularityPlugin has been disabled!");
    }
}

package fr.army.singularity;

import fr.army.singularity.listener.ListenerLoader;
import fr.army.singularity.network.channel.ChannelRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class SingularityPlugin extends JavaPlugin {

    public static SingularityPlugin plugin;

    private ChannelRegistry channelRegistry;
    private ListenerLoader listenerLoader;

    public void onEnable() {
        plugin = this;

        channelRegistry = new ChannelRegistry();
        channelRegistry.register(this);

        listenerLoader = new ListenerLoader();
        listenerLoader.registerListeners(this);

        getLogger().info("SingularityPlugin has been enabled!");
    }

    public void onDisable() {
        channelRegistry.unregister(this);

        getLogger().info("SingularityPlugin has been disabled!");
    }

    public static SingularityPlugin getPlugin() {
        return plugin;
    }

    public ChannelRegistry getChannelRegistry() {
        return channelRegistry;
    }

    public ListenerLoader getListenerLoader() {
        return listenerLoader;
    }
}

package fr.army.singularity;

import fr.army.singularity.config.Config;
import fr.army.singularity.config.ConfigLoader;
import fr.army.singularity.listener.ListenerLoader;
import fr.army.singularity.network.channel.ChannelRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;

public class SingularityPlugin extends JavaPlugin {

    public static SingularityPlugin plugin;

    private ChannelRegistry channelRegistry;
    private ConfigLoader configLoader;
    private Config config;
    private ListenerLoader listenerLoader;

    public void onEnable() {
        plugin = this;

        channelRegistry = new ChannelRegistry();
        channelRegistry.register(this);

        configLoader = new ConfigLoader(this);

        try {
            this.config = new Config(configLoader.initFile("config.yml"));
        } catch (FileNotFoundException e) {
            getLogger().severe("Unable to load config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

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

    public Config getConfiguration() {
        return config;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }
}

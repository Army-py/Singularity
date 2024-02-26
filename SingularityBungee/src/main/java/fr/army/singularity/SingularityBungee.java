package fr.army.singularity;

import fr.army.singularity.network.channel.ChannelRegistry;
import fr.army.singularity.repository.EMFLoader;
import net.md_5.bungee.api.plugin.Plugin;

public class SingularityBungee extends Plugin {

    public static SingularityBungee plugin;

    private ChannelRegistry channelRegistry;
    private EMFLoader emfLoader;

    public void onEnable() {
        plugin = this;

        channelRegistry = new ChannelRegistry();
        channelRegistry.register();

        emfLoader = new EMFLoader();
        emfLoader.setupEntityManagerFactory();

        getLogger().info("SingularityBungee has been enabled!");
    }

    public void onDisable() {
        channelRegistry.unregister();

        getLogger().info("SingularityBungee has been disabled!");
    }

    public static SingularityBungee getPlugin() {
        return plugin;
    }

    public EMFLoader getEMFLoader() {
        return emfLoader;
    }
}

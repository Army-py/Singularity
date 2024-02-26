package fr.army.singularity;

import fr.army.singularity.network.channel.ChannelRegistry;
import net.md_5.bungee.api.plugin.Plugin;

public class SingularityBungee extends Plugin {

    private ChannelRegistry channelRegistry;

    public void onEnable() {
        channelRegistry = new ChannelRegistry();
        channelRegistry.register();

        getLogger().info("SingularityBungee has been enabled!");
    }

    public void onDisable() {
        channelRegistry.unregister();

        getLogger().info("SingularityBungee has been disabled!");
    }
}

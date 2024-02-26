package fr.army.singularity.network.channel;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ChannelRegistry {

    public static final String CHANNEL = "singularity:main";

    public void register(@NotNull Plugin plugin) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, CHANNEL);
    }

    public void unregister(@NotNull Plugin plugin) {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, CHANNEL);
    }

}

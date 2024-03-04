package fr.army.singularity.network.channel;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ChannelRegistry {

    public static final String PLAYER_CHANNEL = "singularity:player";
    public static final String BLOCK_CHANNEL = "singularity:block";

    public void register(@NotNull Plugin plugin) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, PLAYER_CHANNEL);
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, BLOCK_CHANNEL);
    }

    public void unregister(@NotNull Plugin plugin) {
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, PLAYER_CHANNEL);
        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, BLOCK_CHANNEL);
    }

}

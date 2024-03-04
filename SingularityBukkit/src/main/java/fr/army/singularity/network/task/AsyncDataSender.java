package fr.army.singularity.network.task;

import fr.army.singularity.SingularityPlugin;
import fr.army.singularity.network.channel.ChannelRegistry;
import org.bukkit.Bukkit;

public class AsyncDataSender {

    public void sendPluginMessage(byte[] data) {
        final SingularityPlugin plugin = SingularityPlugin.getPlugin();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(plugin, ChannelRegistry.CHANNEL, data);
        });
    }

    public void sendPluginMessage(byte[] data, long tickDelay) {
        final SingularityPlugin plugin = SingularityPlugin.getPlugin();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(plugin, ChannelRegistry.CHANNEL, data);
        }, tickDelay);
    }
}

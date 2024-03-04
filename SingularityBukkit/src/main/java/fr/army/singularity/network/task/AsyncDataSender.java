package fr.army.singularity.network.task;

import fr.army.singularity.SingularityPlugin;
import fr.army.singularity.network.channel.ChannelRegistry;
import org.bukkit.Bukkit;

public class AsyncDataSender {

    public void sendPluginMessage(byte[] data, String channel) {
        final SingularityPlugin plugin = SingularityPlugin.getPlugin();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(plugin, channel, data);
        });
    }

    public void sendPluginMessage(byte[] data, String channel, long tickDelay) {
        final SingularityPlugin plugin = SingularityPlugin.getPlugin();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(plugin, channel, data);
        }, tickDelay);
    }
}

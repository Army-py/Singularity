package fr.army.singularity.network.task.sender;

import fr.army.singularity.SingularityPlugin;
import fr.army.singularity.entity.AbstractLoggerEntity;
import org.bukkit.Bukkit;

public class AsyncDataSender {

    public void sendPluginMessage(AbstractLoggerEntity entity, String channel) {
        final SingularityPlugin plugin = SingularityPlugin.getPlugin();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(plugin, channel, entity.writeToByte());
        });
    }

    public void sendPluginMessage(AbstractLoggerEntity entity, String channel, long tickDelay) {
        final SingularityPlugin plugin = SingularityPlugin.getPlugin();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage(plugin, channel, entity.writeToByte());
        }, tickDelay);
    }
}

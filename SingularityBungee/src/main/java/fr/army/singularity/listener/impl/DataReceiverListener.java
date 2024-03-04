package fr.army.singularity.listener.impl;

import fr.army.singularity.SingularityBungee;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.entity.impl.ConnectionLoggerEntity;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import fr.army.singularity.network.channel.ChannelRegistry;
import fr.army.singularity.network.reader.PlayerLoggerReader;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DataReceiverListener implements Listener {

    private final StorageManager storageManager;

    public DataReceiverListener(SingularityBungee plugin) {
        this.storageManager = plugin.getStorageManager();
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDataReceived(PluginMessageEvent event) {

        if (!ChannelRegistry.CHANNEL.equals(event.getTag())){
            return;
        }

        final PlayerLoggerReader reader = new PlayerLoggerReader(storageManager);

        ProxyServer.getInstance().getScheduler().runAsync(
                SingularityBungee.getPlugin(),
                () -> reader.read(event.getData())
        );
    }
}

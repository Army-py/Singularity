package fr.army.singularity.listener.impl;

import fr.army.singularity.SingularityBungee;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.entity.impl.ConnectionLoggerEntity;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import fr.army.singularity.network.channel.ChannelRegistry;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
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

        final ByteArrayInputStream input = new ByteArrayInputStream(event.getData());
        try {
            final ObjectInputStream inputStream = new ObjectInputStream(input);

            final Object object = inputStream.readObject();
            if (object instanceof PlayerLoggerEntity playerEntity) {
                storageManager.savePlayerLogger(playerEntity);
            } else if (object instanceof PlayerHostLoggerEntity hostEntity) {
                storageManager.savePlayerHostLogger(hostEntity);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // storageManager.savePlayerLogger(PlayerLoggerEntity.readFromByte(event.getData()));
        // System.out.println("PlayerLoggerEntity saved");
    }
}

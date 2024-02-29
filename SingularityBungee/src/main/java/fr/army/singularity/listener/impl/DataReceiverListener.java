package fr.army.singularity.listener.impl;

import fr.army.singularity.SingularityBungee;
import fr.army.singularity.database.StorageManager;
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
            System.out.println("Channel is not good");
            System.out.println(event.getTag());
            System.out.println(ChannelRegistry.CHANNEL);
            return;
        }

        final ByteArrayInputStream input = new ByteArrayInputStream(event.getData());
        try {
            final ObjectInputStream inputStream = new ObjectInputStream(input);

            final Object object = inputStream.readObject();
            if (object instanceof PlayerLoggerEntity.PlayerLoggerSnapshot playerSnapshot) {
                storageManager.savePlayerLogger(PlayerLoggerEntity.fromSnapshot(playerSnapshot));
            } else if (object instanceof PlayerHostLoggerEntity.PlayerHostLoggerSnapshot hostSnapshot) {
                storageManager.savePlayerHostLogger(PlayerHostLoggerEntity.fromSnapshot(hostSnapshot));
            } else if (object instanceof ConnectionLoggerEntity.ConnectionLoggerSnapshot connectionSnapshot) {
                storageManager.saveConnectionLogger(ConnectionLoggerEntity.fromSnapshot(connectionSnapshot));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // storageManager.savePlayerLogger(PlayerLoggerEntity.readFromByte(event.getData()));
        // System.out.println("PlayerLoggerEntity saved");
    }
}

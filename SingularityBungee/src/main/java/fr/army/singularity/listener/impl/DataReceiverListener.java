package fr.army.singularity.listener.impl;

import fr.army.singularity.SingularityBungee;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.network.channel.ChannelRegistry;
import fr.army.singularity.network.reader.BlockLoggerReader;
import fr.army.singularity.network.reader.ItemLoggerReader;
import fr.army.singularity.network.reader.PlayerLoggerReader;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class DataReceiverListener implements Listener {

    private final StorageManager storageManager;

    public DataReceiverListener(SingularityBungee plugin) {
        this.storageManager = plugin.getStorageManager();
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDataReceived(PluginMessageEvent event) {
        if (!ChannelRegistry.PLAYER_CHANNEL.equals(event.getTag())){
            return;
        }

        final PlayerLoggerReader reader = new PlayerLoggerReader(storageManager);

        ProxyServer.getInstance().getScheduler().runAsync(
                SingularityBungee.getPlugin(),
                () -> reader.read(event.getData())
        );
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDataReceived(PluginMessageEvent event) {
        if (!ChannelRegistry.BLOCK_CHANNEL.equals(event.getTag())){
            return;
        }

        final BlockLoggerReader reader = new BlockLoggerReader(storageManager);

        ProxyServer.getInstance().getScheduler().runAsync(
                SingularityBungee.getPlugin(),
                () -> reader.read(event.getData())
        );
    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemDataReceived(PluginMessageEvent event) {
        if (!ChannelRegistry.ITEM_CHANNEL.equals(event.getTag())){
            return;
        }

        final ItemLoggerReader reader = new ItemLoggerReader(storageManager);

        ProxyServer.getInstance().getScheduler().runAsync(
                SingularityBungee.getPlugin(),
                () -> reader.read(event.getData())
        );
    }
}

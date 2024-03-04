package fr.army.singularity.listener.impl;

import fr.army.singularity.SingularityPlugin;
import fr.army.singularity.config.Config;
import fr.army.singularity.config.StorageMode;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.entity.impl.ConnectionLoggerEntity;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import fr.army.singularity.network.channel.ChannelRegistry;
import fr.army.singularity.network.task.AsyncDataSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class SessionListener implements Listener {

    private final StorageManager storageManager;

    public SessionListener(SingularityPlugin plugin) {
        this.storageManager = plugin.getStorageManager();
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        savePlayerLogger(player, 1);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        savePlayerLogger(player, 0);
    }


    private void savePlayerLogger(Player player, int action) {
        final AsyncDataSender asyncDataSender = new AsyncDataSender();

        final PlayerLoggerEntity playerLoggerEntity = new PlayerLoggerEntity()
                .setId(player.getUniqueId())
                .setName(player.getName())
        ;

        final PlayerHostLoggerEntity playerHostLoggerEntity = new PlayerHostLoggerEntity()
                .setPlayer(playerLoggerEntity)
                .setIp(Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress())
        ;
        playerLoggerEntity.getHosts().add(playerHostLoggerEntity);

        final Location location = player.getLocation();
        final ConnectionLoggerEntity connectionLoggerEntity = new ConnectionLoggerEntity()
                .setAction(action)
                .setLocX(location.getX())
                .setLocY(location.getY())
                .setLocZ(location.getZ())
                .setWorld(Objects.requireNonNull(location.getWorld()).getName())
                .setPlayerHost(playerHostLoggerEntity)
                .setPlayer(playerLoggerEntity)
        ;

        playerLoggerEntity.getConnections().add(connectionLoggerEntity);

        if (Config.storageMode.equals(StorageMode.BUNGEE)){
            connectionLoggerEntity.setServerName(Config.serverName);

            if (action == 1) {
                asyncDataSender.sendPluginMessage(playerLoggerEntity.writeToByte(), ChannelRegistry.PLAYER_CHANNEL, 1);
                asyncDataSender.sendPluginMessage(playerHostLoggerEntity.writeToByte(), ChannelRegistry.PLAYER_CHANNEL, 1);
            } else {
                asyncDataSender.sendPluginMessage(playerLoggerEntity.writeToByte(), ChannelRegistry.PLAYER_CHANNEL);
                asyncDataSender.sendPluginMessage(playerHostLoggerEntity.writeToByte(), ChannelRegistry.PLAYER_CHANNEL);
            }
        } else {
            storageManager.savePlayerLogger(playerLoggerEntity);
            storageManager.savePlayerHostLogger(playerHostLoggerEntity);
        }
    }
}

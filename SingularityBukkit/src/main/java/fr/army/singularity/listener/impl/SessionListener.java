package fr.army.singularity.listener.impl;

import fr.army.singularity.SingularityPlugin;
import fr.army.singularity.config.Config;
import fr.army.singularity.config.StorageMode;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.entity.impl.ConnectionLoggerEntity;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
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

    // TODO : code factorization

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final AsyncDataSender asyncDataSender = new AsyncDataSender();

        final PlayerLoggerEntity playerLoggerEntity = new PlayerLoggerEntity()
                .setId(player.getUniqueId())
                .setName(player.getName())
        ;
        asyncDataSender.sendPluginMessage(playerLoggerEntity.writeToByte());

        final PlayerHostLoggerEntity playerHostLoggerEntity = new PlayerHostLoggerEntity()
                .setPlayer(playerLoggerEntity)
                .setIp(Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress())
        ;
        asyncDataSender.sendPluginMessage(playerHostLoggerEntity.writeToByte());

        final Location location = player.getLocation();
        final ConnectionLoggerEntity connectionLoggerEntity = new ConnectionLoggerEntity()
                .setAction(1)
                .setLocX(location.getX())
                .setLocY(location.getY())
                .setLocZ(location.getZ())
                .setWorld(Objects.requireNonNull(location.getWorld()).getName())
                .setPlayerHost(playerHostLoggerEntity)
        ;

        if (Config.storageMode.equals(StorageMode.BUNGEE)){
            connectionLoggerEntity.setServerName(Config.serverName);

            asyncDataSender.sendPluginMessage(connectionLoggerEntity.writeToByte());
        }
        else {
            storageManager.savePlayerLogger(playerLoggerEntity);
            storageManager.savePlayerHostLogger(playerHostLoggerEntity);
            storageManager.saveConnectionLogger(connectionLoggerEntity);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final AsyncDataSender asyncDataSender = new AsyncDataSender();

        final PlayerLoggerEntity playerLoggerEntity = new PlayerLoggerEntity()
                .setId(player.getUniqueId())
                .setName(player.getName())
        ;
        asyncDataSender.sendPluginMessage(playerLoggerEntity.writeToByte());

        final PlayerHostLoggerEntity playerHostLoggerEntity = new PlayerHostLoggerEntity()
                .setPlayer(playerLoggerEntity)
                .setIp(Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress())
        ;
        asyncDataSender.sendPluginMessage(playerHostLoggerEntity.writeToByte());

        final Location location = player.getLocation();
        final ConnectionLoggerEntity connectionLoggerEntity = new ConnectionLoggerEntity()
                .setAction(0)
                .setLocX(location.getX())
                .setLocY(location.getY())
                .setLocZ(location.getZ())
                .setWorld(Objects.requireNonNull(location.getWorld()).getName())
                .setPlayerHost(playerHostLoggerEntity)
        ;

        if (Config.storageMode.equals(StorageMode.BUNGEE)){
            connectionLoggerEntity.setServerName(Config.serverName);

            asyncDataSender.sendPluginMessage(connectionLoggerEntity.writeToByte());
        }
        else {
            storageManager.savePlayerLogger(playerLoggerEntity);
            storageManager.savePlayerHostLogger(playerHostLoggerEntity);
            storageManager.saveConnectionLogger(connectionLoggerEntity);
        }
    }
}

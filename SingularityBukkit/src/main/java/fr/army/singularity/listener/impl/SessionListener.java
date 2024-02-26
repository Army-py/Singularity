package fr.army.singularity.listener.impl;

import fr.army.singularity.config.Config;
import fr.army.singularity.config.StorageMode;
import fr.army.singularity.entity.impl.ConnectionLoggerEntity;
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final AsyncDataSender asyncDataSender = new AsyncDataSender();

        final PlayerLoggerEntity playerLoggerEntity = new PlayerLoggerEntity()
                .setId(player.getUniqueId())
                .setName(player.getName())
        ;
        asyncDataSender.sendPluginMessage(playerLoggerEntity.writeToByte());

        final Location location = player.getLocation();
        final ConnectionLoggerEntity connectionLoggerEntity = new ConnectionLoggerEntity()
                .setIp(Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress())
                .setAction(1)
                .setLocX(location.getX())
                .setLocY(location.getY())
                .setLocZ(location.getZ())
                .setWorld(Objects.requireNonNull(location.getWorld()).getName())
        ;
        if (Config.storageMode.equals(StorageMode.BUNGEE))
            connectionLoggerEntity.setServerName(Config.serverName);

        asyncDataSender.sendPluginMessage(connectionLoggerEntity.writeToByte());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final AsyncDataSender asyncDataSender = new AsyncDataSender();

        final Location location = player.getLocation();
        final ConnectionLoggerEntity connectionLoggerEntity = new ConnectionLoggerEntity()
                .setIp(Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress())
                .setAction(0)
                .setLocX(location.getX())
                .setLocY(location.getY())
                .setLocZ(location.getZ())
                .setWorld(Objects.requireNonNull(location.getWorld()).getName())
        ;
        if (Config.storageMode.equals(StorageMode.BUNGEE))
            connectionLoggerEntity.setServerName(Config.serverName);

        asyncDataSender.sendPluginMessage(connectionLoggerEntity.writeToByte());
    }
}

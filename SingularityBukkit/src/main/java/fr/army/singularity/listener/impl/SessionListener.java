package fr.army.singularity.listener.impl;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SessionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Do something
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Do something
    }
}

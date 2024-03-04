package fr.army.singularity.network.channel;

import net.md_5.bungee.api.ProxyServer;

public class ChannelRegistry {

    public static final String PLAYER_CHANNEL = "singularity:player";
    public static final String BLOCK_CHANNEL = "singularity:block";

    public void register() {
        ProxyServer.getInstance().registerChannel(PLAYER_CHANNEL);
        ProxyServer.getInstance().registerChannel(BLOCK_CHANNEL);
    }

    public void unregister() {
        ProxyServer.getInstance().unregisterChannel(PLAYER_CHANNEL);
        ProxyServer.getInstance().unregisterChannel(BLOCK_CHANNEL);
    }
}

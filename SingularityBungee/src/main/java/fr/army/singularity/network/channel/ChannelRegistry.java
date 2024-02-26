package fr.army.singularity.network.channel;

import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.NotNull;

public class ChannelRegistry {

    public static final String CHANNEL = "singularity:main";

    public void register() {
        ProxyServer.getInstance().registerChannel(CHANNEL);
    }

    public void unregister() {
        ProxyServer.getInstance().unregisterChannel(CHANNEL);
    }
}

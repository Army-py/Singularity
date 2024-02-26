package fr.army.singularity.listener;

import fr.army.singularity.SingularityBungee;
import fr.army.singularity.listener.impl.DataReceiverListener;
import net.md_5.bungee.api.plugin.PluginManager;

public class ListenerLoader {

    public void registerListeners(SingularityBungee plugin) {
        final PluginManager pluginManager = plugin.getProxy().getPluginManager();
        pluginManager.registerListener(plugin, new DataReceiverListener(plugin));
    }
}

package fr.army.singularity.listener;

import fr.army.singularity.SingularityPlugin;
import fr.army.singularity.listener.impl.BlockListener;
import fr.army.singularity.listener.impl.ItemLoggerListener;
import fr.army.singularity.listener.impl.SessionListener;
import org.bukkit.plugin.PluginManager;

public class ListenerLoader {

    public void registerListeners(SingularityPlugin plugin) {
        final PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new SessionListener(plugin), plugin);
        pluginManager.registerEvents(new BlockListener(plugin), plugin);
        pluginManager.registerEvents(new ItemLoggerListener(plugin), plugin);
    }
}

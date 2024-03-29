package fr.army.singularity;

import fr.army.singularity.config.Config;
import fr.army.singularity.config.ConfigLoader;
import fr.army.singularity.config.StorageMode;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.database.repository.EMFLoader;
import fr.army.singularity.listener.ListenerLoader;
import fr.army.singularity.network.channel.ChannelRegistry;
import fr.army.singularity.network.task.queue.DataSenderQueueManager;
import fr.army.singularity.serializer.SerializerManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;

public class SingularityPlugin extends JavaPlugin {

    public static SingularityPlugin plugin;

    private ChannelRegistry channelRegistry;
    private ConfigLoader configLoader;
    private Config config;
    private ListenerLoader listenerLoader;
    private SerializerManager serializerManager;
    private EMFLoader emfLoader = null;
    private StorageManager storageManager = null;

    public void onEnable() {
        plugin = this;

        channelRegistry = new ChannelRegistry();
        channelRegistry.register(this);

        configLoader = new ConfigLoader(this);

        final YamlConfiguration configFile;
        final YamlConfiguration databaseConfigFile;
        try {
            configFile = configLoader.initFile("config.yml");
        } catch (FileNotFoundException e) {
            getLogger().severe("Unable to load config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        try {
            databaseConfigFile = configLoader.initFile("database.yml");
        } catch (FileNotFoundException e) {
            getLogger().severe("Unable to load database.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.config = new Config(configFile, databaseConfigFile);
        this.config.load();

        this.serializerManager = new SerializerManager();

        if (!Config.storageMode.equals(StorageMode.BUNGEE)){
            emfLoader = new EMFLoader();
            emfLoader.setupEntityManagerFactory(getDataFolder().getPath());

            storageManager = new StorageManager(emfLoader);
        }

        listenerLoader = new ListenerLoader();
        listenerLoader.registerListeners(this);

        getLogger().info("SingularityPlugin has been enabled!");
    }

    public void onDisable() {
        channelRegistry.unregister(this);
        if (emfLoader != null) {
            EMFLoader.closeEntityManagerFactory();
        }

        DataSenderQueueManager.shutdown();

        getLogger().info("SingularityPlugin has been disabled!");
    }

    public static SingularityPlugin getPlugin() {
        return plugin;
    }

    public ChannelRegistry getChannelRegistry() {
        return channelRegistry;
    }

    public ListenerLoader getListenerLoader() {
        return listenerLoader;
    }

    public Config getConfiguration() {
        return config;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public SerializerManager getSerializerManager() {
        return serializerManager;
    }

    public EMFLoader getEMFLoader() {
        return emfLoader;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }
}

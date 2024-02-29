package fr.army.singularity;

import fr.army.singularity.config.Config;
import fr.army.singularity.config.ConfigLoader;
import fr.army.singularity.database.StorageManager;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.listener.ListenerLoader;
import fr.army.singularity.network.channel.ChannelRegistry;
import fr.army.singularity.database.repository.EMFLoader;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SingularityBungee extends Plugin {

    public static SingularityBungee plugin;

    private ChannelRegistry channelRegistry;
    private ConfigLoader configLoader;
    private Config config;
    private ListenerLoader listenerLoader;
    private EMFLoader emfLoader;
    private StorageManager storageManager;

    public void onEnable() {
        plugin = this;

        channelRegistry = new ChannelRegistry();
        channelRegistry.register();

        configLoader = new ConfigLoader(this);

        final Configuration configFile;
        final Configuration databaseConfigFile;
        try {
            configFile = configLoader.initFile("config.yml");
        } catch (IOException e) {
            getLogger().severe("Unable to load config.yml");
            getProxy().getPluginManager().unregisterListeners(this);
            return;
        }
        try {
            databaseConfigFile = configLoader.initFile("database.yml");
        } catch (IOException e) {
            getLogger().severe("Unable to load database.yml");
            getProxy().getPluginManager().unregisterListeners(this);
            return;
        }
        this.config = new Config(configFile, databaseConfigFile);
        this.config.load();


        this.emfLoader = new EMFLoader();
        this.emfLoader.setupEntityManagerFactory(getDataFolder().getPath());

        try {
            storageManager = new StorageManager(this.emfLoader.getEntityManager());
        } catch (RepositoryException e) {
            getLogger().severe("An error occurred while setting up the storage manager: " + e.getMessage());
            getProxy().getPluginManager().unregisterListeners(this);
        }

        listenerLoader = new ListenerLoader();
        listenerLoader.registerListeners(this);


        getLogger().info("SingularityBungee has been enabled!");
    }

    public void onDisable() {
        channelRegistry.unregister();
        storageManager.close();
        EMFLoader.closeEntityManagerFactory();


        getLogger().info("SingularityBungee has been disabled!");
    }

    public static SingularityBungee getPlugin() {
        return plugin;
    }

    public EMFLoader getEMFLoader() {
        return emfLoader;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }
}

package fr.army.singularity.config;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {

    private final Plugin plugin;

    public ConfigLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public Configuration initFile(@NotNull String fileName) throws IOException {
        if (!plugin.getDataFolder().exists()) {
           plugin.getLogger().info("Created config folder: " + plugin.getDataFolder().mkdir());
        }

        File configFile = new File(plugin.getDataFolder(), fileName);

        if (!configFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(configFile);
            InputStream in = plugin.getResourceAsStream(fileName);
            in.transferTo(outputStream);
        }
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
    }

}

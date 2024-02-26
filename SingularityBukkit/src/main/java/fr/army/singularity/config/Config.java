package fr.army.singularity.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    private final YamlConfiguration config;

    public static String language;
    public static String serverName;
    public static StorageMode storageMode;
    public static String mysqlHost;
    public static int mysqlPort;
    public static String mysqlDatabase;
    public static String mysqlUsername;
    public static String mysqlPassword;
    public static String sqliteFile;

    public Config(YamlConfiguration config) {
        this.config = config;
    }

    public void load() {
        language = config.getString("language");
        serverName = config.getString("server-name");
        storageMode = StorageMode.valueOf(config.getString("storage-mode"));
        mysqlHost = config.getString("mysql.host");
        mysqlPort = config.getInt("mysql.port");
        mysqlDatabase = config.getString("mysql.database");
        mysqlUsername = config.getString("mysql.username");
        mysqlPassword = config.getString("mysql.password");
        sqliteFile = config.getString("sqlite.file");
    }
}

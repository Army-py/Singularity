package fr.army.singularity.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    private final YamlConfiguration config;
    private final YamlConfiguration databaseConfig;

    public static String language;
    public static String serverName;
    public static StorageMode storageMode;
    public static String mysqlHost;
    public static int mysqlPort;
    public static String mysqlDatabase;
    public static String mysqlUsername;
    public static String mysqlPassword;
    public static String sqliteFile;

    public Config(YamlConfiguration config, YamlConfiguration databaseConfig) {
        this.config = config;
        this.databaseConfig = databaseConfig;
    }

    public void load() {
        language = config.getString("language");
        serverName = config.getString("server-name");
        storageMode = StorageMode.valueOf(config.getString("storage-mode"));
        mysqlHost = databaseConfig.getString("mysql.host");
        mysqlPort = databaseConfig.getInt("mysql.port");
        mysqlDatabase = databaseConfig.getString("mysql.database");
        mysqlUsername = databaseConfig.getString("mysql.username");
        mysqlPassword = databaseConfig.getString("mysql.password");
        sqliteFile = databaseConfig.getString("sqlite.file");
    }
}

package fr.army.singularity.database.repository;

import fr.army.singularity.config.Config;
import fr.army.singularity.config.StorageMode;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.database.repository.exception.impl.EntityManagerNotInitializedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.Properties;

public class EMFLoader {

    private static EntityManagerFactory entityManagerFactory = null;

    private final boolean remoteDatabase;
    private final String databaseHost;
    private final int databasePort;
    private final String databaseName;
    private final String databaseUsername;
    private final String databasePassword;
    private final String sqliteFile;


    public EMFLoader(){
        remoteDatabase = Config.storageMode == StorageMode.MYSQL;
        databaseHost = Config.mysqlHost;
        databasePort = Config.mysqlPort;
        databaseName = Config.mysqlDatabase;
        databaseUsername = Config.mysqlUsername;
        databasePassword = Config.mysqlPassword;
        sqliteFile = Config.sqliteFile;
    }

    public void setupEntityManagerFactory(@NotNull String dataFolderPath) {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            Properties properties = new Properties();

            properties.put("hibernate.hbm2ddl.auto", "update");
            if (remoteDatabase) {
                properties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
                properties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseName + "?autoReconnect=true");
                properties.put("jakarta.persistence.jdbc.user", databaseUsername);
                properties.put("jakarta.persistence.jdbc.password", databasePassword);
                properties.put("hibernate.dialect", "org.hibernate.community.dialect.MariaDBDialect");
            } else {
                properties.put("jakarta.persistence.jdbc.driver", "org.sqlite.JDBC");
                properties.put("jakarta.persistence.jdbc.url", "jdbc:sqlite:" + dataFolderPath + "/" + sqliteFile);
                properties.put("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
                properties.put("hibernate.connection.isolation", String.valueOf(Connection.TRANSACTION_SERIALIZABLE));
                properties.put("jakarta.persistence.sharedCache.mode", "NONE");
                properties.put("hibernate.connection.release_mode", "after_transaction");
                // properties.put("hibernate.connection.pool_size", "1");
            }


            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit", properties);
        }
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    public EntityManager getEntityManager() throws RepositoryException {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            System.out.println("EMFLoader.getEntityManager");
            return entityManagerFactory.createEntityManager();
        } else {
            throw new EntityManagerNotInitializedException();
        }
    }
}

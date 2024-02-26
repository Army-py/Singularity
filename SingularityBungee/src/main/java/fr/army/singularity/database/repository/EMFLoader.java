package fr.army.singularity.database.repository;

import fr.army.singularity.SingularityBungee;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.database.repository.exception.impl.EntityManagerNotInitializedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Properties;

public class EMFLoader {

    private static EntityManagerFactory entityManagerFactory = null;

    private static final boolean remoteDatabase = false;
    private static final String databaseHost = "localhost";
    private static final int databasePort = 3306;
    private static final String databaseName = "database";
    private static final String databaseUsername = "username";
    private static final String databasePassword = "password";

    public void setupEntityManagerFactory() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            Properties properties = new Properties();

            if (remoteDatabase) {
                properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
                properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseName + "?autoReconnect=true");
                properties.put("javax.persistence.jdbc.user", databaseUsername);
                properties.put("javax.persistence.jdbc.password", databasePassword);
                properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
            } else {
                properties.put("javax.persistence.jdbc.driver", "org.sqlite.JDBC");
                properties.put("javax.persistence.jdbc.url", "jdbc:sqlite:./" + SingularityBungee.getPlugin().getDataFolder().getPath() + "/database" + "?autoReconnect=true");
                properties.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
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
        setupEntityManagerFactory();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            return entityManagerFactory.createEntityManager();
        } else {
            throw new EntityManagerNotInitializedException();
        }
    }
}
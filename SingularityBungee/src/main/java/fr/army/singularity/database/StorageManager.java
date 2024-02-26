package fr.army.singularity.database;

import fr.army.singularity.database.repository.impl.ConnectionLoggerRepository;
import fr.army.singularity.database.repository.impl.PlayerLoggerRepository;
import fr.army.singularity.entity.impl.ConnectionLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import jakarta.persistence.EntityManager;

public class StorageManager {

    private final PlayerLoggerRepository playerLoggerRepository;
    private final ConnectionLoggerRepository connectionLoggerRepository;

    public StorageManager(EntityManager entityManager) {
        this.playerLoggerRepository = new PlayerLoggerRepository(PlayerLoggerEntity.class, entityManager);
        this.connectionLoggerRepository = new ConnectionLoggerRepository(ConnectionLoggerEntity.class, entityManager);
    }

    public void savePlayerLogger(PlayerLoggerEntity playerLoggerEntity) {
        if (playerLoggerRepository.findByPlayerUuid(playerLoggerEntity.getId()) == null) {
            playerLoggerRepository.insert(playerLoggerEntity);
        } else {
            playerLoggerRepository.update(playerLoggerEntity, result -> {});
        }
    }

    public void saveConnectionLogger(ConnectionLoggerEntity connectionLoggerEntity) {
        connectionLoggerRepository.insert(connectionLoggerEntity);
    }
}

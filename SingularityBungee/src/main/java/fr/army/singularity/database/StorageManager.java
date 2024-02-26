package fr.army.singularity.database;

import fr.army.singularity.database.repository.impl.PlayerLoggerRepository;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import jakarta.persistence.EntityManager;

public class StorageManager {

    private final PlayerLoggerRepository playerLoggerRepository;

    public StorageManager(EntityManager entityManager) {
        this.playerLoggerRepository = new PlayerLoggerRepository(PlayerLoggerEntity.class, entityManager);
    }

    public void savePlayerLogger(PlayerLoggerEntity playerLoggerEntity) {
        if (playerLoggerRepository.findByPlayerUuid(playerLoggerEntity.getId()) == null) {
            playerLoggerRepository.insert(playerLoggerEntity);
        } else {
            playerLoggerRepository.update(playerLoggerEntity, result -> {});
        }
    }
}

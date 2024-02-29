package fr.army.singularity.database;

import fr.army.singularity.database.repository.impl.ConnectionLoggerRepository;
import fr.army.singularity.database.repository.impl.PlayerHostLoggerRepository;
import fr.army.singularity.database.repository.impl.PlayerLoggerRepository;
import fr.army.singularity.entity.impl.ConnectionLoggerEntity;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

public class StorageManager {

    private final EntityManager entityManager;
    private final PlayerLoggerRepository playerLoggerRepository;
    private final ConnectionLoggerRepository connectionLoggerRepository;
    private final PlayerHostLoggerRepository playerHostLoggerRepository;

    public StorageManager(@NotNull EntityManager entityManager) {
        this.entityManager = entityManager;
        this.playerLoggerRepository = new PlayerLoggerRepository(PlayerLoggerEntity.class, entityManager);
        this.connectionLoggerRepository = new ConnectionLoggerRepository(ConnectionLoggerEntity.class, entityManager);
        this.playerHostLoggerRepository = new PlayerHostLoggerRepository(PlayerHostLoggerEntity.class, entityManager);
    }

    public void savePlayerLogger(PlayerLoggerEntity playerLoggerEntity) {
        if (playerLoggerRepository.findByPlayerUuid(playerLoggerEntity.getId()) == null) {
            playerLoggerRepository.insert(playerLoggerEntity);
        } else {
            playerLoggerRepository.update(playerLoggerEntity, result -> {});
        }
    }

    public void savePlayerHostLogger(PlayerHostLoggerEntity playerHostLoggerEntity) {
        if (playerHostLoggerRepository.findByPlayerIdAndIp(playerHostLoggerEntity.getPlayer().getId(), playerHostLoggerEntity.getIp()) == null) {
            playerHostLoggerRepository.insert(playerHostLoggerEntity);
        } else {
            playerHostLoggerRepository.update(playerHostLoggerEntity, result -> {});
        }
    }

    public void saveConnectionLogger(ConnectionLoggerEntity connectionLoggerEntity) {
        connectionLoggerRepository.insert(connectionLoggerEntity);
    }

    public void close() {
        entityManager.close();
    }
}

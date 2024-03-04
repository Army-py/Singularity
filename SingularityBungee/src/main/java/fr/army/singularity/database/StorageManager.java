package fr.army.singularity.database;

import fr.army.singularity.database.repository.EMFLoader;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.database.repository.impl.PlayerHostLoggerRepository;
import fr.army.singularity.database.repository.impl.PlayerLoggerRepository;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import org.jetbrains.annotations.NotNull;

public class StorageManager {

    private final EMFLoader emfLoader;
    private final PlayerLoggerRepository playerLoggerRepository;
    private final PlayerHostLoggerRepository playerHostLoggerRepository;

    public StorageManager(@NotNull EMFLoader emfLoader) throws RepositoryException {
        this.emfLoader = emfLoader;
        this.playerLoggerRepository = new PlayerLoggerRepository(PlayerLoggerEntity.class, emfLoader);
        this.playerHostLoggerRepository = new PlayerHostLoggerRepository(PlayerHostLoggerEntity.class, emfLoader);
    }

    public void savePlayerLogger(PlayerLoggerEntity playerLoggerEntity) {
        playerLoggerRepository.save(playerLoggerEntity);
    }

    public void savePlayerHostLogger(PlayerHostLoggerEntity playerHostLoggerEntity) {
        playerHostLoggerRepository.save(playerHostLoggerEntity);
    }
}

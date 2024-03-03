package fr.army.singularity.database;

import fr.army.singularity.database.repository.EMFLoader;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.database.repository.impl.PlayerLoggerRepository;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import org.jetbrains.annotations.NotNull;

public class StorageManager {

    private final EMFLoader emfLoader;
    private final PlayerLoggerRepository playerLoggerRepository;
    // private final ConnectionLoggerRepository connectionLoggerRepository;
    // private final PlayerHostLoggerRepository playerHostLoggerRepository;

    public StorageManager(@NotNull EMFLoader emfLoader) throws RepositoryException {
        this.emfLoader = emfLoader;
        this.playerLoggerRepository = new PlayerLoggerRepository(PlayerLoggerEntity.class, emfLoader);
        // this.connectionLoggerRepository = new ConnectionLoggerRepository(ConnectionLoggerEntity.class, emfLoader);
        // this.playerHostLoggerRepository = new PlayerHostLoggerRepository(PlayerHostLoggerEntity.class, emfLoader);
    }

    public void savePlayerLogger(PlayerLoggerEntity playerLoggerEntity) throws RepositoryException {
        playerLoggerRepository.save(playerLoggerEntity);
    }

    // public void savePlayerHostLogger(PlayerHostLoggerEntity playerHostLoggerEntity) throws RepositoryException {
    //     if (playerHostLoggerRepository.findByPlayerIdAndIp(playerHostLoggerEntity.getPlayer().getUuid(), playerHostLoggerEntity.getIp()) == null) {
    //         playerHostLoggerRepository.insert(playerHostLoggerEntity);
    //     } else {
    //         playerHostLoggerRepository.update(playerHostLoggerEntity, result -> {});
    //     }
    // }
    //
    // public void saveConnectionLogger(ConnectionLoggerEntity connectionLoggerEntity) {
    //     connectionLoggerRepository.insert(connectionLoggerEntity);
    // }

    public EMFLoader getEmfLoader() {
        return emfLoader;
    }
}

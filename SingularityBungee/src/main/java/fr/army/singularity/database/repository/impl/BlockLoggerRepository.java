package fr.army.singularity.database.repository.impl;

import fr.army.singularity.database.repository.AbstractRepository;
import fr.army.singularity.database.repository.EMFLoader;
import fr.army.singularity.entity.impl.BlockLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;

public class BlockLoggerRepository extends AbstractRepository<BlockLoggerEntity> {
    public BlockLoggerRepository(Class<BlockLoggerEntity> entityClass, EMFLoader emfLoader) {
        super(entityClass, emfLoader);
    }

    public void save(BlockLoggerEntity blockLoggerEntity){
        executeInTransaction(entityManager -> entityManager.persist(blockLoggerEntity));
    }
}

package fr.army.singularity.database.repository.impl;

import fr.army.singularity.database.repository.AbstractRepository;
import fr.army.singularity.database.repository.EMFLoader;
import fr.army.singularity.entity.impl.ItemLoggerEntity;

public class ItemLoggerRepository extends AbstractRepository<ItemLoggerEntity> {
    public ItemLoggerRepository(Class<ItemLoggerEntity> entityClass, EMFLoader emfLoader) {
        super(entityClass, emfLoader);
    }

    public void save(ItemLoggerEntity itemLoggerEntity){
        executeInTransaction(entityManager -> entityManager.persist(itemLoggerEntity));
    }
}

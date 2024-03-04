package fr.army.singularity.database.repository;

import fr.army.singularity.database.repository.callback.AsyncCallBackObject;
import fr.army.singularity.database.repository.callback.AsyncCallBackObjectList;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.database.queue.DatabaseTask;
import fr.army.singularity.database.queue.DatabaseTaskQueueManager;
import fr.army.singularity.entity.AbstractLoggerEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractRepository<T extends AbstractLoggerEntity> {

    protected final Class<T> entityClass;
    protected final EMFLoader emfLoader;

    public AbstractRepository(Class<T> entityClass, EMFLoader emfLoader) {
        this.entityClass = entityClass;
        this.emfLoader = emfLoader;
    }

    public void insert(T entity) {
        executeInTransaction(entityManager -> entityManager.persist(entity));
    }

    public void update(T entity, AsyncCallBackObject<T> asyncCallBackObject) {
        executeInTransaction(entityManager -> {
            T mergedEntity = entityManager.merge(entity);
            if (asyncCallBackObject != null) {
                asyncCallBackObject.done(mergedEntity);
            }
        });
    }

    public void delete(T entity) {
        executeInTransaction(entityManager -> entityManager.remove(entityManager.merge(entity)));
    }

    public void find(int id, AsyncCallBackObject<T> asyncCallBackObject) {
        executeInTransaction(entityManager -> {
            T e = entityManager.find(entityClass, id);
            if (asyncCallBackObject != null) {
                asyncCallBackObject.done(e);
            }
        });
    }

    protected void executeAsyncQuery(Supplier<T> supplier, AsyncCallBackObject<T> asyncCallBackObject){
        executeInTransaction(entityManager -> {
            T e = supplier.get();
            if (asyncCallBackObject != null) {
                asyncCallBackObject.done(e);
            }
        });
    }

    protected void executeAsyncQueryList(Supplier<List<T>> supplier, AsyncCallBackObjectList<T> asyncCallBackObjectList){
        executeInTransaction(entityManager -> {
            List<T> e = supplier.get();
            if (asyncCallBackObjectList != null) {
                asyncCallBackObjectList.done(e);
            }
        });
    }

    @Transactional
    protected void executeInTransaction(Consumer<EntityManager> action) {
        try {
            DatabaseTask task = new DatabaseTask(emfLoader.getEntityManager(), action);
            DatabaseTaskQueueManager.enqueueTask(task);
        } catch (InterruptedException | RepositoryException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}

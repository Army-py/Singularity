package fr.army.singularity.database.repository;

import fr.army.singularity.database.repository.callback.AsyncCallBackObject;
import fr.army.singularity.database.repository.callback.AsyncCallBackObjectList;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.database.repository.queue.DatabaseTask;
import fr.army.singularity.database.repository.queue.DatabaseTaskQueueManager;
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

    // @Transactional
    // protected synchronized void persist(T entity) throws RepositoryException {
    //     final EntityManager entityManager = getEntityManager();
    //     EntityTransaction entityTransaction = entityManager.getTransaction();
    //     entityTransaction.begin();
    //
    //     entityManager.persist(entity);
    //
    //     entityTransaction.commit();
    //     entityManager.close();
    // }

    // public synchronized void insert(T entity) {
    //     new Thread(() -> {
    //         try {
    //             persist(entity);
    //         } catch (RepositoryException e) {
    //             SingularityBungee.getPlugin().getLogger().severe(e.getMessage());
    //         }
    //     }).start();
    // }

    public void insert(T entity) {
        executeInTransaction(entityManager -> entityManager.persist(entity));
    }

    // @Transactional
    // protected synchronized T merge(T entity) throws RepositoryException {
    //     final EntityManager entityManager = getEntityManager();
    //     EntityTransaction entityTransaction = entityManager.getTransaction();
    //     entityTransaction.begin();
    //
    //     T mergedEntity = entityManager.merge(entity);
    //
    //     entityTransaction.commit();
    //     entityManager.close();
    //     return mergedEntity;
    // }

    // public synchronized void update(T entity, AsyncCallBackObject<T> asyncCallBackObject) {
    //     new Thread(() -> {
    //         try {
    //             asyncCallBackObject.done(merge(entity));
    //         } catch (RepositoryException e) {
    //             SingularityBungee.getPlugin().getLogger().severe(e.getMessage());
    //         }
    //     }).start();
    // }

    public void update(T entity, AsyncCallBackObject<T> asyncCallBackObject) {
        executeInTransaction(entityManager -> {
            T mergedEntity = entityManager.merge(entity);
            if (asyncCallBackObject != null) {
                asyncCallBackObject.done(mergedEntity);
            }
        });
    }

    // @Transactional
    // protected synchronized void remove(T entity) throws RepositoryException {
    //     final EntityManager entityManager = getEntityManager();
    //     EntityTransaction entityTransaction = entityManager.getTransaction();
    //     entityTransaction.begin();
    //
    //     entityManager.remove(entityManager.merge(entity));
    //
    //     entityTransaction.commit();
    //     entityManager.close();
    // }

    // public void delete(T entity) {
    //     new Thread(() -> {
    //         try {
    //             remove(entity);
    //         } catch (RepositoryException e) {
    //             SingularityBungee.getPlugin().getLogger().severe(e.getMessage());
    //         }
    //     }).start();
    // }

    public void delete(T entity) {
        executeInTransaction(entityManager -> entityManager.remove(entityManager.merge(entity)));
    }

    // @Nullable
    // protected synchronized T find(int id) throws RepositoryException {
    //     final EntityManager entityManager = getEntityManager();
    //     EntityTransaction entityTransaction = entityManager.getTransaction();
    //     entityTransaction.begin();
    //
    //     T e = entityManager.find(entityClass, id);
    //
    //     entityTransaction.commit();
    //     entityManager.close();
    //     return e;
    // }

    // public void find(int id, AsyncCallBackObject<T> asyncCallBackObject) {
    //     new Thread(() -> {
    //         try {
    //             asyncCallBackObject.done(find(id));
    //         } catch (RepositoryException e) {
    //             SingularityBungee.getPlugin().getLogger().severe(e.getMessage());
    //         }
    //     }).start();
    // }

    public void find(int id, AsyncCallBackObject<T> asyncCallBackObject) {
        executeInTransaction(entityManager -> {
            T e = entityManager.find(entityClass, id);
            if (asyncCallBackObject != null) {
                asyncCallBackObject.done(e);
            }
        });
    }

    // protected synchronized List<T> findAll() throws RepositoryException {
    //     final EntityManager entityManager = getEntityManager();
    //     EntityTransaction entityTransaction = entityManager.getTransaction();
    //     entityTransaction.begin();
    //
    //     CriteriaQuery<T> cq = entityManager.getCriteriaBuilder().createQuery(entityClass);
    //     cq.select(cq.from(entityClass));
    //     List<T> e = entityManager.createQuery(cq).getResultList();
    //
    //     entityTransaction.commit();
    //     entityManager.close();
    //     return e;
    // }

    // public void findAll(AsyncCallBackObjectList<T> asyncCallBackObjectList) {
    //     new Thread(() -> {
    //         try {
    //             asyncCallBackObjectList.done(findAll());
    //         } catch (RepositoryException e) {
    //             SingularityBungee.getPlugin().getLogger().severe(e.getMessage()); // TODO : error with bukkit part
    //         }
    //     }).start();
    // }

    // public void findAll(AsyncCallBackObjectList<T> asyncCallBackObjectList) {
    //     executeInTransaction(entityManager -> {
    //         List<T> e = null;
    //         try {
    //             e = findAll();
    //         } catch (RepositoryException ex) {
    //             throw new RuntimeException(ex);
    //         }
    //         if (asyncCallBackObjectList != null) {
    //             asyncCallBackObjectList.done(e);
    //         }
    //     });
    // }

    // protected void executeAsyncQuery(Supplier<T> supplier, AsyncCallBackObject<T> asyncCallBackObject){
    //     new Thread(() -> {
    //         asyncCallBackObject.done(supplier.get());
    //     }).start();
    // }
    //
    // protected void executeAsyncQueryList(Supplier<List<T>> supplier, AsyncCallBackObjectList<T> asyncCallBackObjectList){
    //     new Thread(() -> {
    //         asyncCallBackObjectList.done(supplier.get());
    //     }).start();
    // }

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

    // protected EntityManager getEntityManager() throws RepositoryException {
    //     return emfLoader.getEntityManager();
    // }

    public void shutdownQueueManager() {
        DatabaseTaskQueueManager.shutdown();
    }
}

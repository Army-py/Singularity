package fr.army.singularity.database.repository.impl;

import fr.army.singularity.database.repository.AbstractRepository;
import fr.army.singularity.database.repository.EMFLoader;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import jakarta.persistence.criteria.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerHostLoggerRepository extends AbstractRepository<PlayerHostLoggerEntity> {
    public PlayerHostLoggerRepository(Class<PlayerHostLoggerEntity> entityClass, EMFLoader emfLoader) {
        super(entityClass, emfLoader);
    }

    // @Nullable
    // public PlayerHostLoggerEntity findByPlayerIdAndIp(@NotNull UUID id, String ip) throws RepositoryException {
    //     final EntityManager entityManager = getEntityManager();
    //     final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    //     CriteriaQuery<PlayerHostLoggerEntity> query = criteriaBuilder.createQuery(entityClass);
    //     Root<PlayerHostLoggerEntity> playerHostRoot = query.from(entityClass);
    //     query.select(playerHostRoot);
    //
    //     Join<PlayerHostLoggerEntity, PlayerLoggerEntity> playerJoin = playerHostRoot.join("player", JoinType.INNER);
    //
    //     query.where(criteriaBuilder.and(
    //             criteriaBuilder.equal(playerJoin.get("id"), id.toString()),
    //             criteriaBuilder.equal(playerHostRoot.get("ip"), ip)
    //     ));
    //     List<PlayerHostLoggerEntity> results = entityManager.createQuery(query).getResultList();
    //     entityManager.close();
    //     return !results.isEmpty() ? results.get(0) : null;
    // }
    //
    // public synchronized void asyncFindByPlayerIdAndIp(@NotNull UUID id, @NotNull String ip,
    //                                                   @NotNull AsyncCallBackObject<PlayerHostLoggerEntity> callback) {
    //     executeAsyncQuery(() -> {
    //         try {
    //             return findByPlayerIdAndIp(id, ip);
    //         } catch (RepositoryException e) {
    //             throw new RuntimeException(e);
    //         }
    //     }, callback);
    // }

    public void save(@NotNull PlayerHostLoggerEntity playerHostLoggerEntity){
        executeInTransaction(entityManager -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<PlayerHostLoggerEntity> query = criteriaBuilder.createQuery(entityClass);
            final Root<PlayerHostLoggerEntity> playerHostRoot = query.from(entityClass);
            query.select(playerHostRoot);

            final Join<PlayerHostLoggerEntity, PlayerLoggerEntity> playerJoin = playerHostRoot.join("player", JoinType.INNER);

            query.where(criteriaBuilder.and(
                    criteriaBuilder.equal(playerJoin.get("id"), playerHostLoggerEntity.getPlayer().getId()),
                    criteriaBuilder.equal(playerHostRoot.get("ip"), playerHostLoggerEntity.getIp())
            ));
            final List<PlayerHostLoggerEntity> result = entityManager.createQuery(query).getResultList();

            if (result.isEmpty()) {
                entityManager.persist(playerHostLoggerEntity);
            }else {
                entityManager.merge(playerHostLoggerEntity);
            }
        });
    }
}

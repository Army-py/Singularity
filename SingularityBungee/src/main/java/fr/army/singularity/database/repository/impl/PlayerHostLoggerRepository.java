package fr.army.singularity.database.repository.impl;

import fr.army.singularity.database.repository.AbstractRepository;
import fr.army.singularity.database.repository.callback.AsyncCallBackObject;
import fr.army.singularity.entity.impl.PlayerHostLoggerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PlayerHostLoggerRepository extends AbstractRepository<PlayerHostLoggerEntity> {
    public PlayerHostLoggerRepository(Class<PlayerHostLoggerEntity> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }

    @Nullable
    public PlayerHostLoggerEntity findByPlayerIdAndIp(@NotNull UUID id, String ip) {
        CriteriaQuery<PlayerHostLoggerEntity> query = criteriaBuilder.createQuery(entityClass);
        Root<PlayerHostLoggerEntity> playerHostRoot = query.from(entityClass);
        query.select(playerHostRoot);

        query.where(criteriaBuilder.and(
                criteriaBuilder.equal(playerHostRoot.get("id"), id),
                criteriaBuilder.equal(playerHostRoot.get("ip"), ip)
        ));
        List<PlayerHostLoggerEntity> results = entityManager.createQuery(query).getResultList();
        return !results.isEmpty() ? results.get(0) : null;
    }

    public synchronized void asyncFindByPlayerIdAndIp(@NotNull UUID id, @NotNull String ip,
                                                      @NotNull AsyncCallBackObject<PlayerHostLoggerEntity> callback) {
        executeAsyncQuery(() -> findByPlayerIdAndIp(id, ip), callback);
    }
}

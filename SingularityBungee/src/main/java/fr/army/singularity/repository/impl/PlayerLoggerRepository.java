package fr.army.singularity.repository.impl;

import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import fr.army.singularity.repository.AbstractRepository;
import jakarta.persistence.EntityManager;

public class PlayerLoggerRepository extends AbstractRepository<PlayerLoggerEntity> {
    public PlayerLoggerRepository(Class<PlayerLoggerEntity> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }

//    @Nullable
//    public TeamEntity findByTeamUuid(@NotNull UUID uuid){
//        CriteriaQuery<TeamEntity> query = criteriaBuilder.createQuery(entityClass);
//        Root<TeamEntity> teamEntityRoot = query.from(entityClass);
//        query.select(teamEntityRoot);
//        query.where(criteriaBuilder.equal(teamEntityRoot.get("uuid"), uuid));
//        List<TeamEntity> results = entityManager.createQuery(query).getResultList();
//        return !results.isEmpty() ? results.get(0) : null;
//    }
//
//    public synchronized void asyncFindByTeamUuid(@NotNull UUID uuid,
//                                                 @NotNull AsyncCallBackObject<TeamEntity> callback){
//        executeAsyncQuery(() -> findByTeamUuid(uuid), callback);
//    }
}

package fr.army.singularity.database.repository.impl;

import fr.army.singularity.database.repository.AbstractRepository;
import fr.army.singularity.database.repository.EMFLoader;
import fr.army.singularity.entity.impl.ConnectionLoggerEntity;
import jakarta.persistence.EntityManager;

public class ConnectionLoggerRepository extends AbstractRepository<ConnectionLoggerEntity> {
    public ConnectionLoggerRepository(Class<ConnectionLoggerEntity> entityClass, EMFLoader emfLoader) {
        super(entityClass, emfLoader);
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

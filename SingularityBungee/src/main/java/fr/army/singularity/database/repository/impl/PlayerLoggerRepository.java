package fr.army.singularity.database.repository.impl;

import fr.army.singularity.database.repository.EMFLoader;
import fr.army.singularity.database.repository.exception.RepositoryException;
import fr.army.singularity.entity.impl.PlayerLoggerEntity;
import fr.army.singularity.database.repository.AbstractRepository;
import jakarta.persistence.EntityManager;

import java.util.UUID;

public class PlayerLoggerRepository extends AbstractRepository<PlayerLoggerEntity> {
    public PlayerLoggerRepository(Class<PlayerLoggerEntity> entityClass, EMFLoader emfLoader) {
        super(entityClass, emfLoader);
    }

   // @Nullable
   // public TeamEntity findByTeamUuid(@NotNull UUID uuid){
   //     CriteriaQuery<TeamEntity> query = criteriaBuilder.createQuery(entityClass);
   //     Root<TeamEntity> teamEntityRoot = query.from(entityClass);
   //     query.select(teamEntityRoot);
   //     query.where(criteriaBuilder.equal(teamEntityRoot.get("uuid"), uuid));
   //     List<TeamEntity> results = entityManager.createQuery(query).getResultList();
   //     return !results.isEmpty() ? results.get(0) : null;
   // }
   //
   // public synchronized void asyncFindByTeamUuid(@NotNull UUID uuid,
   //                                              @NotNull AsyncCallBackObject<TeamEntity> callback){
   //     executeAsyncQuery(() -> findByTeamUuid(uuid), callback);
   // }

    public PlayerLoggerEntity findByPlayerUuid(UUID uuid) throws RepositoryException {
        final EntityManager entityManager = getEntityManager();
        final PlayerLoggerEntity res = entityManager.find(entityClass, uuid.toString());
        entityManager.close();
        return res;
    }
}

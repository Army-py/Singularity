package fr.army.singularity.database.repository.exception.impl;

import fr.army.singularity.database.repository.exception.RepositoryException;

public class EntityNotFoundException extends RepositoryException {

    public EntityNotFoundException(Class<?> entityClass) {
        super("Entity not found", "Entity " + entityClass.getSimpleName() + " not found");
    }
}
